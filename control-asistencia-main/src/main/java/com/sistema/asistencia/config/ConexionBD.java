package com.sistema.asistencia.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConexionBD {
    
    // Inicializamos el Logger de Logback para esta clase
    private static final Logger logger = LoggerFactory.getLogger(ConexionBD.class);
    
    // Configuración de los parámetros de acceso a PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/control_asistencia_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "casana1233"; // ⚠️ REEMPLAZA CON TU CONTRASEÑA REAL

    /**
     * Método estático encargado de abrir y retornar la conexión física con la base de datos.
     * Usa Logback para dejar registro del éxito o fracaso en la consola del sistema.
     */
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // Registramos en el log el intento de conexión
            logger.info("Intentando conectar a la base de datos: {}", URL);
            
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            
            logger.info("Conexión establecida exitosamente con PostgreSQL.");
        } catch (SQLException e) {
            // Logback captura el error crítico con todo el rastreo (Stacktrace) sin romper el flujo
            logger.error("Error crítico al intentar conectar a la base de datos: ", e);
        }
        return conexion;
    }
}