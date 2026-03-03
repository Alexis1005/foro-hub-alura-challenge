package com.challenge.foroHub.repository;

import com.challenge.foroHub.domain.topicos.Topico;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
