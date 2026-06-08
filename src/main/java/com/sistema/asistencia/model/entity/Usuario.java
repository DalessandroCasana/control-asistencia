package com.sistema.asistencia.model.entity;

public class Usuario {
    // Definición del Enum para acoplarlo al ENUM 'rol_usuario' de PostgreSQL
    public enum RolUsuario {
        Administrador, Docente
    }

    private int idUsuario;
    private String codigoTrabajador;
    private String nombres;
    private String apellidos;
    private String correo;
    private String contrasena; // Almacenará el hash de seguridad
    private RolUsuario rol;
    private boolean estado;

    // Constructor Vacío (Obligatorio para frameworks y buenas prácticas)
    public Usuario() {}

    // Constructor Completo
    public Usuario(int idUsuario, String codigoTrabajador, String nombres, String apellidos, 
                   String correo, String contrasena, RolUsuario rol, boolean estado) {
        this.idUsuario = idUsuario;
        this.codigoTrabajador = codigoTrabajador;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
    }

    // Métodos Getters y Setters
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

    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}