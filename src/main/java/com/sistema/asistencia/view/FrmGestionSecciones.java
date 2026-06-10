package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class FrmGestionSecciones extends JPanel {

    public JComboBox<String> cboCursos;
    public JComboBox<String> cboDocentes;
    public JTextField txtCodigoSeccion;
    public JTextField txtPeriodo;
    public JButton btnGuardar;
    public JButton btnLimpiar;
    public JTable tblSecciones;
    public DefaultTableModel modeloTabla;

    public FrmGestionSecciones() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        
        JPanel panelForm = new JPanel(null);
        panelForm.setPreferredSize(new Dimension(310, 0));
        panelForm.setBorder(BorderFactory.createTitledBorder(" Nueva Sección Académica "));
        panelForm.setOpaque(false);

        JLabel lblCurso = new JLabel("Seleccione Asignatura/Curso:");
        lblCurso.setBounds(15, 25, 280, 20);
        cboCursos = new JComboBox<>();
        cboCursos.setBounds(15, 45, 275, 32);
        cboCursos.putClientProperty("JComponent.roundRect", true);

        JLabel lblDocente = new JLabel("Asignar Docente Titular:");
        lblDocente.setBounds(15, 85, 280, 20);
        cboDocentes = new JComboBox<>();
        cboDocentes.setBounds(15, 105, 275, 32);
        cboDocentes.putClientProperty("JComponent.roundRect", true);

        JLabel lblCodigo = new JLabel("Código de Sección:");
        lblCodigo.setBounds(15, 145, 280, 20);
        txtCodigoSeccion = new JTextField();
        txtCodigoSeccion.setBounds(15, 165, 275, 32);
        txtCodigoSeccion.putClientProperty("JComponent.roundRect", true);
        txtCodigoSeccion.putClientProperty("JTextField.placeholderText", "Ej: 44310");

        JLabel lblPeriodo = new JLabel("Periodo Académico:");
        lblPeriodo.setBounds(15, 205, 280, 20);
        txtPeriodo = new JTextField();
        txtPeriodo.setBounds(15, 225, 275, 32);
        txtPeriodo.putClientProperty("JComponent.roundRect", true);
        txtPeriodo.putClientProperty("JTextField.placeholderText", "Ej: 2026-1");

        btnGuardar = new JButton("Registrar Sección");
        btnGuardar.setBackground(new Color(25, 135, 84));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setBounds(15, 280, 275, 35);
        btnGuardar.putClientProperty("JButton.buttonType", "roundRect");

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(15, 325, 275, 30);
        btnLimpiar.putClientProperty("JButton.buttonType", "roundRect");

        panelForm.add(lblCurso); panelForm.add(cboCursos);
        panelForm.add(lblDocente); panelForm.add(cboDocentes);
        panelForm.add(lblCodigo); panelForm.add(txtCodigoSeccion);
        panelForm.add(lblPeriodo); panelForm.add(txtPeriodo);
        panelForm.add(btnGuardar); panelForm.add(btnLimpiar);
        add(panelForm, BorderLayout.WEST);

        
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Registro de Secciones Aperturadas "));
        panelTabla.setOpaque(false);

        String[] columnas = {"ID", "Código", "Curso / Asignatura", "Docente Asignado", "Periodo"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblSecciones = new JTable(modeloTabla);
        tblSecciones.setRowHeight(26);
        tblSecciones.putClientProperty("JTable.showHorizontalLines", true);
        tblSecciones.putClientProperty("JTable.showVerticalLines", false);
        tblSecciones.putClientProperty("JTable.intercellSpacing", new Dimension(0, 0));

        JScrollPane scroll = new JScrollPane(tblSecciones);
        panelTabla.add(scroll, BorderLayout.CENTER);
        add(panelTabla, BorderLayout.CENTER);
    }
}