package com.challenge.foroHub.service;


import com.challenge.foroHub.domain.topicos.dto.DatosActualizacionTopicoDTO;
import com.challenge.foroHub.domain.topicos.dto.DatosListaTopicosDTO;
import com.challenge.foroHub.domain.topicos.dto.DatosTopicosDTO;
import com.challenge.foroHub.domain.topicos.Topico;
import com.challenge.foroHub.repository.CursoRepository;
import com.challenge.foroHub.repository.TopicoRepository;
import com.challenge.foroHub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public Topico guardar(DatosTopicosDTO topico){
        var autor = usuarioRepository.findById(topico.autorId()).get();
        var curso = cursoRepository.findById(topico.cursoId()).get();

        if(topicoRepository.existsByTituloAndMensaje(topico.titulo(), topico.mensaje())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Titulo y mensajes existentes");
        }

        var topicos = new Topico(null, topico.titulo(), topico.mensaje(), autor, curso);
        return topicoRepository.save(topicos);
    }

    public Page<DatosListaTopicosDTO> listar(String titulo, Integer anio, Pageable paginacion) {
        if (titulo != null && anio != null) {
            return topicoRepository.findByNombreCursoAndAnio(titulo, anio, paginacion).map(DatosListaTopicosDTO::new);
        } else {
            return topicoRepository.findAll(paginacion).map(DatosListaTopicosDTO::new);
        }
    }

    @Transactional
    public DatosListaTopicosDTO actualizar(Long id, DatosActualizacionTopicoDTO datosTopicos) {
        var topicoEncontrado = topicoRepository.findById(id);
        if (!topicoEncontrado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el registro con el id: " + id);
        }
        //Obtengo el objeto a actualizar si está presente
        var topico = topicoEncontrado.get();

        //llamo al método creado en la clase Tópico con los datos seteados
        topico.actualizar(datosTopicos);
        return new DatosListaTopicosDTO(topico);
    }

    @Transactional
    public void eliminar(Long id) {
        var topicoEncontrado = topicoRepository.findById(id);
        if (!topicoEncontrado.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el tópico con el id: " + id);
        }
        topicoRepository.deleteById(id);
    }
}
