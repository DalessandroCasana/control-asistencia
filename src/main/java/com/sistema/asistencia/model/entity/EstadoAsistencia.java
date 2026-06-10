package com.sistema.asistencia.model.entity;

public class EstadoAsistencia {
    private int idEstado;
    private String nombreEstado; 
    private double valorAsistencia; 

    public EstadoAsistencia() {}

    public EstadoAsistencia(int idEstado, String nombreEstado, double valorAsistencia) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
        this.valorAsistencia = valorAsistencia;
    }

    
    public int getIdEstado() { return idEstado; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }

    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }

    public double getValorAsistencia() { return valorAsistencia; }
    public void setValorAsistencia(double valorAsistencia) { this.valorAsistencia = valorAsistencia; }
}