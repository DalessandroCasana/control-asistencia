package com.sistema.asistencia.controller;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.view.FrmLogin;
import com.sistema.asistencia.view.FrmDashboardAdmin;
import com.sistema.asistencia.view.FrmDashboardDocente;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController implements ActionListener {

    private final FrmLogin vista;

    public LoginController(FrmLogin vista) {
        this.vista = vista;
        this.vista.btnIngresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnIngresar) {
            ejecutarAutenticacion();
        }
    }

    private void ejecutarAutenticacion() {
        String correoInput = vista.txtUsuario.getText().trim();
        String passwordInput = new String(vista.txtPassword.getPassword()).trim();

        if (StringUtils.isBlank(correoInput) || StringUtils.isBlank(passwordInput)) {
            JOptionPane.showMessageDialog(vista, "Por favor, introduzca su correo y contraseña institucional.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // CONSULTA ADAPTADA A TU SCRIPT REAL DE POSTGRESQL
        // Se extrae id_usuario, nombres, apellidos y el rol (que es un ENUM) convertido a TEXT.
        String sql = "SELECT id_usuario, nombres, apellidos, rol::TEXT AS rol_texto " +
                     "FROM Usuario WHERE correo = ? AND contrasena = ? AND estado = TRUE";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, correoInput);
            ps.setString(2, passwordInput);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idUsuario = rs.getInt("id_usuario");
                    String nombreCompleto = rs.getString("nombres") + " " + rs.getString("apellidos");
                    String rolObtenido = rs.getString("rol_texto"); // Captura 'Administrador' o 'Docente'

                    vista.dispose(); // Cerrar ventana de login

                    // Filtro inteligente basado en tus ENUMS reales de base de datos
                    if ("Administrador".equalsIgnoreCase(rolObtenido)) {
                        FrmDashboardAdmin adminVista = new FrmDashboardAdmin();
                        new DashboardAdminController(adminVista);
                        adminVista.setVisible(true);
                    } 
                    else if ("Docente".equalsIgnoreCase(rolObtenido)) {
                        FrmDashboardDocente docenteVista = new FrmDashboardDocente();
                        new DashboardDocenteController(docenteVista, idUsuario, nombreCompleto);
                        docenteVista.setVisible(true);
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(vista, "Las credenciales no coinciden o el usuario está inactivo.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al conectar con PostgreSQL:\n" + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}