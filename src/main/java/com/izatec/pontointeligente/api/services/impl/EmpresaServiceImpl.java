package com.izatec.pontointeligente.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izatec.pontointeligente.api.entities.Empresa;
import com.izatec.pontointeligente.api.repository.EmpresaRepository;
import com.izatec.pontointeligente.api.services.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> buscarPorCnpj(String cnpj){
        log.info("Buscar empresa por cnpj {}", cnpj);
        return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
    }

    @Override
    public Empresa persistir(Empresa empresa) {
        log.info("Persistindo empresa {}", empresa);
        return this.empresaRepository.save(empresa);
    }

    @Override
    public List<Empresa> findAll() {
        return this.empresaRepository.findAll();
    }

}
