package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.Seccion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeccionDAO {

    // 1. LISTAR SECCIONES CON INNER JOIN (Para ver nombres en lugar de IDs numéricos)
    public List<Seccion> listar() {
        List<Seccion> lista = new ArrayList<>();
        String sql = "SELECT s.id_seccion, s.codigo_seccion, s.id_curso, s.id_docente, s.aula, " +
                     "       c.nombre AS curso_nombre, u.nombres || ' ' || u.apellidos AS docente_nombre " +
                     "FROM seccion s " +
                     "INNER JOIN curso c ON s.id_curso = c.id_curso " +
                     "INNER JOIN usuario u ON s.id_docente = u.id_usuario " +
                     "ORDER BY s.codigo_seccion ASC";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Seccion s = new Seccion();
                s.setIdSeccion(rs.getInt("id_seccion"));
                s.setCodigoSeccion(rs.getString("codigo_seccion"));
                s.setIdCurso(rs.getInt("id_curso"));
                s.setIdDocente(rs.getInt("id_docente"));
                s.setAula(rs.getString("aula"));
                s.setNombreCurso(rs.getString("curso_nombre"));
                s.setNombreDocente(rs.getString("docente_nombre"));
                lista.add(s);
            }
        } catch (SQLException ex) {
            System.err.println("Error en SeccionDAO.listar: " + ex.getMessage());
        }
        return lista;
    }

    // 2. REGISTRAR SECCIÓN (INSERT)
    public boolean registrar(Seccion seccion) {
        String sql = "INSERT INTO seccion (codigo_seccion, id_curso, id_docente, aula) VALUES (?, ?, ?, ?)";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, seccion.getCodigoSeccion());
            ps.setInt(2, seccion.getIdCurso());
            ps.setInt(3, seccion.getIdDocente());
            ps.setString(4, seccion.getAula());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error en SeccionDAO.registrar: " + ex.getMessage());
            return false;
        }
    }

    // 3. MODIFICAR SECCIÓN (UPDATE)
    public boolean modificar(Seccion seccion) {
        String sql = "UPDATE seccion SET codigo_seccion = ?, id_curso = ?, id_docente = ?, aula = ? WHERE id_seccion = ?";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, seccion.getCodigoSeccion());
            ps.setInt(2, seccion.getIdCurso());
            ps.setInt(3, seccion.getIdDocente());
            ps.setString(4, seccion.getAula());
            ps.setInt(5, seccion.getIdSeccion());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error en SeccionDAO.modificar: " + ex.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR SECCIÓN (DELETE)
    public boolean eliminar(int idSeccion) {
        String sql = "DELETE FROM seccion WHERE id_seccion = ?";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, idSeccion);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error en SeccionDAO.eliminar: " + ex.getMessage());
            return false;
        }
    }
    // 5. LISTAR SECCIONES DEL DOCENTE FILTRADAS POR EL DÍA ACTUAL
    public List<Seccion> listarPorDocenteYDia(int idDocente, String diaSemana) {
        List<Seccion> lista = new ArrayList<>();
        String sql = "SELECT s.id_seccion, s.codigo_seccion, c.nombre AS curso_nombre, s.aula, h.hora_inicio, h.hora_fin " +
                 "FROM seccion s " +
                 "INNER JOIN curso c ON s.id_curso = c.id_curso " +
                 "INNER JOIN horario h ON s.id_seccion = h.id_seccion " +
                 "WHERE s.id_docente = ? AND h.dia = ? " +
                 "ORDER BY h.hora_inicio ASC";

        try (Connection cn = com.sistema.asistencia.config.ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, idDocente);
            ps.setString(2, diaSemana);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Seccion s = new Seccion();
                s.setIdSeccion(rs.getInt("id_seccion"));
                s.setCodigoSeccion(rs.getString("codigo_seccion"));
                s.setNombreCurso(rs.getString("curso_nombre"));
                s.setAula(rs.getString("aula"));
                // Aprovechamos los setters auxiliares para pasar las horas del join temporal
                s.setNombreDocente(rs.getTime("hora_inicio").toString().substring(0, 5) + " - " + 
                                   rs.getTime("hora_fin").toString().substring(0, 5)); 
                lista.add(s);
            }
        }
    } catch (SQLException ex) {
        System.err.println("Error en SeccionDAO.listarPorDocenteYDia: " + ex.getMessage());
    }
    return lista;
}
}