package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.DetalleAsistencia;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class DetalleAsistenciaDAO {

    private static final Logger logger = LoggerFactory.getLogger(DetalleAsistenciaDAO.class);
    private final EstudianteDAO estudianteDAO = new EstudianteDAO();

    /**
     * Guarda la asistencia de todos los alumnos mediante un proceso transaccional por lotes (Batch).
     * @param detalles Lista con los estados de asistencia de cada alumno.
     * @return true si se guardaron todos correctamente, false si hubo un fallo y se aplicó Rollback.
     */
    public boolean guardarAsistenciaMasiva(List<DetalleAsistencia> detalles) {
        Preconditions.checkNotNull(detalles, "La lista de asistencia no puede ser nula.");
        Preconditions.checkArgument(!detalles.isEmpty(), "La lista de asistencia no puede estar vacía.");

        String sql = "INSERT INTO Detalle_Asistencia (id_sesion, id_estudiante, id_estado, hora_marcado, observacion) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionBD.obtenerConexion();
            // Desactivamos el auto-commit para iniciar manualmente una Transacción Segura
            conn.setAutoCommit(false);
            
            ps = conn.prepareStatement(sql);
            
            int idSesionActual = detalles.get(0).getSesion().getIdSesion();
            int idSeccionActual = detalles.get(0).getSesion().getSeccion().getIdSeccion();

            logger.info("Iniciando inserción transaccional de asistencia para la sesión ID: {}", idSesionActual);

            for (DetalleAsistencia detalle : detalles) {
                ps.setInt(1, detalle.getSesion().getIdSesion());
                ps.setInt(2, detalle.getEstudiante().getIdEstudiante());
                ps.setInt(3, detalle.getEstado().getIdEstado());
                ps.setTime(4, Time.valueOf(detalle.getHoraMarcado()));
                ps.setString(5, detalle.getObservacion());
                
                ps.addBatch(); // Agrega la fila al lote en memoria sin enviarla aún a la BD
            }

            // Ejecutamos todo el lote de un solo golpe
            ps.executeBatch();
            
            // Confirmamos y consolidamos la transacción en el disco duro de PostgreSQL
            conn.commit();
            logger.info("Transacción exitosa. Se registraron {} filas de asistencia.", detalles.size());

            // AUTOMATIZACIÓN (Fase 4): Tras guardar la asistencia, el sistema evalúa en segundo plano 
            // a cada alumno de la lista para calcular inhabilitaciones de forma inmediata.
           // AUTOMATIZACIÓN (Fase 4): Tras guardar la asistencia, el sistema evalúa en segundo plano 
// a cada alumno de la lista para calcular inhabilitaciones de forma inmediata.
            for (DetalleAsistencia detalle : detalles) {
                estudianteDAO.verificarYActualizarInhabilitacion(
                    detalle.getEstudiante().getIdEstudiante(), 
                    idSeccionActual
                );
            }
            return true;

        } catch (SQLException e) {
            logger.error("Error crítico en lote de asistencia. Iniciando Rollback para proteger la consistencia de datos.", e);
            if (conn != null) {
                try {
                    conn.rollback(); // Deshace absolutamente todo si un solo alumno falla
                    logger.info("Rollback ejecutado con éxito. Ningún dato corrupto fue almacenado.");
                } catch (SQLException ex) {
                    logger.error("Error fatal al intentar hacer el rollback", ex);
                }
            }
            return false;
        } finally {
            // Liberación estricta de recursos en el bloque finally
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                logger.error("Error al cerrar los recursos JDBC de asistencia", e);
            }
        }
    }
}