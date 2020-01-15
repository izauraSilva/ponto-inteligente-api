package com.izatec.pontointeligente.api.services;

import java.util.Optional;

import com.izatec.pontointeligente.api.entities.Usuario;

/**
 * @author Izaura Silva
 */
public interface UsuarioService {

    /**
     * busca e retorna um usuário dado o e-mail
     *
     * @param email
     * @return Optional<Usuario>
     */
    Optional<Usuario> buscaorEmail(String email);
}
