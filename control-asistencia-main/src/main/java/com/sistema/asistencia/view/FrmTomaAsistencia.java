package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmTomaAsistencia extends JDialog {

    // Componentes que controlará el Backend
    public JLabel lblInfoClase;
    public JTable tblAlumnosAsistencia;
    public DefaultTableModel modeloTabla;
    public JButton btnGuardarAsistencia;
    public JButton btnCancelar;

    public FrmTomaAsistencia(Frame padre, String informacionClase) {
        super(padre, "Control de Aula - Registro de Asistencia", true);
        setSize(750, 480);
        setLocationRelativeTo(padre);
        setResizable(false);

        // Panel Principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(12, 12));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(244, 246, 249));

        // Header Informativo
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);
        
        lblInfoClase = new JLabel("Clase: " + informacionClase);
        lblInfoClase.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblInfoClase.setForeground(new Color(44, 62, 80));
        
        JLabel lblIndicacion = new JLabel("Seleccione el estado de cada estudiante y guarde al finalizar.");
        lblIndicacion.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblIndicacion.setForeground(new Color(127, 140, 141));

        panelHeader.add(lblInfoClase, BorderLayout.NORTH);
        panelHeader.add(lblIndicacion, BorderLayout.SOUTH);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // Estructura de la Tabla JTable
        String[] columnas = {"ID Alumno", "Código U", "Apellidos y Nombres", "Estado de Asistencia"};
        
        // Sobrescribimos el modelo para permitir que la columna 3 (combos) sea editable
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo la columna del combo es editable por el docente
            }
        };

        tblAlumnosAsistencia = new JTable(modeloTabla);
        tblAlumnosAsistencia.setRowHeight(30); // Espacio óptimo para que quepa el ComboBox
        tblAlumnosAsistencia.getTableHeader().setReorderingAllowed(false);

        // Ocultar la columna técnica ID Alumno
        tblAlumnosAsistencia.getColumnModel().getColumn(0).setMinWidth(0);
        tblAlumnosAsistencia.getColumnModel().getColumn(0).setMaxWidth(0);
        tblAlumnosAsistencia.getColumnModel().getColumn(0).setPreferredWidth(0);

        // TRUCO DE RENDERIZADO: Incrustar un JComboBox real dentro de la celda de la tabla
        JComboBox<String> cboEstadosAsistencia = new JComboBox<>(new String[]{"Asistió", "Tardanza", "Faltó"});
        tblAlumnosAsistencia.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(cboEstadosAsistencia));

        JScrollPane scroll = new JScrollPane(tblAlumnosAsistencia);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        // Panel de Control Inferior (Botones)
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAcciones.setOpaque(false);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(110, 35));

        btnGuardarAsistencia = new JButton("Guardar Registro");
        btnGuardarAsistencia.setBackground(new Color(39, 174, 96));
        btnGuardarAsistencia.setForeground(Color.WHITE);
        btnGuardarAsistencia.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardarAsistencia.setPreferredSize(new Dimension(150, 35));
        btnGuardarAsistencia.setFocusPainted(false);

        panelAcciones.add(btnCancelar);
        panelAcciones.add(btnGuardarAsistencia);
        panelPrincipal.add(panelAcciones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }
}