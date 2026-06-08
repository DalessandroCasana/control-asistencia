package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.Curso; // Apunta correctamente a la subcarpeta entity
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    // 1. LISTAR TODOS LOS CURSOS
    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT id_curso, codigo, nombre, creditos, horas_totales FROM curso ORDER BY nombre ASC";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Curso c = new Curso();
                c.setIdCurso(rs.getInt("id_curso"));
                c.setCodigo(rs.getString("codigo"));
                c.setNombre(rs.getString("nombre"));
                c.setCreditos(rs.getInt("creditos"));
                c.setHorasTotales(rs.getInt("horas_totales"));
                lista.add(c);
            }
        } catch (SQLException ex) {
            System.err.println("Error en CursoDAO.listar: " + ex.getMessage());
        }
        return lista;
    }

    // 2. REGISTRAR NUEVO CURSO (INSERT)
    public boolean registrar(Curso curso) {
        String sql = "INSERT INTO curso (codigo, nombre, creditos, horas_totales) VALUES (?, ?, ?, ?)";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, curso.getCodigo());
            ps.setString(2, curso.getNombre());
            ps.setInt(3, curso.getCreditos());
            ps.setInt(4, curso.getHorasTotales());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error en CursoDAO.registrar: " + ex.getMessage());
            return false;
        }
    }

    // 3. MODIFICAR CURSO EXISTENTE (UPDATE)
    public boolean modificar(Curso curso) {
        String sql = "UPDATE curso SET codigo = ?, nombre = ?, creditos = ?, horas_totales = ? WHERE id_curso = ?";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, curso.getCodigo());
            ps.setString(2, curso.getNombre());
            ps.setInt(3, curso.getCreditos());
            ps.setInt(4, curso.getHorasTotales());
            ps.setInt(5, curso.getIdCurso());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error en CursoDAO.modificar: " + ex.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR CURSO (DELETE)
    public boolean eliminar(int idCurso) {
        String sql = "DELETE FROM curso WHERE id_curso = ?";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, idCurso);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error en CursoDAO.eliminar: " + ex.getMessage());
            return false;
        }
    }
}