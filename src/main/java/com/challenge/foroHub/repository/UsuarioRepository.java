package com.challenge.foroHub.repository;

import com.challenge.foroHub.domain.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
