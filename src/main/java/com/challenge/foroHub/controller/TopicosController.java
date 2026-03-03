package com.challenge.foroHub.controller;


import com.challenge.foroHub.domain.topicos.DatosTopicos;
import com.challenge.foroHub.domain.topicos.Topico;
import com.challenge.foroHub.repository.CursoRepository;
import com.challenge.foroHub.repository.TopicoRepository;
import com.challenge.foroHub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topico;
    @Autowired
    private UsuarioRepository autores;
    @Autowired
    private CursoRepository cursos;

    @PostMapping
    @Transactional
    public ResponseEntity<Topico> guardar(@RequestBody @Valid DatosTopicos datosTopicos, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        var autor = autores.findById(datosTopicos.autorId()).get();
        var curso = cursos.findById(datosTopicos.cursoId()).get();

        if (topico.existsByTituloAndMensaje(datosTopicos.titulo(), datosTopicos.mensaje())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un tópico con ese título y mensaje");
        }

        var topicos = new Topico(null, datosTopicos.titulo(), datosTopicos.mensaje(), autor, curso);

        topico.save(topicos);

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicos.getId()).toUri();

        return  ResponseEntity.created(uri).body(topicos);
    }
}
