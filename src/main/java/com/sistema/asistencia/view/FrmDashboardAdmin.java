package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmDashboardAdmin extends JFrame {

    public JButton btnCursos;
    public JButton btnSecciones;
    public JButton btnHorarios;
    public JButton btnReportes;
    public JButton btnCerrarSesion;
    public JLabel lblBienvenida;
    
    
    public JPanel areaContenido; 

    public FrmDashboardAdmin() {
        setTitle("Panel de Control General - Sistema de Asistencia");
        setSize(1100, 650); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 246, 248));

        
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setBackground(new Color(33, 37, 41));
        sidebar.setBorder(new EmptyBorder(25, 15, 25, 15));

        
        JLabel lblLogo = new JLabel("CONTROL ASISTENCIA");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblRol = new JLabel("Módulo Administrador");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRol.setForeground(new Color(173, 181, 189));
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(lblLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(lblRol);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        btnCursos = crearBotónSidebar("  Gestionar Cursos");
        btnSecciones = crearBotónSidebar("  Planificar Secciones");
        btnHorarios = crearBotónSidebar("  Asignar Horarios");
        btnReportes = crearBotónSidebar("  Reportes");
        
        btnCerrarSesion = new JButton("  Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCerrarSesion.setForeground(new Color(220, 53, 69));
        btnCerrarSesion.setBackground(new Color(33, 37, 41));
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setMaximumSize(new Dimension(230, 40));

        sidebar.add(btnCursos); sidebar.add(Box.createRigidArea(new Dimension(0, 12)));
        sidebar.add(btnSecciones); sidebar.add(Box.createRigidArea(new Dimension(0, 12)));
        sidebar.add(btnHorarios); sidebar.add(Box.createRigidArea(new Dimension(0, 12)));
        sidebar.add(btnReportes);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnCerrarSesion);

        panelPrincipal.add(sidebar, BorderLayout.WEST);

        
        areaContenido = new JPanel(new BorderLayout());
        areaContenido.setBackground(new Color(248, 249, 250));
        areaContenido.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(222, 226, 230)));

        
        mostrarVistaBienvenida();

        panelPrincipal.add(areaContenido, BorderLayout.CENTER);
        add(panelPrincipal);
    }

    
    public void mostrarVistaBienvenida() {
        areaContenido.removeAll();
        
        JPanel panelInicio = new JPanel(null);
        panelInicio.setBackground(new Color(248, 249, 250));

        JPanel tarjetaBienvenida = new JPanel(null);
        tarjetaBienvenida.setBounds(40, 40, 720, 100);
        tarjetaBienvenida.setBackground(Color.WHITE);
        tarjetaBienvenida.setBorder(BorderFactory.createLineBorder(new Color(233, 236, 239), 1, true));
        tarjetaBienvenida.putClientProperty("JComponent.roundRect", true);

        lblBienvenida = new JLabel("¡Bienvenido al Panel de Control Administrativo!");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBienvenida.setForeground(new Color(33, 37, 41));
        lblBienvenida.setBounds(20, 25, 600, 25);

        JLabel lblSubtexto = new JLabel("Seleccione una opción del menú lateral izquierdo para gestionar la planificación académica.");
        lblSubtexto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtexto.setForeground(new Color(108, 117, 125));
        lblSubtexto.setBounds(20, 50, 600, 20);

        tarjetaBienvenida.add(lblBienvenida);
        tarjetaBienvenida.add(lblSubtexto);
        panelInicio.add(tarjetaBienvenida);

        JPanel tarjetaInfo = new JPanel(null);
        tarjetaInfo.setBounds(40, 170, 720, 380);
        tarjetaInfo.setBackground(Color.WHITE);
        tarjetaInfo.setBorder(BorderFactory.createLineBorder(new Color(233, 236, 239), 1, true));
        
        JLabel lblStatus = new JLabel("Estado del Sistema: Operacional y Conectado a PostgreSQL");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStatus.setForeground(new Color(40, 167, 69));
        lblStatus.setBounds(25, 25, 500, 20);
        tarjetaInfo.add(lblStatus);
        
        panelInicio.add(tarjetaInfo);

        areaContenido.add(panelInicio, BorderLayout.CENTER);
        areaContenido.revalidate();
        areaContenido.repaint();
    }

    private JButton crearBotónSidebar(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setForeground(new Color(248, 249, 250));
        boton.setBackground(new Color(43, 48, 53));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setMaximumSize(new Dimension(230, 42));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.putClientProperty("JButton.buttonType", "roundRect");
        return boton;
    }
}