package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.SesionClase;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;

public class SesionClaseDAO {

    private static final Logger logger = LoggerFactory.getLogger(SesionClaseDAO.class);

    
    public boolean existeSesion(int idSeccion, int idHorario, LocalDate fecha) {
        Preconditions.checkArgument(idSeccion > 0, "ID de sección inválido.");
        Preconditions.checkArgument(idHorario > 0, "ID de horario inválido.");
        Preconditions.checkNotNull(fecha, "La fecha no puede ser nula.");

        String sql = "SELECT COUNT(*) FROM Sesion_Clase WHERE id_seccion = ? AND id_horario = ? AND fecha = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idSeccion);
            ps.setInt(2, idHorario);
            ps.setDate(3, Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al verificar la existencia de la sesión clase", e);
        }
        return false;
    }

    
    public int registrarSesion(SesionClase sesion) {
        Preconditions.checkNotNull(sesion, "El objeto SesionClase no puede ser nulo.");
        
        
        if (existeSesion(sesion.getSeccion().getIdSeccion(), sesion.getHorario().getIdHorario(), sesion.getFecha())) {
            logger.warn("Bloqueo de seguridad: Intento de duplicar sesión para la sección ID {} en la fecha {}", 
                        sesion.getSeccion().getIdSeccion(), sesion.getFecha());
            return -1; 
        }

        String sql = "INSERT INTO Sesion_Clase (id_seccion, id_horario, fecha, tema_dictado, estado_sesion) " +
                     "VALUES (?, ?, ?, ?, ?::estado_sesion_enum)";

        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, sesion.getSeccion().getIdSeccion());
            ps.setInt(2, sesion.getHorario().getIdHorario());
            ps.setDate(3, Date.valueOf(sesion.getFecha()));
            ps.setString(4, sesion.getTemaDictado());
            ps.setString(5, sesion.getEstadoSesion().name()); 

            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGenerado = generatedKeys.getInt(1);
                        logger.info("Nueva sesión de clase registrada exitosamente. ID Sesión: {}", idGenerado);
                        return idGenerado;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error crítico al registrar la sesión de clase", e);
        }
        return -1;
    }

    
    public void cerrarSesion(int idSesion) {
        Preconditions.checkArgument(idSesion > 0, "ID de sesión inválido.");

        String sql = "UPDATE Sesion_Clase SET estado_sesion = 'Cerrada'::estado_sesion_enum WHERE id_sesion = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idSesion);
            ps.executeUpdate();
            logger.info("Sesión de clase ID: {} ha sido CERRADA de forma definitiva.", idSesion);
            
        } catch (SQLException e) {
            logger.error("Error al cerrar la sesión de clase ID: " + idSesion, e);
        }
    }
}