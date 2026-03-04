package com.challenge.foroHub.domain.topicos.dto;


import com.challenge.foroHub.domain.topicos.Status;
import com.challenge.foroHub.domain.topicos.Topico;

import java.time.LocalDateTime;

public record DatosListaTopicosDTO(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Status status,
        Long autorId,
        Long cursoId
) {

    public DatosListaTopicosDTO(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getId(),
                topico.getCurso().getId()
        );
    }
}
