package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmGestionSecciones extends JDialog {

    // Componentes del Formulario de Entrada
    public JTextField txtIdSeccion; // Oculto para identificar la llave primaria en updates
    public JTextField txtCodigoSeccion; // Ej: 44310
    public JComboBox<String> cboCursos; // Almacenará los cursos registrados
    public JComboBox<String> cboDocentes; // Almacenará los docentes disponibles
    public JTextField txtAula; // Ej: Aula 402-A

    // Botones CRUD
    public JButton btnRegistrar;
    public JButton btnModificar;
    public JButton btnEliminar;
    public JButton btnLimpiar;

    // Tabla de Visualización
    public JTable tblSecciones;
    public DefaultTableModel modeloTabla;

    public FrmGestionSecciones(Frame padre) {
        super(padre, "Mantenimiento - Gestión de Secciones Académicas", true);
        setSize(900, 500);
        setLocationRelativeTo(padre);
        setResizable(false);

        // Contenedor principal con márgenes limpios
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(245, 247, 250));

        // ==========================================
        // 1. PANEL IZQUIERDO: Formulario con GridBagLayout
        // ==========================================
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder(" Planificación de Sección "));
        panelFormulario.setPreferredSize(new Dimension(350, 400));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 6, 8, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID Oculto
        txtIdSeccion = new JTextField();
        txtIdSeccion.setVisible(false);

        // Código de la Sección
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Cód. Sección:"), gbc);
        txtCodigoSeccion = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        panelFormulario.add(txtCodigoSeccion, gbc);

        // ComboBox: Curso Vinculado
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Curso:"), gbc);
        cboCursos = new JComboBox<>(new String[]{"-- Seleccione Curso --", "INF-401 - Integrador I", "INF-402 - Arquitectura de Software"});
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        panelFormulario.add(cboCursos, gbc);

        // ComboBox: Docente Asignado
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Docente:"), gbc);
        cboDocentes = new JComboBox<>(new String[]{"-- Seleccione Docente --", "Ing. Carlos Torres", "Dra. Ana Martínez"});
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        panelFormulario.add(cboDocentes, gbc);

        // Campo Aula
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Aula / Lab:"), gbc);
        txtAula = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        panelFormulario.add(txtAula, gbc);

        // Panel de Botones
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

        // Insertar bloque de botones en la parte inferior del formulario
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 6, 5, 6);
        panelFormulario.add(panelBotonesForm, gbc);

        panelPrincipal.add(panelFormulario, BorderLayout.WEST);

        // ==========================================
        // 2. PANEL CENTRAL/DERECHO: Grid de Visualización
        // ==========================================
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Secciones Academicas Aperturadas "));

        String[] columnas = {"ID", "Cód. Sección", "Curso Asignado", "Docente", "Aula / Ubicación"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desactiva edición en celda directa
            }
        };

        tblSecciones = new JTable(modeloTabla);
        tblSecciones.setRowHeight(24);
        tblSecciones.getTableHeader().setReorderingAllowed(false);
        
        // Ocultar ID físicamente
        tblSecciones.getColumnModel().getColumn(0).setMinWidth(0);
        tblSecciones.getColumnModel().getColumn(0).setMaxWidth(0);
        tblSecciones.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scroll = new JScrollPane(tblSecciones);
        panelTabla.add(scroll, BorderLayout.CENTER);
        
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        add(panelPrincipal);
    }
}