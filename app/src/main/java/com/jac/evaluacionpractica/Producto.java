package com.jac.evaluacionpractica;

import java.security.Timestamp;

public class Producto { int id_producto;
    String nom_producto;
    String des_producto;
    float stock;
    float precio;
    String unidad_de_medida;
    int estado_producto;
    int codigo;
    Timestamp fecha_entrada;


    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {this.id_producto = id_producto;}

    public String getNom_producto() {return nom_producto;}
    public  void  setNom_producto(String nom_producto) {this.nom_producto = nom_producto;}

    public String getDes_producto() {return des_producto;}
    public void  setDes_producto(String des_producto) {this.des_producto = des_producto;}

    public float getStock() {return stock;}
    public void setStock(float stock) {this.stock = stock;}

    public float getPrecio() {return stock;}
    public void setPrecio(float precio) {this.precio = precio;}

    public String getUnidad_de_medida() {return nom_producto;}
    public  void  setUnidad_de_medida(String unidad_de_medida) {this.unidad_de_medida = unidad_de_medida ;}

    public int getEstado_producto() {return estado_producto;}
    public void setEstado_producto(int estado_producto) {this.estado_producto = estado_producto;}

    public int getCodigo() {return codigo;}
    public void setCodigo(int codigo) {this.codigo = codigo;}

    public Timestamp getFecha_entrada() {return fecha_entrada;}
    public void setFecha_entrada (Timestamp fecha_entrada) {this.fecha_entrada = fecha_entrada;}

}