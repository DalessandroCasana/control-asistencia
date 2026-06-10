package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmDashboardDocente extends JFrame {

    // Componentes que el Controlador modificará o escuchará (Deben ser públicos)
    public JLabel lblBienvenida;
    public JLabel lblFechaActual;
    public JButton btnIniciarAsistencia;
    public JButton btnCerrarSesion;
    public JTable tblClasesHoy;
    public DefaultTableModel modeloTabla;

    public FrmDashboardDocente() {
        // Configuración de la ventana principal del profesor
        setTitle("Dashboard de Control Docente - Sistema de Asistencia");
        setSize(850, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Contenedor base con diseño limpio y moderno
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(240, 244, 248));

        // ==========================================
        // 1. PANEL SUPERIOR: Barra de Identificación
        // ==========================================
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        lblBienvenida = new JLabel("Bienvenido(a): Cargando Docente...");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBienvenida.setForeground(new Color(44, 62, 80));

        lblFechaActual = new JLabel("Agenda de Hoy: Calculando fecha...");
        lblFechaActual.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFechaActual.setForeground(new Color(127, 140, 141));

        JPanel panelTextosTop = new JPanel(new GridLayout(2, 1, 4, 4));
        panelTextosTop.setOpaque(false);
        panelTextosTop.add(lblBienvenida);
        panelTextosTop.add(lblFechaActual);

        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(231, 76, 60));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);

        panelSuperior.add(panelTextosTop, BorderLayout.WEST);
        panelSuperior.add(btnCerrarSesion, BorderLayout.EAST);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // ==========================================
        // 2. PANEL CENTRAL: Agenda del día (JTable)
        // ==========================================
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Clases Programadas para su Dictado Hoy "));
        panelTabla.setBackground(Color.WHITE);

        String[] columnas = {"ID Sección", "Curso / Asignatura", "Bloque de Horario", "Aula Asignada"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquea la edición manual de celdas en la cuadrícula
            }
        };

        tblClasesHoy = new JTable(modeloTabla);
        tblClasesHoy.setRowHeight(28);
        tblClasesHoy.getTableHeader().setReorderingAllowed(false);
        tblClasesHoy.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Ocultar la columna ID Sección para mantener estética limpia
        tblClasesHoy.getColumnModel().getColumn(0).setMinWidth(0);
        tblClasesHoy.getColumnModel().getColumn(0).setMaxWidth(0);
        tblClasesHoy.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scroll = new JScrollPane(tblClasesHoy);
        panelTabla.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        // ==========================================
        // 3. PANEL INFERIOR: Acciones de Control
        // ==========================================
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setOpaque(false);

        btnIniciarAsistencia = new JButton(" Pasar Asistencia Imprevista ");
        btnIniciarAsistencia.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIniciarAsistencia.setBackground(new Color(39, 174, 96));
        btnIniciarAsistencia.setForeground(Color.WHITE);
        btnIniciarAsistencia.setPreferredSize(new Dimension(240, 40));
        btnIniciarAsistencia.setFocusPainted(false);
        btnIniciarAsistencia.setEnabled(false); // Se activará solo si hay clases en agenda

        panelInferior.add(btnIniciarAsistencia);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);
    }
}