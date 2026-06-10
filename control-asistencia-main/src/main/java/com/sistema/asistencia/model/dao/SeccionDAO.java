package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.Seccion;
import com.sistema.asistencia.model.entity.Curso;
import com.sistema.asistencia.model.entity.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeccionDAO {

    // Método para insertar una nueva sección en PostgreSQL
    public void insertar(Seccion seccion) throws SQLException {
        String sql = "INSERT INTO Seccion (id_curso, id_docente, codigo_seccion, periodo_academico) VALUES (?, ?, ?, ?)";
        try (Connection cn = ConexionBD.obtenerConexion(); // CORREGIDO: obtenerConexion()
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, seccion.getIdCurso());
            ps.setInt(2, seccion.getIdDocente());
            ps.setString(3, seccion.getCodigoSeccion());
            ps.setString(4, seccion.getPeriodoAcademico());
            ps.executeUpdate();
        }
    }

    // Método relacional para cargar la JTable con los nombres legibles
    public List<Seccion> listar() throws SQLException {
        List<Seccion> lista = new ArrayList<>();
        String sql = "SELECT s.id_seccion, s.id_curso, s.id_docente, s.codigo_seccion, s.periodo_academico, " +
                     "c.nombre_curso, u.nombres || ' ' || u.apellidos AS docente " +
                     "FROM Seccion s " +
                     "INNER JOIN Curso c ON s.id_curso = c.id_curso " +
                     "INNER JOIN Usuario u ON s.id_docente = u.id_usuario " +
                     "ORDER BY s.id_seccion DESC";

        try (Connection cn = ConexionBD.obtenerConexion(); // CORREGIDO: obtenerConexion()
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Seccion s = new Seccion();
                s.setIdSeccion(rs.getInt("id_seccion"));
                s.setIdCurso(rs.getInt("id_curso"));
                s.setIdDocente(rs.getInt("id_docente"));
                s.setCodigoSeccion(rs.getString("codigo_seccion"));
                s.setPeriodoAcademico(rs.getString("periodo_academico"));
                s.setNombreCurso(rs.getString("nombre_curso"));
                s.setNombreDocente(rs.getString("docente"));
                lista.add(s);
            }
        }
        return lista;
    }

    // MÉTODO AUXILIAR: Listar Cursos para llenar el JComboBox de la Vista
    public List<Curso> listarCursosAux() throws SQLException {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT id_curso, codigo_curso, nombre_curso FROM Curso ORDER BY nombre_curso ASC";
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Curso c = new Curso();
                c.setIdCurso(rs.getInt("id_curso"));
                c.setCodigoCurso(rs.getString("codigo_curso"));
                c.setNombreCurso(rs.getString("nombre_curso"));
                lista.add(c);
            }
        }
        return lista;
    }

    // MÉTODO AUXILIAR: Listar Usuarios con rol 'Docente' para llenar el JComboBox de la Vista
    public List<Usuario> listarDocentesAux() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        // Filtramos por tu tipo ENUM convertido a TEXT para evitar conflictos relacionales
        String sql = "SELECT id_usuario, nombres, apellidos FROM Usuario WHERE rol::text = 'Docente' AND estado = TRUE ORDER BY apellidos ASC";
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                lista.add(u);
            }
        }
        return lista;
    }
}