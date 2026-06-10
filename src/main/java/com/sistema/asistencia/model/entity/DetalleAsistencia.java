package com.sistema.asistencia.model.entity;

import java.time.LocalTime;

public class DetalleAsistencia {
    private int idDetalle;
    private SesionClase sesion;           
    private Estudiante estudiante;         
    private EstadoAsistencia estado;       
    private LocalTime horaMarcado;         
    private String observacion;            

    
    public DetalleAsistencia() {}

    
    public DetalleAsistencia(int idDetalle, SesionClase sesion, Estudiante estudiante, 
                             EstadoAsistencia estado, LocalTime horaMarcado, String observacion) {
        this.idDetalle = idDetalle;
        this.sesion = sesion;
        this.estudiante = estudiante;
        this.estado = estado;
        this.horaMarcado = horaMarcado;
        this.observacion = observacion;
    }

    
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public SesionClase getSesion() { return sesion; }
    public void setSesion(SesionClase sesion) { this.sesion = sesion; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public EstadoAsistencia getEstado() { return estado; }
    public void setEstado(EstadoAsistencia estado) { this.estado = estado; }

    public LocalTime getHoraMarcado() { return horaMarcado; }
    public void setHoraMarcado(LocalTime horaMarcado) { this.horaMarcado = horaMarcado; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}