package com.challenge.foroHub.domain.topicos;

import com.challenge.foroHub.domain.cursos.Curso;
import com.challenge.foroHub.domain.respuestas.Respuesta;
import com.challenge.foroHub.domain.topicos.dto.DatosActualizacionTopicoDTO;
import com.challenge.foroHub.domain.topicos.dto.DatosTopicosDTO;
import com.challenge.foroHub.domain.usuarios.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name="topicos")
@Entity(name = "Topico")

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(@Valid DatosTopicosDTO datosTopicosDTO) {
    }

    public Topico(Long id, String titulo, String mensaje, Usuario autor, Curso curso) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
        this.fechaCreacion = LocalDateTime.now();
        this.status = Status.ABIERTO;
    }

    public void actualizar(DatosActualizacionTopicoDTO datosTopicos) {
        if(datosTopicos.titulo() != null) this.titulo = datosTopicos.titulo();
        if (datosTopicos.mensaje() != null) this.mensaje = datosTopicos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
    }
}
