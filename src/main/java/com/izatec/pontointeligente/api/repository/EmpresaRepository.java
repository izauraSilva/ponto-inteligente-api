package com.izatec.pontointeligente.api.repository;


import com.izatec.pontointeligente.api.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    //@Transactional(readOnly = true)
    Empresa findByCnpj(String cnpj);

}
