package com.example.appparcialkquiel_lsaldana_crodriguez.Entidades;

public class Usuario {
    private String nombre;
    private String correo;
    private String password;
    private String tipo;

    public Usuario(String nombre, String correo, String password, String tipo){
       this.nombre=nombre;
       this.correo=correo;
       this.password=password;
       this.tipo=tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public String getTipo() {
        return tipo;
    }
}
