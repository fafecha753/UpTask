package com.example.uptask.Modelo;

public class Usuario {
    private int id;
    private String nombreUsuario;
    private String email;
    private String password;
    private double exp;

    public Usuario(int id, String nombreUsuario, String email, String password) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.exp = 0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
