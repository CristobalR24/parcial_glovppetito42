package com.example.appparcialkquiel_lsaldana_crodriguez.Entidades;

public class Receta {
    private String imagen;
    private String titulo;
    private String ingredientes;
    private String preparacion;

    public Receta(String imagen, String titulo, String ingredientes, String preparacion){
        this.imagen=imagen;
        this.titulo=titulo;
        this.ingredientes=ingredientes;
        this.preparacion=preparacion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getPreparacion() {
        return preparacion;
    }
}
