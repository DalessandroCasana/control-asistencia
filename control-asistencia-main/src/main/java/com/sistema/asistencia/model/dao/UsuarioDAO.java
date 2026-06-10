package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para insertar un usuario respetando tu ENUM y columnas reales
    public void insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (codigo_trabajador, nombres, apellidos, correo, contrasena, rol, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?::rol_usuario, ?)";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getCodigoTrabajador());
            ps.setString(2, usuario.getNombres());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getContrasena());
            ps.setString(6, usuario.getRol()); // Debe enviarse 'Administrador' o 'Docente'
            ps.setBoolean(7, usuario.isEstado());
            
            ps.executeUpdate();
        }
    }

    // Método para listar todos los usuarios del sistema
    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, codigo_trabajador, nombres, apellidos, correo, contrasena, " +
                     "rol::text AS rol_texto, estado FROM Usuario ORDER BY id_usuario DESC";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setCodigoTrabajador(rs.getString("codigo_trabajador"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rs.getString("rol_texto")); // Captura el ENUM como texto limpio
                u.setEstado(rs.getBoolean("estado"));
                lista.add(u);
            }
        }
        return lista;
    }
}