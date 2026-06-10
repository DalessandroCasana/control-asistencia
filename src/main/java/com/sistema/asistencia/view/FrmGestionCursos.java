package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class FrmGestionCursos extends JPanel {

    public JTextField txtIdCurso;
    public JTextField txtCodigoCurso;
    public JTextField txtNombreCurso;
    public JSpinner spnCreditos;
    public JSpinner spnHoras;
    public JButton btnRegistrar;
    public JButton btnModificar;
    public JButton btnEliminar;
    public JButton btnLimpiar;
    public JTable tblCursos;
    public DefaultTableModel modeloTabla;

    public FrmGestionCursos() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        
        JPanel panelFormulario = new JPanel(null);
        panelFormulario.setPreferredSize(new Dimension(280, 0));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(" Propiedades del Curso "));
        panelFormulario.setOpaque(false);

        txtIdCurso = new JTextField();
        txtIdCurso.setVisible(false);
        txtIdCurso.setBounds(0, 0, 0, 0);

        JLabel lblCodigo = new JLabel("Código de Curso:");
        lblCodigo.setBounds(15, 30, 250, 20);
        txtCodigoCurso = new JTextField();
        txtCodigoCurso.setBounds(15, 55, 240, 32);
        txtCodigoCurso.putClientProperty("JComponent.roundRect", true);
        txtCodigoCurso.putClientProperty("JTextField.placeholderText", "Ej: INF-401");

        JLabel lblNombre = new JLabel("Nombre de la Asignatura:");
        lblNombre.setBounds(15, 95, 250, 20);
        txtNombreCurso = new JTextField();
        txtNombreCurso.setBounds(15, 120, 240, 32);
        txtNombreCurso.putClientProperty("JComponent.roundRect", true);

        JLabel lblCreditos = new JLabel("Créditos:");
        lblCreditos.setBounds(15, 160, 250, 20);
        spnCreditos = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
        spnCreditos.setBounds(15, 185, 240, 32);
        spnCreditos.putClientProperty("JComponent.roundRect", true);

        JLabel lblHoras = new JLabel("Horas Totales:");
        lblHoras.setBounds(15, 225, 250, 20);
        spnHoras = new JSpinner(new SpinnerNumberModel(64, 1, 200, 1));
        spnHoras.setBounds(15, 250, 240, 32);
        spnHoras.putClientProperty("JComponent.roundRect", true);

        btnRegistrar = new JButton("Guardar Curso");
        btnRegistrar.setBackground(new Color(25, 135, 84));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRegistrar.setBounds(15, 300, 240, 35);
        btnRegistrar.putClientProperty("JButton.buttonType", "roundRect");

        btnModificar = new JButton("Modificar Curso");
        btnModificar.setBackground(new Color(255, 193, 7));
        btnModificar.setForeground(Color.BLACK);
        btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnModificar.setBounds(15, 345, 240, 35);
        btnModificar.putClientProperty("JButton.buttonType", "roundRect");

        btnEliminar = new JButton("Eliminar Curso");
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEliminar.setBounds(15, 390, 240, 35);
        btnEliminar.putClientProperty("JButton.buttonType", "roundRect");

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(15, 435, 240, 30);
        btnLimpiar.putClientProperty("JButton.buttonType", "roundRect");

        panelFormulario.add(txtIdCurso);
        panelFormulario.add(lblCodigo); panelFormulario.add(txtCodigoCurso);
        panelFormulario.add(lblNombre); panelFormulario.add(txtNombreCurso);
        panelFormulario.add(lblCreditos); panelFormulario.add(spnCreditos);
        panelFormulario.add(lblHoras); panelFormulario.add(spnHoras);
        panelFormulario.add(btnRegistrar);
        panelFormulario.add(btnModificar);
        panelFormulario.add(btnEliminar);
        panelFormulario.add(btnLimpiar);
        add(panelFormulario, BorderLayout.WEST);

        
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Cursos Activos en Base de Datos "));
        panelTabla.setOpaque(false);

        String[] columnas = {"ID", "Código", "Nombre del Curso", "Créditos", "Horas Totales"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblCursos = new JTable(modeloTabla);
        tblCursos.setRowHeight(26);
        tblCursos.putClientProperty("JTable.showHorizontalLines", true);
        tblCursos.putClientProperty("JTable.showVerticalLines", false);
        tblCursos.putClientProperty("JTable.intercellSpacing", new Dimension(0, 0));

        JScrollPane scroll = new JScrollPane(tblCursos);
        panelTabla.add(scroll, BorderLayout.CENTER);
        add(panelTabla, BorderLayout.CENTER);
    }
}