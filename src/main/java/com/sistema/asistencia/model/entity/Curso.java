package com.sistema.asistencia.model.entity;

public class Curso {
    private int idCurso;
    private String codigo;
    private String nombre;
    private int creditos;
    private int horasTotales; // Verifica que termine exactamente en 'Totales'

    // Constructor Vacío (Obligatorio para que el DAO instancie filas)
    public Curso() {
    }

    // Constructor Lleno
    public Curso(int idCurso, String codigo, String nombre, int creditos, int horasTotales) {
        this.idCurso = idCurso;
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.horasTotales = horasTotales;
    }

    // ==========================================
    // GETTERS Y SETTERS (Estrictos para el DAO)
    // ==========================================

    public int getIdCurso() { 
        return idCurso; 
    }
    
    public void setIdCurso(int idCurso) { 
        this.idCurso = idCurso; 
    }

    public String getCodigo() { 
        return codigo; 
    }
    
    public void setCodigo(String codigo) { 
        this.codigo = codigo; 
    }

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public int getCreditos() { 
        return creditos; 
    }
    
    public void setCreditos(int creditos) { 
        this.creditos = creditos; 
    }

    public int getHorasTotales() { 
        return horasTotales; 
    }
    
    public void setHorasTotales(int horasTotales) { 
        this.horasTotales = horasTotales; 
    }
}