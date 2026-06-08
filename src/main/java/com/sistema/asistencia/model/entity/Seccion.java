package com.sistema.asistencia.model.entity;

public class Seccion {
    private int idSeccion;
    private String codigoSeccion;
    private int idCurso;
    private int idDocente;
    private String aula;
    
    // Atributos auxiliares para simplificar el renderizado en la JTable
    private String nombreCurso;
    private String nombreDocente;

    public Seccion() {}

    public Seccion(int idSeccion, String codigoSeccion, int idCurso, int idDocente, String aula) {
        this.idSeccion = idSeccion;
        this.codigoSeccion = codigoSeccion;
        this.idCurso = idCurso;
        this.idDocente = idDocente;
        this.aula = aula;
    }

    // Getters y Setters
    public int getIdSeccion() { return idSeccion; }
    public void setIdSeccion(int idSeccion) { this.idSeccion = idSeccion; }

    public String getCodigoSeccion() { return codigoSeccion; }
    public void setCodigoSeccion(String codigoSeccion) { this.codigoSeccion = codigoSeccion; }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public int getIdDocente() { return idDocente; }
    public void setIdDocente(int idDocente) { this.idDocente = idDocente; }

    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    public String getNombreDocente() { return nombreDocente; }
    public void setNombreDocente(String nombreDocente) { this.nombreDocente = nombreDocente; }
}