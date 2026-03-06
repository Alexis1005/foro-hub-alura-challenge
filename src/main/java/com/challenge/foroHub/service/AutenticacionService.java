package com.challenge.foroHub.service;

import com.challenge.foroHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

//Interfaz para buscar usuarios en la autenticación(USERDETAILSSERVICE)
public class AutenticacionService implements UserDetailsService {

@Autowired
private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreoElectronico(username);
    }
}
