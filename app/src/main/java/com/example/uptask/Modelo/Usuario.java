package com.example.uptask.Modelo;



public class Usuario {
    private String nombreUsuario;
    private String email;
    private String password;
    private String avatar;
    private int exp;

    public Usuario(String nombreUsuario, String email, String password, String avatar, int exp) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.exp = exp;
    }

    public Usuario() {
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
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
