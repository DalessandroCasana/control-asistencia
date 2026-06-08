package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmGestionSecciones extends JDialog {

    // Componentes del Formulario (Públicos para el Controlador)
    public JComboBox<String> cboCursos;
    public JComboBox<String> cboDocentes;
    public JTextField txtCodigoSeccion;
    public JTextField txtPeriodo;

    // Botones de Acción
    public JButton btnGuardar;
    public JButton btnLimpiar;
    public JButton btnCerrar;

    // Tabla de Visualización
    public JTable tblSecciones;
    public DefaultTableModel modeloTabla;

    public FrmGestionSecciones(Window padre) {
        super(padre, "Módulo de Planificación - Gestión de Secciones", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(800, 450);
        setLocationRelativeTo(padre);
        setResizable(false);

        // Panel Principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(240, 244, 248));

        // ========================================================
        // 1. FORMULARIO DE REGISTRO (IZQUIERDA)
        // ========================================================
        JPanel panelForm = new JPanel(null);
        panelForm.setPreferredSize(new Dimension(300, 0));
        panelForm.setBorder(BorderFactory.createTitledBorder(" Nueva Sección Académica "));
        panelForm.setOpaque(false);

        JLabel lblCurso = new JLabel("Seleccione Asignatura/Curso:");
        lblCurso.setBounds(15, 25, 270, 20);
        cboCursos = new JComboBox<>();
        cboCursos.setBounds(15, 45, 260, 30);

        JLabel lblDocente = new JLabel("Asignar Docente Titular:");
        lblDocente.setBounds(15, 85, 270, 20);
        cboDocentes = new JComboBox<>();
        cboDocentes.setBounds(15, 105, 260, 30);

        JLabel lblCodigo = new JLabel("Código de Sección (Ej: 44310):");
        lblCodigo.setBounds(15, 145, 270, 20);
        txtCodigoSeccion = new JTextField();
        txtCodigoSeccion.setBounds(15, 165, 260, 30);

        JLabel lblPeriodo = new JLabel("Periodo Académico (Ej: 2026-1):");
        lblPeriodo.setBounds(15, 205, 270, 20);
        txtPeriodo = new JTextField();
        txtPeriodo.setBounds(15, 225, 260, 30);

        btnGuardar = new JButton("Registrar Sección");
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setBounds(15, 280, 260, 35);
        btnGuardar.setFocusPainted(false);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(15, 325, 260, 28);

        panelForm.add(lblCurso);
        panelForm.add(cboCursos);
        panelForm.add(lblDocente);
        panelForm.add(cboDocentes);
        panelForm.add(lblCodigo);
        panelForm.add(txtCodigoSeccion);
        panelForm.add(lblPeriodo);
        panelForm.add(txtPeriodo);
        panelForm.add(btnGuardar);
        panelForm.add(btnLimpiar);

        panelPrincipal.add(panelForm, BorderLayout.WEST);

        // ========================================================
        // 2. TABLA DE REGISTROS (DERECHA)
        // ========================================================
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Registro de Secciones Aperturadas "));
        panelTabla.setOpaque(false);

        String[] columnas = {"ID", "Código", "Curso / Asignatura", "Docente Asignado", "Periodo"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblSecciones = new JTable(modeloTabla);
        tblSecciones.setRowHeight(24);
        tblSecciones.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblSecciones.getColumnModel().getColumn(1).setPreferredWidth(70);
        tblSecciones.getColumnModel().getColumn(2).setPreferredWidth(180);
        tblSecciones.getColumnModel().getColumn(3).setPreferredWidth(180);

        JScrollPane scroll = new JScrollPane(tblSecciones);
        panelTabla.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        // Botón inferior
        btnCerrar = new JButton("Cerrar Ventana");
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setOpaque(false);
        panelSur.add(btnCerrar);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }
}