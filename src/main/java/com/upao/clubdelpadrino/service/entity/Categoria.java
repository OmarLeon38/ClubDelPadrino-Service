package com.upao.clubdelpadrino.service.entity;

import javax.persistence.*;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100)
    private String nombre;
    @Column
    private boolean Vigencia;
    @OneToOne
    private Documento foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isVigencia() {
        return Vigencia;
    }

    public void setVigencia(boolean vigencia) {
        Vigencia = vigencia;
    }

    public Documento getFoto() {
        return foto;
    }

    public void setFoto(Documento foto) {
        this.foto = foto;
    }
}
