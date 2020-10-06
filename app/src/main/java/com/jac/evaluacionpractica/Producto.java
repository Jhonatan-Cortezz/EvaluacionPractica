package com.jac.evaluacionpractica;

import android.database.sqlite.SQLiteDatabase;
import java.io.Serializable;

public class Producto implements Serializable {
    private int codigo;
    private String nombreProducto;
    private String descripcion;
    private double stock;
    private double precio;
    private String unidadMedida;
    private int estadoProd;
    private String categoria;


    public Producto() {
    }

    public Producto(int codigo, String nombreProducto, String descripcion, double stock, double precio, String unidadMedida, int estadoProd, String categoria) {
        this.codigo = codigo;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
        this.estadoProd = estadoProd;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public int getEstadoProd() {
        return estadoProd;
    }

    public void setEstadoProd(int estadoProd) {
        this.estadoProd = estadoProd;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}