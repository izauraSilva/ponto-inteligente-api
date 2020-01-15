package com.izatec.pontointeligente.api.services;

import java.util.List;
import java.util.Optional;

import com.izatec.pontointeligente.api.entities.Empresa;

public interface EmpresaService {

    /**
     * Retorna uma empresa dado cnpj
     *
     * @param cnpj
     * @return Optional<Empresa>
     */
    Optional<Empresa> buscarPorCnpj(String cnpj);

    /**
     * Cadastra nova empresa
     *
     * @param empresa
     * @return Empresa
     */
    Empresa persistir(Empresa empresa);

    /**
     *
     * @return List<Empresa>
     */
    List<Empresa> findAll();
}
