package com.sistema.asistencia.model.entity;

public class Usuario {
    private int idUsuario;
    private String codigoTrabajador;
    private String nombres;
    private String apellidos;
    private String correo;
    private String contrasena;
    private String rol;
    private boolean estado;

    public Usuario() {}

    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getCodigoTrabajador() { return codigoTrabajador; }
    public void setCodigoTrabajador(String codigoTrabajador) { this.codigoTrabajador = codigoTrabajador; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}