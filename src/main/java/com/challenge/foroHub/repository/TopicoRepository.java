package com.challenge.foroHub.repository;

import com.challenge.foroHub.domain.topicos.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("SELECT t FROM Topico t WHERE t.titulo = :titulo AND YEAR(t.fechaCreacion) = :anio")
    Page <Topico> findByNombreCursoAndAnio(String titulo, Integer anio, Pageable paginacion);
}
