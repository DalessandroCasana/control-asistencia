package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.Curso; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    
    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT id_curso, codigo_curso, nombre_curso, creditos, horas_totales FROM curso ORDER BY nombre_curso ASC";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Curso c = new Curso();
                c.setIdCurso(rs.getInt("id_curso"));
                c.setCodigo(rs.getString("codigo_curso"));
                c.setNombre(rs.getString("nombre_curso"));
                c.setCreditos(rs.getInt("creditos"));
                c.setHorasTotales(rs.getInt("horas_totales"));
                lista.add(c);
            }
        } catch (SQLException ex) {
            System.err.println("Error en CursoDAO.listar: " + ex.getMessage());
        }
        return lista;
    }

    
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