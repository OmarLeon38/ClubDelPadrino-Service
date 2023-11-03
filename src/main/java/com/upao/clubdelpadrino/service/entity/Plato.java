package com.upao.clubdelpadrino.service.entity;

import javax.persistence.*;

@Entity
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100)
    private String nombre;
    @OneToOne
    private Documento foto;
    @Column
    private Double precio;
    @Column
    private int stock;
    @Column(length = 500)
    private String descripcionPlato;
    @OneToOne
    private Categoria categoria;
    @Column
    private boolean vigencia;
    @Column
    private boolean recomendado;

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

    public Documento getFoto() {
        return foto;
    }

    public void setFoto(Documento foto) {
        this.foto = foto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcionPlatillo() {
        return descripcionPlato;
    }

    public void setDescripcionPlatillo(String descripcionPlatillo) {
        this.descripcionPlato = descripcionPlatillo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isVigencia() {
        return vigencia;
    }

    public void setVigencia(boolean vigencia) {
        this.vigencia = vigencia;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }
}
