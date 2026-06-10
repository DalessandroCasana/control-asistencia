package com.sistema.asistencia.model.entity;

import java.time.LocalTime;

public class DetalleAsistencia {
    private int idDetalle;
    private SesionClase sesion;           // Vinculación a la cabecera de la sesión
    private Estudiante estudiante;         // El alumno evaluado
    private EstadoAsistencia estado;       // Estado: Presente, Ausente, Tardanza, Justificada
    private LocalTime horaMarcado;         // Hora exacta del servidor al registrarse
    private String observacion;            // Nota adicional por alumno (ej: "Llegó 15 min tarde")

    // Constructor Vacío
    public DetalleAsistencia() {}

    // Constructor Completo
    public DetalleAsistencia(int idDetalle, SesionClase sesion, Estudiante estudiante, 
                             EstadoAsistencia estado, LocalTime horaMarcado, String observacion) {
        this.idDetalle = idDetalle;
        this.sesion = sesion;
        this.estudiante = estudiante;
        this.estado = estado;
        this.horaMarcado = horaMarcado;
        this.observacion = observacion;
    }

    // Métodos Getters y Setters
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