package com.challenge.foroHub.controller;


import com.challenge.foroHub.domain.topicos.dto.DatosActualizacionTopicoDTO;
import com.challenge.foroHub.domain.topicos.dto.DatosListaTopicosDTO;
import com.challenge.foroHub.domain.topicos.dto.DatosTopicosDTO;
import com.challenge.foroHub.domain.topicos.Topico;
import com.challenge.foroHub.repository.CursoRepository;
import com.challenge.foroHub.repository.TopicoRepository;
import com.challenge.foroHub.repository.UsuarioRepository;
import com.challenge.foroHub.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private TopicoService service;


    //GUARDAR TÓPICOS
    @PostMapping
    public ResponseEntity<Topico> guardar(@RequestBody @Valid DatosTopicosDTO datosTopicosDTO, UriComponentsBuilder uriComponentsBuilder) {
        var topicos = service.guardar(datosTopicosDTO);

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicos.getId()).toUri();

        return ResponseEntity.created(uri).body(topicos);
    }

    //LISTAR TÓPICOS PAGINADOS
    //Mediante la opción de título del topico y año, si no se envía uno de los dos devuelve todos los tópicos
    @GetMapping
    public ResponseEntity<Page<DatosListaTopicosDTO>> listar(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion,
                                                             @RequestParam(required = false) String titulo, @RequestParam(required = false) Integer anio) {
        var topicos = service.listar(titulo, anio, paginacion);
        return ResponseEntity.ok(topicos);
    }

    //DETALLE DE UN TOPICO
    @GetMapping("/{id}")
    public ResponseEntity<DatosListaTopicosDTO> detallarTopico(@PathVariable @Valid Long id) {
        var topicoDetalle = topico.findById(id);
        if (topicoDetalle.isPresent()) {
            return ResponseEntity.ok(new DatosListaTopicosDTO(topicoDetalle.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatosListaTopicosDTO> actualizar(@RequestBody DatosActualizacionTopicoDTO datosTopicos, @PathVariable Long id) {
        var topicoActualizado = service.actualizar(id, datosTopicos);
        return ResponseEntity.ok(topicoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
