package com.izatec.pontointeligente.api.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;

@SuppressWarnings("deprecation")
public class CadastroPJDto {

    private Long Id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String razaoSocial;
    private String cnpj;

    public CadastroPJDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @NotEmpty(message="Nome não pode estar vazio.")
    @Length(min=5, max=200, message = "Nome deve conter entre 5 e 200 caracteres. ")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotEmpty(message="Email não pode estar vazio.")
    @Length(min=5, max=200, message = "Email deve conter entre 5 e 200 caracteres. ")
    @Email(message = "Email ivalido.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message="Senha não pode estar vazio.")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @NotEmpty(message="CPF não pode estar vazio.")
    @CPF(message = "CPF invalido.")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotEmpty(message="Razao Social não pode estar vazio.")
    @Length(min=5, max=200, message = "Razao social deve conter entre 5 e 200 caracteres. ")
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

    @Override
    public String toString() {
        return "CadastroPJDto{" +
                "Id=" + Id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
