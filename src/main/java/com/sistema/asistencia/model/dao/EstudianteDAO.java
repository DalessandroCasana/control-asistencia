package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.Estudiante;
import com.google.common.base.Preconditions; // Uso de Google Guava
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    private static final Logger logger = LoggerFactory.getLogger(EstudianteDAO.class);

    /**
     * Obtiene la lista de estudiantes matriculados en una sección específica para cargar la JTable del docente.
     * @param idSeccion ID de la sección actual.
     * @return Lista de objetos Estudiante vinculados.
     */
    public List<Estudiante> listarPorSeccion(int idSeccion) {
        // Validación con Guava: El ID de la sección debe ser un entero positivo válido
        Preconditions.checkArgument(idSeccion > 0, "El ID de la sección debe ser mayor a cero.");

        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT e.id_estudiante, e.codigo_estudiante, e.nombres, e.apellidos, e.correo, e.estado_academico " +
                     "FROM Estudiante e " +
                     "INNER JOIN Matricula m ON e.id_estudiante = m.id_estudiante " +
                     "WHERE m.id_seccion = ? AND m.estado_matricula = 'Activo' " +
                     "ORDER BY e.apellidos, e.nombres";

        logger.info("Consultando estudiantes matriculados en la sección ID: {}", idSeccion);

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idSeccion);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Estudiante est = new Estudiante(
                        rs.getInt("id_estudiante"),
                        rs.getString("codigo_estudiante"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("correo"),
                        rs.getString("estado_academico")
                    );
                    lista.add(est);
                }
            }
            logger.info("Se cargaron exitosamente {} estudiantes para la sección ID: {}", lista.size(), idSeccion);

        } catch (SQLException e) {
            logger.error("Error crítico al listar estudiantes de la sección ID: " + idSeccion, e);
        }

        return lista;
    }

    /**
     * REGLA DE NEGOCIO CRÍTICA (Fase 4): Verifica el porcentaje de faltas de un alumno 
     * en una sección y lo inhabilita automáticamente si supera el 30%.
     * @param idEstudiante ID del alumno evaluado.
     * @param idSeccion ID de la sección evaluada.
     */
    public void verificarYActualizarInhabilitacion(int idEstudiante, int idSeccion) {
        // Validaciones previas con Guava
        Preconditions.checkArgument(idEstudiante > 0, "ID de estudiante inválido.");
        Preconditions.checkArgument(idSeccion > 0, "ID de sección inválido.");

        // SQL 1: Cuenta el total de sesiones dictadas a la fecha para esa sección
        String sqlTotalSesiones = "SELECT COUNT(*) FROM Sesion_Clase WHERE id_seccion = ? AND estado_sesion = 'Cerrada'";
        
        // SQL 2: Suma el peso de las ausencias del alumno (Donde Ausente vale 0.00 en la BD, sumamos los registros con id_estado = 2)
        String sqlTotalAusencias = "SELECT COUNT(*) FROM Detalle_Asistencia da " +
                                   "INNER JOIN Sesion_Clase sc ON da.id_sesion = sc.id_sesion " +
                                   "WHERE sc.id_seccion = ? AND da.id_estudiante = ? AND da.id_estado = 2";

        // SQL 3: Actualiza el estado en la tabla intermedia Matricula si excede el límite
        String sqlInhabilitar = "UPDATE Matricula SET estado_matricula = 'Inhabilitado' WHERE id_seccion = ? AND id_estudiante = ?";

        try (Connection conn = ConexionBD.obtenerConexion()) {
            int totalSesiones = 0;
            int totalAusencias = 0;

            // 1. Obtener total sesiones
            try (PreparedStatement ps1 = conn.prepareStatement(sqlTotalSesiones)) {
                ps1.setInt(1, idSeccion);
                try (ResultSet rs1 = ps1.executeQuery()) {
                    if (rs1.next()) totalSesiones = rs1.getInt(1);
                }
            }

            // Si no hay sesiones cerradas aún, no se puede calcular porcentajes
            if (totalSesiones == 0) return;

            // 2. Obtener total ausencias
            try (PreparedStatement ps2 = conn.prepareStatement(sqlTotalAusencias)) {
                ps2.setInt(1, idSeccion);
                ps2.setInt(2, idEstudiante);
                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) totalAusencias = rs2.getInt(1);
                }
            }

            // 3. Aplicar fórmula matemática exacta del requerimiento
            double porcentajeFaltas = ((double) totalAusencias / totalSesiones) * 100;
            logger.info("Evaluación de riesgo - Alumno ID {}: {} ausencias de {} clases ({}%)", 
                        idEstudiante, totalAusencias, totalSesiones, String.format("%.2f", porcentajeFaltas));

            // REGLA INSTITUCIONAL: Límite del 30% de inasistencias
            if (porcentajeFaltas > 30.0) {
                try (PreparedStatement ps3 = conn.prepareStatement(sqlInhabilitar)) {
                    ps3.setInt(1, idSeccion);
                    ps3.setInt(2, idEstudiante);
                    ps3.executeUpdate();
                }
                logger.warn("¡ALERTA CRÍTICA AUTOMÁTICA! El estudiante ID: {} ha sido INHABILITADO en la sección ID: {} por superar el 30% de faltas.", 
                            idEstudiante, idSeccion);
            }

        } catch (SQLException e) {
            logger.error("Error al procesar la regla de inhabilitación para el estudiante: " + idEstudiante, e);
        }
    }
}