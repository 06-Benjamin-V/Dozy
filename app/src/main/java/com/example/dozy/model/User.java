package com.example.dozy.model;

public class User {
    private String username;
    private String telefono;
    private String correo;

    public User() {}
    public User(String username, String telefono, String correo) {
        this.username = username;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
