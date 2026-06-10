package com.sistema.asistencia.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConexionBD {
    
    private static final Logger logger = LoggerFactory.getLogger(ConexionBD.class);
    
    private static final String URL = "jdbc:postgresql://localhost:5432/control_asistencia_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "casana1233";

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            logger.info("Intentando conectar a la base de datos: {}", URL);
            
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            
            logger.info("Conexión establecida exitosamente con PostgreSQL.");
        } catch (SQLException e) {
            logger.error("Error crítico al intentar conectar a la base de datos: ", e);
        }
        return conexion;
    }
}