package com.izatec.pontointeligente.api.services;

import java.util.Optional;

import com.izatec.pontointeligente.api.entities.Funcionario;

public interface FuncionarioService {

    /**
     * Persistir funcionario
     *
     * @param funcionario
     * @return
     */
    Funcionario persistir(Funcionario funcionario);

    /**
     * Buscar funcionario por cpf
     *
     * @param cpf
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     * Buscar funcionario por email
     *
     * @param email
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     * Busca e retorna um funcionário por ID.
     *
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorId(Long id);

}
