package com.sistema.asistencia.model.entity;

public class Seccion {
    private int idSeccion;
    private int idCurso;
    private int idDocente;
    private String codigoSeccion;
    private String periodoAcademico;
    
    // Atributos adicionales para facilitar el renderizado en la interfaz gráfica
    private String nombreCurso;
    private String nombreDocente;

    public Seccion() {}

    public Seccion(int idSeccion, int idCurso, int idDocente, String codigoSeccion, String periodoAcademico) {
        this.idSeccion = idSeccion;
        this.idCurso = idCurso;
        this.idDocente = idDocente;
        this.codigoSeccion = codigoSeccion;
        this.periodoAcademico = periodoAcademico;
    }

    // Getters y Setters
    public int getIdSeccion() { return idSeccion; }
    public void setIdSeccion(int idSeccion) { this.idSeccion = idSeccion; }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public int getIdDocente() { return idDocente; }
    public void setIdDocente(int idDocente) { this.idDocente = idDocente; }

    public String getCodigoSeccion() { return codigoSeccion; }
    public void setCodigoSeccion(String codigoSeccion) { this.codigoSeccion = codigoSeccion; }

    public String getPeriodoAcademico() { return periodoAcademico; }
    public void setPeriodoAcademico(String periodoAcademico) { this.periodoAcademico = periodoAcademico; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    public String getNombreDocente() { return nombreDocente; }
    public void setNombreDocente(String nombreDocente) { this.nombreDocente = nombreDocente; }
}