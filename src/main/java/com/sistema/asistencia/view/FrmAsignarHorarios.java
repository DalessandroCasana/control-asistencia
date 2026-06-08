package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmAsignarHorarios extends JDialog {

    // Componentes del Formulario de Entrada
    public JTextField txtIdHorario; // Oculto para identificar la llave primaria en updates
    public JComboBox<String> cboSecciones; // Almacenará las secciones configuradas
    public JComboBox<String> cboDias; // Lunes, Martes, Miércoles, etc.
    public JSpinner spnHoraInicio; // Selector de tiempo (Formato de hora)
    public JSpinner spnHoraFin;

    // Botones CRUD
    public JButton btnAsignar;
    public JButton btnModificar;
    public JButton btnEliminar;
    public JButton btnLimpiar;

    // Tabla de Visualización
    public JTable tblHorarios;
    public DefaultTableModel modeloTabla;

    public FrmAsignarHorarios(Frame padre) {
        super(padre, "Planificación - Asignación de Horarios Institucionales", true);
        setSize(900, 480);
        setLocationRelativeTo(padre);
        setResizable(false);

        // Contenedor principal con márgenes limpios
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(245, 247, 250));

        // ==========================================
        // 1. PANEL IZQUIERDO: Formulario de Tiempo
        // ==========================================
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder(" Bloque de Tiempo "));
        panelFormulario.setPreferredSize(new Dimension(350, 400));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 6, 8, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID Oculto
        txtIdHorario = new JTextField();
        txtIdHorario.setVisible(false);

        // ComboBox: Sección Vinculada
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Sección Académica:"), gbc);
        cboSecciones = new JComboBox<>(new String[]{"-- Seleccione Sección --", "Sección 44310 (Integrador I)", "Sección 44311 (Arquitectura)"});
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        panelFormulario.add(cboSecciones, gbc);

        // ComboBox: Día de la semana
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Día Semanal:"), gbc);
        cboDias = new JComboBox<>(new String[]{"-- Seleccione Día --", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"});
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        panelFormulario.add(cboDias, gbc);

        // Spinner: Hora Inicio (Modelo SpinnerDateModel para manejar tiempos limpios)
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Hora Inicio:"), gbc);
        
        spnHoraInicio = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorInicio = new JSpinner.DateEditor(spnHoraInicio, "HH:mm");
        spnHoraInicio.setEditor(editorInicio);
        spnHoraInicio.setValue(new java.util.Date()); // Hora actual por defecto
        
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        panelFormulario.add(spnHoraInicio, gbc);

        // Spinner: Hora Fin
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Hora Fin:"), gbc);
        
        spnHoraFin = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorFin = new JSpinner.DateEditor(spnHoraFin, "HH:mm");
        spnHoraFin.setEditor(editorFin);
        spnHoraFin.setValue(new java.util.Date());
        
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        panelFormulario.add(spnHoraFin, gbc);

        // Panel de Botones
        JPanel panelBotonesForm = new JPanel(new GridLayout(2, 2, 8, 8));
        panelBotonesForm.setOpaque(false);
        
        btnAsignar = new JButton("Asignar");
        btnAsignar.setBackground(new Color(28, 112, 219));
        btnAsignar.setForeground(Color.WHITE);
        
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.WHITE);
        
        btnLimpiar = new JButton("Limpiar");

        panelBotonesForm.add(btnAsignar);
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
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Horarios Programados en el Ciclo "));

        String[] columnas = {"ID", "Sección Académica", "Día", "Hora Inicio", "Hora Fin"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desactiva edición directa en la cuadrícula
            }
        };

        tblHorarios = new JTable(modeloTabla);
        tblHorarios.setRowHeight(24);
        tblHorarios.getTableHeader().setReorderingAllowed(false);
        
        // Ocultar ID físicamente
        tblHorarios.getColumnModel().getColumn(0).setMinWidth(0);
        tblHorarios.getColumnModel().getColumn(0).setMaxWidth(0);
        tblHorarios.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scroll = new JScrollPane(tblHorarios);
        panelTabla.add(scroll, BorderLayout.CENTER);
        
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        add(panelPrincipal);
    }
}