package com.izatec.pontointeligente.api.dtos;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;

@SuppressWarnings("deprecation")
public class EmpresaDto {

    private Long Id;
    private String razaoSocial;
    private String cnpj;

    public EmpresaDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @NotEmpty(message="Razão Social não pode estar vazio.")
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @NotEmpty(message="CNPJ não pode estar vazio.")
    @CNPJ(message = "CNPJ invalido")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
