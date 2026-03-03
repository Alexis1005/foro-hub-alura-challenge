package com.challenge.foroHub.repository;

import com.challenge.foroHub.domain.respuestas.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
}
