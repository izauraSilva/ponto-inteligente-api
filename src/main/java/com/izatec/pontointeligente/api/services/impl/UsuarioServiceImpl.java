package com.izatec.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izatec.pontointeligente.api.entities.Usuario;
import com.izatec.pontointeligente.api.repository.UsuarioRepository;
import com.izatec.pontointeligente.api.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> buscaorEmail(String email) {
        log.info("Buscar usuario por email {}", email);
        return Optional.ofNullable(usuarioRepository.findByEmail(email));
    }
}
