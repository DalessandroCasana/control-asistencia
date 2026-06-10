package com.sistema.asistencia.model.entity;

public class Estudiante {
    private int idEstudiante;
    private String codigoEstudiante; // Ej: U23238701
    private String nombres;
    private String apellidos;
    private String correo;
    private String estadoAcademico; // 'Regular' o 'Inhabilitado'

    public Estudiante() {}

    public Estudiante(int idEstudiante, String codigoEstudiante, String nombres, 
                      String apellidos, String correo, String estadoAcademico) {
        this.idEstudiante = idEstudiante;
        this.codigoEstudiante = codigoEstudiante;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.estadoAcademico = estadoAcademico;
    }

    // Getters y Setters
    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getEstadoAcademico() { return estadoAcademico; }
    public void setEstadoAcademico(String estadoAcademico) { this.estadoAcademico = estadoAcademico; }
}