package com.sistema.asistencia.view;

import javax.swing.*;
import java.awt.*;

public class FrmReportes extends JDialog {

    // Componentes públicos para que el controlador los pueda escuchar y leer
    public JComboBox<String> cboSeccionesReporte;
    public JButton btnExportarExcel;
    public JButton btnCerrar;

    public FrmReportes(Frame padre) {
        super(padre, "Módulo de Inteligencia de Datos - Reportes", true);
        setSize(480, 220);
        setLocationRelativeTo(padre);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 247, 250));

        JLabel lblTitulo = new JLabel("Exportación de Reportes Consolidados", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(new Color(44, 62, 80));

        cboSeccionesReporte = new JComboBox<>();
        cboSeccionesReporte.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        btnExportarExcel = new JButton(" Generar Reporte Excel (.xlsx) ");
        btnExportarExcel.setBackground(new Color(41, 128, 185));
        btnExportarExcel.setForeground(Color.WHITE);
        btnExportarExcel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportarExcel.setFocusPainted(false);

        btnCerrar = new JButton("Cerrar Ventana");

        panel.add(lblTitulo);
        panel.add(cboSeccionesReporte);
        panel.add(btnExportarExcel);
        panel.add(btnCerrar);

        add(panel);
    }
}