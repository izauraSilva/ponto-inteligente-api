package com.izatec.pontointeligente.api.entities;


import javax.persistence.*;

import com.izatec.pontointeligente.api.enums.PerfilEnum;

@Entity
@Table(name="usuario")
public class Usuario {

    private Long Id;
    private String senha;
    private String email;
    private PerfilEnum perfil;

    public Usuario() {
    }

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return Id;
    }

    @Column(name = "senha", nullable = false)
    public String getSenha() {
        return senha;
    }

    @Column(name = "nome", nullable = false)
    public String getEmail() {
        return email;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    public PerfilEnum getPerfil() {
        return perfil;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }
}
