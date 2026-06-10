package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmTomaAsistencia extends JDialog {

    
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

        
        JPanel panelPrincipal = new JPanel(new BorderLayout(12, 12));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(244, 246, 249));

        
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

        
        String[] columnas = {"ID Alumno", "Código U", "Apellidos y Nombres", "Estado de Asistencia"};
        
        
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; 
            }
        };

        tblAlumnosAsistencia = new JTable(modeloTabla);
        tblAlumnosAsistencia.setRowHeight(30); 
        tblAlumnosAsistencia.getTableHeader().setReorderingAllowed(false);

        
        tblAlumnosAsistencia.getColumnModel().getColumn(0).setMinWidth(0);
        tblAlumnosAsistencia.getColumnModel().getColumn(0).setMaxWidth(0);
        tblAlumnosAsistencia.getColumnModel().getColumn(0).setPreferredWidth(0);

        
        JComboBox<String> cboEstadosAsistencia = new JComboBox<>(new String[]{"Asistió", "Tardanza", "Faltó"});
        tblAlumnosAsistencia.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(cboEstadosAsistencia));

        JScrollPane scroll = new JScrollPane(tblAlumnosAsistencia);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        
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