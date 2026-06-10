package com.sistema.asistencia.view;

import javax.swing.*;
import java.awt.*;

public class FrmReportes extends JPanel {

    public JComboBox<String> cboSeccionesReporte;
    public JButton btnExportarExcel;
    public JButton btnCerrar;

    public FrmReportes() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        
        JLabel lblTitulo = new JLabel("Centro de Reportes y Estadísticas Consolidadas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        
        JPanel panelContenido = new JPanel(null);
        panelContenido.setOpaque(false);

        JPanel tarjetaReporte = new JPanel(null);
        tarjetaReporte.setBounds(20, 20, 500, 150);
        tarjetaReporte.setBackground(new Color(248, 249, 250));
        tarjetaReporte.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true));
        tarjetaReporte.putClientProperty("JComponent.roundRect", true);

        JLabel lblDesc = new JLabel("Reporte General de Asistencia por Cursos Aperturados");
        lblDesc.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDesc.setBounds(20, 20, 450, 20);

        JLabel lblSub = new JLabel("Genera un consolidado de porcentajes de faltas y asistencias.");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        lblSub.setBounds(20, 45, 450, 20);

        JLabel lblSeleccion = new JLabel("Seleccione la sección para exportar la asistencia:");
        lblSeleccion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSeleccion.setBounds(20, 80, 450, 20);

        cboSeccionesReporte = new JComboBox<>();
        cboSeccionesReporte.setBounds(20, 105, 450, 32);
        cboSeccionesReporte.putClientProperty("JComponent.roundRect", true);

        btnExportarExcel = new JButton("Exportar Reporte a Excel");
        btnExportarExcel.setBackground(new Color(25, 135, 84));
        btnExportarExcel.setForeground(Color.WHITE);
        btnExportarExcel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportarExcel.setBounds(20, 150, 250, 35);
        btnExportarExcel.putClientProperty("JButton.buttonType", "roundRect");

        btnCerrar = new JButton("Cerrar Reporte");
        btnCerrar.setBackground(new Color(108, 117, 125));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setBounds(290, 150, 180, 35);
        btnCerrar.putClientProperty("JButton.buttonType", "roundRect");

        tarjetaReporte.add(lblDesc);
        tarjetaReporte.add(lblSub);
        tarjetaReporte.add(lblSeleccion);
        tarjetaReporte.add(cboSeccionesReporte);
        tarjetaReporte.add(btnExportarExcel);
        tarjetaReporte.add(btnCerrar);
        panelContenido.add(tarjetaReporte);

        add(panelContenido, BorderLayout.CENTER);
    }
}