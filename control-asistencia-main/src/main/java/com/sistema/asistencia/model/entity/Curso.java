package com.sistema.asistencia.model.entity;

public class Curso {
    private int idCurso;
    private String codigoCurso;
    private String nombreCurso;
    private int creditos;

    public Curso() {}

    public Curso(int idCurso, String codigoCurso, String nombreCurso, int creditos) {
        this.idCurso = idCurso;
        this.codigoCurso = codigoCurso;
        this.nombreCurso = nombreCurso;
        this.creditos = creditos;
    }

    // Métodos Getters y Setters exactos
    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

  
}