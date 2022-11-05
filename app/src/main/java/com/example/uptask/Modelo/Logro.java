package com.example.uptask.Modelo;

public class Logro {

    private String nombre;
    private String descripcion;
    private boolean bloqueado;

    public Logro() {
    }

    public Logro(String nombre, String descripcion, boolean bloqueado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.bloqueado = bloqueado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
