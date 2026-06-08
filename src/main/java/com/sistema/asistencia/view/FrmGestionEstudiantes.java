package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmGestionEstudiantes extends JDialog {

    // Componentes del Formulario de Entrada
    public JTextField txtIdEstudiante; // Oculto para tracking de Primary Key
    public JTextField txtCodigo;       // Ej: U20261023
    public JTextField txtNombres;
    public JTextField txtApellidos;
    public JTextField txtCorreo;
    public JComboBox<String> cboEstado; // Activo / Inactivo

    // Botones CRUD
    public JButton btnRegistrar;
    public JButton btnModificar;
    public JButton btnEliminar;
    public JButton btnLimpiar;

    // Tabla de Visualización
    public JTable tblEstudiantes;
    public DefaultTableModel modeloTabla;

    public FrmGestionEstudiantes(Frame padre) {
        super(padre, "Mantenimiento - Gestión de Estudiantes Universitarios", true);
        setSize(950, 520);
        setLocationRelativeTo(padre);
        setResizable(false);

        // Contenedor principal con márgenes limpios
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(245, 247, 250));

        // ==========================================
        // 1. PANEL IZQUIERDO: Formulario de Ficha Alumno
        // ==========================================
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder(" Ficha de Matrícula del Alumno "));
        panelFormulario.setPreferredSize(new Dimension(360, 400));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 6, 8, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID Oculto
        txtIdEstudiante = new JTextField();
        txtIdEstudiante.setVisible(false);

        // Código Universitario
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Código U:"), gbc);
        txtCodigo = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        panelFormulario.add(txtCodigo, gbc);

        // Nombres
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Nombres:"), gbc);
        txtNombres = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        panelFormulario.add(txtNombres, gbc);

        // Apellidos
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Apellidos:"), gbc);
        txtApellidos = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        panelFormulario.add(txtApellidos, gbc);

        // Correo Institucional
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Email Inst:"), gbc);
        txtCorreo = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        panelFormulario.add(txtCorreo, gbc);

        // Estado del Alumno
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Estado:"), gbc);
        cboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        panelFormulario.add(cboEstado, gbc);

        // Panel de Botones CRUD
        JPanel panelBotonesForm = new JPanel(new GridLayout(2, 2, 8, 8));
        panelBotonesForm.setOpaque(false);
        
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(28, 112, 219));
        btnRegistrar.setForeground(Color.WHITE);
        
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.WHITE);
        
        btnLimpiar = new JButton("Limpiar");

        panelBotonesForm.add(btnRegistrar);
        panelBotonesForm.add(btnModificar);
        panelBotonesForm.add(btnEliminar);
        panelBotonesForm.add(btnLimpiar);

        // Insertar bloque de botones
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 6, 5, 6);
        panelFormulario.add(panelBotonesForm, gbc);

        panelPrincipal.add(panelFormulario, BorderLayout.WEST);

        // ==========================================
        // 2. PANEL DERECHO: Cuadrícula JTable
        // ==========================================
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Alumnos Matriculados en el Sistema "));

        String[] columnas = {"ID", "Código", "Nombres", "Apellidos", "Correo Electrónico", "Estado"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquear edición directa
            }
        };

        tblEstudiantes = new JTable(modeloTabla);
        tblEstudiantes.setRowHeight(24);
        tblEstudiantes.getTableHeader().setReorderingAllowed(false);
        
        // Ocultar ID físicamente
        tblEstudiantes.getColumnModel().getColumn(0).setMinWidth(0);
        tblEstudiantes.getColumnModel().getColumn(0).setMaxWidth(0);
        tblEstudiantes.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scroll = new JScrollPane(tblEstudiantes);
        panelTabla.add(scroll, BorderLayout.CENTER);
        
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        add(panelPrincipal);
    }
}