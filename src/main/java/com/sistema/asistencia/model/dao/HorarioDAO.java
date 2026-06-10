package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.Horario;
import com.sistema.asistencia.model.entity.Seccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {

    public void insertar(Horario horario) throws SQLException {
        
        String sql = "INSERT INTO Horario (id_seccion, dia_semana, hora_inicio, hora_fin, aula) " +
                     "VALUES (?, ?::dia_semana_enum, ?, ?, ?)";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, horario.getIdSeccion());
            ps.setString(2, horario.getDiaSemana());
            ps.setTime(3, horario.getHoraInicio());
            ps.setTime(4, horario.getHoraFin());
            ps.setString(5, horario.getAula());
            
            ps.executeUpdate();
        }
    }

    public List<Horario> listar() throws SQLException {
        List<Horario> lista = new ArrayList<>();
        String sql = "SELECT h.id_horario, h.id_seccion, h.dia_semana::text AS dia, h.hora_inicio, h.hora_fin, h.aula, " +
                     "s.codigo_seccion, c.nombre_curso " +
                     "FROM Horario h " +
                     "INNER JOIN Seccion s ON h.id_seccion = s.id_seccion " +
                     "INNER JOIN Curso c ON s.id_curso = c.id_curso " +
                     "ORDER BY h.id_horario DESC";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Horario h = new Horario();
                h.setIdHorario(rs.getInt("id_horario"));
                h.setIdSeccion(rs.getInt("id_seccion"));
                h.setDiaSemana(rs.getString("dia"));
                h.setHoraInicio(rs.getTime("hora_inicio"));
                h.getHoraFin(); 
                h.setHoraFin(rs.getTime("hora_fin"));
                h.setAula(rs.getString("aula"));
                h.setCodigoSeccion(rs.getString("codigo_seccion"));
                h.setNombreCurso(rs.getString("nombre_curso"));
                lista.add(h);
            }
        }
        return lista;
    }

    
    public List<Seccion> listarSeccionesAux() throws SQLException {
        List<Seccion> lista = new ArrayList<>();
        String sql = "SELECT s.id_seccion, s.codigo_seccion, c.nombre_curso " +
                     "FROM Seccion s INNER JOIN Curso c ON s.id_curso = c.id_curso ORDER BY s.codigo_seccion ASC";
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Seccion s = new Seccion();
                s.setIdSeccion(rs.getInt("id_seccion"));
                s.setCodigoSeccion(rs.getString("codigo_seccion"));
                s.setNombreCurso(rs.getString("nombre_curso"));
                lista.add(s);
            }
        }
        return lista;
    }
}