package com.challenge.foroHub.controller;

import com.challenge.foroHub.domain.usuarios.Usuario;
import com.challenge.foroHub.domain.usuarios.dto.DatosAutenticacion;
import com.challenge.foroHub.infra.security.DatosTokenJWT;
import com.challenge.foroHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity iniciarSesion(@Valid @RequestBody DatosAutenticacion datosAutenticacion){
        var authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacion.correoElectronico(),
                datosAutenticacion.contrasena()
        );
        var auth = manager.authenticate(authToken);
        var usuario = (Usuario) auth.getPrincipal();
        var token = tokenService.generarToken(usuario);
        return ResponseEntity.ok(new DatosTokenJWT(token));
    }
}
