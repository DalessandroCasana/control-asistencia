package com.sistema.asistencia.model.entity;

public class Horario {
    private int idHorario;
    private int idSeccion;
    private String dia;
    private String horaInicio; // Formato HH:mm
    private String horaFin;    // Formato HH:mm
    
    // Atributo auxiliar para la interfaz gráfica
    private String codigoSeccion;

    public Horario() {}

    public Horario(int idHorario, int idSeccion, String dia, String horaInicio, String horaFin) {
        this.idHorario = idHorario;
        this.idSeccion = idSeccion;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y Setters
    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }

    public int getIdSeccion() { return idSeccion; }
    public void setIdSeccion(int idSeccion) { this.idSeccion = idSeccion; }

    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public String getCodigoSeccion() { return codigoSeccion; }
    public void setCodigoSeccion(String codigoSeccion) { this.codigoSeccion = codigoSeccion; }
}