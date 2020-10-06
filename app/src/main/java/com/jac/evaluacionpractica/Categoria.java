package com.jac.evaluacionpractica;

import java.io.Serializable;

public class Categoria implements Serializable {
    private int codigo;
    private String nombreCategoria;
    private int estado;

    public Categoria() {
    }

    public Categoria(int codigo, String nombreCategoria, int estado) {
        this.codigo = codigo;
        this.nombreCategoria = nombreCategoria;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
