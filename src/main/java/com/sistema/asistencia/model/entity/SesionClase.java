package com.sistema.asistencia.model.entity;

import java.time.LocalDate;

public class SesionClase {
    // Enum para acoplarlo al tipo ENUM 'estado_sesion_enum' de PostgreSQL
    public enum EstadoSesion {
        Abierta, Cerrada
    }

    private int idSesion;
    private Seccion seccion;   // Relación orientada a objetos con la Sección
    private Horario horario;   // Relación orientada a objetos con el Horario específico
    private LocalDate fecha;   // Ej: 2026-06-08
    private String temaDictado; // Registro pedagógico de la clase
    private EstadoSesion estadoSesion; // Abierta o Cerrada

    // Constructor Vacío
    public SesionClase() {}

    // Constructor Completo
    public SesionClase(int idSesion, Seccion seccion, Horario horario, LocalDate fecha, 
                       String temaDictado, EstadoSesion estadoSesion) {
        this.idSesion = idSesion;
        this.seccion = seccion;
        this.horario = horario;
        this.fecha = fecha;
        this.temaDictado = temaDictado;
        this.estadoSesion = estadoSesion;
    }

    // Métodos Getters y Setters
    public int getIdSesion() { return idSesion; }
    public void setIdSesion(int idSesion) { this.idSesion = idSesion; }

    public Seccion getSeccion() { return seccion; }
    public void setSeccion(Seccion seccion) { this.seccion = seccion; }

    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTemaDictado() { return temaDictado; }
    public void setTemaDictado(String temaDictado) { this.temaDictado = temaDictado; }

    public EstadoSesion getEstadoSesion() { return estadoSesion; }
    public void setEstadoSesion(EstadoSesion estadoSesion) { this.estadoSesion = estadoSesion; }
}