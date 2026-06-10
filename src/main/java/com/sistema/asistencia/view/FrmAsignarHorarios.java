package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class FrmAsignarHorarios extends JPanel {

    public JComboBox<String> cboSecciones;
    public JComboBox<String> cboDias;
    public JTextField txtHoraInicio;
    public JTextField txtHoraFin;
    public JTextField txtAula;
    public JButton btnGuardar;
    
    
    public JTable tblHorarios;
    public DefaultTableModel modeloTabla;

    public FrmAsignarHorarios() {
        
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE); 

        
        JPanel panelForm = new JPanel(null);
        panelForm.setPreferredSize(new Dimension(310, 0));
        panelForm.setBorder(BorderFactory.createTitledBorder(" Planificación del Horario "));
        panelForm.setOpaque(false);

        JLabel lblSeccion = new JLabel("Seleccione Sección Aperturada:");
        lblSeccion.setBounds(15, 25, 280, 20);
        cboSecciones = new JComboBox<>();
        cboSecciones.setBounds(15, 45, 275, 32);
        cboSecciones.putClientProperty("JComponent.roundRect", true);

        JLabel lblDia = new JLabel("Día de la Semana:");
        lblDia.setBounds(15, 85, 280, 20);
        cboDias = new JComboBox<>(new String[]{"-- Seleccione un Día --", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"});
        cboDias.setBounds(15, 105, 275, 32);
        cboDias.putClientProperty("JComponent.roundRect", true);

        JLabel lblInicio = new JLabel("Hora Inicio (24h):");
        lblInicio.setBounds(15, 145, 280, 20);
        txtHoraInicio = new JTextField();
        txtHoraInicio.setBounds(15, 165, 275, 32);
        txtHoraInicio.putClientProperty("JComponent.roundRect", true);
        txtHoraInicio.putClientProperty("JTextField.placeholderText", "Ej: 08:30");

        JLabel lblFin = new JLabel("Hora Fin (24h):");
        lblFin.setBounds(15, 205, 280, 20);
        txtHoraFin = new JTextField();
        txtHoraFin.setBounds(15, 225, 275, 32);
        txtHoraFin.putClientProperty("JComponent.roundRect", true);
        txtHoraFin.putClientProperty("JTextField.placeholderText", "Ej: 11:00");

        JLabel lblAula = new JLabel("Aula Asignada:");
        lblAula.setBounds(15, 265, 280, 20);
        txtAula = new JTextField();
        txtAula.setBounds(15, 285, 275, 32);
        txtAula.putClientProperty("JComponent.roundRect", true);
        txtAula.putClientProperty("JTextField.placeholderText", "Ej: B-405");

        btnGuardar = new JButton("Asignar Bloque Horario");
        btnGuardar.setBackground(new Color(111, 66, 193));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setBounds(15, 340, 275, 38);
        btnGuardar.putClientProperty("JButton.buttonType", "roundRect");

        panelForm.add(lblSeccion); panelForm.add(cboSecciones);
        panelForm.add(lblDia); panelForm.add(cboDias);
        panelForm.add(lblInicio); panelForm.add(txtHoraInicio);
        panelForm.add(lblFin); panelForm.add(txtHoraFin);
        panelForm.add(lblAula); panelForm.add(txtAula);
        panelForm.add(btnGuardar);
        add(panelForm, BorderLayout.WEST);

        
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Distribución del Tiempo Académico "));
        panelTabla.setOpaque(false);

        String[] columnas = {"ID", "Sección", "Curso / Asignatura", "Día", "Inicio", "Fin", "Aula"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblHorarios = new JTable(modeloTabla);
        tblHorarios.setRowHeight(26);
        tblHorarios.putClientProperty("JTable.showHorizontalLines", true);
        tblHorarios.putClientProperty("JTable.showVerticalLines", false);
        tblHorarios.putClientProperty("JTable.intercellSpacing", new Dimension(0, 0));

        JScrollPane scroll = new JScrollPane(tblHorarios);
        panelTabla.add(scroll, BorderLayout.CENTER);
        panelPrincipalAsignar(panelTabla);
    }

    private void panelPrincipalAsignar(JPanel panelTabla) {
        add(panelTabla, BorderLayout.CENTER);
    }
}