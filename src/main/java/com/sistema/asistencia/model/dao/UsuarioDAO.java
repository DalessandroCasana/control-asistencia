package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.Usuario;
import com.google.common.base.Preconditions; // Uso de Google Guava para validaciones
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Inicializamos el Logger de Logback para registrar eventos de autenticación
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    /**
     * Valida las credenciales de acceso de un usuario (Admin o Docente).
     * @param correo Correo institucional ingresado.
     * @param contrasena Contraseña (en un sistema real se compararía el Hash).
     * @return Objeto Usuario completo si las credenciales son correctas, null en caso contrario.
     */
    public Usuario login(String correo, String contrasena) {
        // REGLA DE SEGURIDAD (Google Guava): Validamos precondiciones críticas antes de consultar la BD
        try {
            Preconditions.checkNotNull(correo, "El correo de inicio de sesión no puede ser nulo.");
            Preconditions.checkNotNull(contrasena, "La contraseña de inicio de sesión no puede ser nula.");
        } catch (NullPointerException e) {
            logger.warn("Intento de login fallido debido a parámetros nulos: {}", e.getMessage());
            return null;
        }

        // Consulta SQL usando PreparedStatement para evitar ataques de Inyección SQL
        String sql = "SELECT id_usuario, codigo_trabajador, nombres, apellidos, correo, contrasena, rol, estado " +
                     "FROM Usuario WHERE correo = ? AND contrasena = ? AND estado = TRUE";

        logger.info("Iniciando intento de autenticación para el correo: {}", correo);

        // Uso de Try-with-resources para asegurar el cierre automático de conexiones y liberar memoria
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, correo.trim());
            ps.setString(2, contrasena); // Nota: En la siguiente fase implementaremos el hash

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si se encuentra el usuario, instanciamos el objeto de la entidad mapeando las columnas
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setCodigoTrabajador(rs.getString("codigo_trabajador"));
                    usuario.setNombres(rs.getString("nombres"));
                    usuario.setApellidos(rs.getString("apellidos"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    
                    // Convertimos el String/ENUM de PostgreSQL al Enum nativo de Java de forma segura
                    String rolBD = rs.getString("rol");
                    usuario.setRol(Usuario.RolUsuario.valueOf(rolBD));
                    
                    usuario.setEstado(rs.getBoolean("estado"));

                    logger.info("Autenticación exitosa. Usuario: {} {} con Rol: {}", 
                                usuario.getNombres(), usuario.getApellidos(), usuario.getRol());
                    return usuario;
                } else {
                    logger.warn("Intento de autenticación fallido para el correo: {}. Credenciales incorrectas o usuario inactivo.", correo);
                }
            }

        } catch (SQLException e) {
            logger.error("Error crítico en la base de datos durante el proceso de Login para el correo: " + correo, e);
        }

        return null; // Retorna null si las credenciales no coincidieron o si ocurrió un error
    }
}