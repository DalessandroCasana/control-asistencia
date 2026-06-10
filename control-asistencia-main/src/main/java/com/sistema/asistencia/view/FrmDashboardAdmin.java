package com.sistema.asistencia.view;

import javax.swing.*;
import java.awt.*;

public class FrmDashboardAdmin extends JFrame {

    // Componentes públicos para el enlace del controlador
    public JButton btnCursos;
    public JButton btnSecciones;
    public JButton btnHorarios;
    public JButton btnReportes; // El botón del error
    public JButton btnCerrarSesion;

    public FrmDashboardAdmin() {
        setTitle("Panel de Control del Administrador - Sistema de Asistencia");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Contenedor principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(236, 240, 241));

        // Encabezado
        JLabel lblTitulo = new JLabel("MÓDULO DE GESTIÓN INSTITUCIONAL", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(44, 62, 80));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel de Menú (Cuadrícula de botones)
        JPanel panelMenu = new JPanel(new GridLayout(2, 2, 15, 15));
        panelMenu.setOpaque(false);

        btnCursos = new JButton("Gestionar Cursos");
        btnCursos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCursos.setBackground(new Color(52, 152, 219));
        btnCursos.setForeground(Color.WHITE);

        btnSecciones = new JButton("Gestionar Secciones");
        btnSecciones.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSecciones.setBackground(new Color(46, 204, 113));
        btnSecciones.setForeground(Color.WHITE);

        btnHorarios = new JButton("Asignar Horarios");
        btnHorarios.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnHorarios.setBackground(new Color(155, 89, 182));
        btnHorarios.setForeground(Color.WHITE);

        btnReportes = new JButton("Reportes y Estadísticas"); // Nombre exacto unificado
        btnReportes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReportes.setBackground(new Color(230, 126, 34));
        btnReportes.setForeground(Color.WHITE);

        panelMenu.add(btnCursos);
        panelMenu.add(btnSecciones);
        panelMenu.add(btnHorarios);
        panelMenu.add(btnReportes);

        panelPrincipal.add(panelMenu, BorderLayout.CENTER);

        // Barra inferior para cerrar sesión
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setOpaque(false);
        
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(192, 57, 43));
        btnCerrarSesion.setForeground(Color.WHITE);
        
        panelInferior.add(btnCerrarSesion);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);
    }
}