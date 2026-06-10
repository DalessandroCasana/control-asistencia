package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmGestionCursos extends JDialog {

    // Componentes del Formulario de Entrada
    public JTextField txtIdCurso; // Oculto o deshabilitado, sirve para las búsquedas y actualizaciones
    public JTextField txtCodigoCurso;
    public JTextField txtNombreCurso;
    public JSpinner spnCreditos;
    public JSpinner spnHoras;

    // Botones de Operación CRUD
    public JButton btnRegistrar;
    public JButton btnModificar;
    public JButton btnEliminar;
    public JButton btnLimpiar;

    // Tabla de Visualización
    public JTable tblCursos;
    public DefaultTableModel modeloTabla;

    public FrmGestionCursos(Frame padre) {
        super(padre, "Mantenimiento - Gestión de Cursos Globales", true);
        setSize(850, 480);
        setLocationRelativeTo(padre);
        setResizable(false);

        // CONTENEDOR PRINCIPAL: Distribución por zonas divididas
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(245, 247, 250));

        // ==========================================
        // 1. PANEL IZQUIERDO: Formulario de Registro
        // ==========================================
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder(" Datos de la Asignatura "));
        panelFormulario.setPreferredSize(new Dimension(320, 400));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 6, 8, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo ID (Invisible de resguardo técnico para el query UPDATE/DELETE)
        txtIdCurso = new JTextField();
        txtIdCurso.setVisible(false);

        // Código del Curso (Ej: INF-401)
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Código Curso:"), gbc);
        txtCodigoCurso = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        panelFormulario.add(txtCodigoCurso, gbc);

        // Nombre del Curso
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Nombre Curso:"), gbc);
        txtNombreCurso = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        panelFormulario.add(txtNombreCurso, gbc);

        // Créditos Académicos (Spinner Numérico limitado)
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Créditos:"), gbc);
        spnCreditos = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        panelFormulario.add(spnCreditos, gbc);

        // Horas Totales del Ciclo
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Horas Totales:"), gbc);
        spnHoras = new JSpinner(new SpinnerNumberModel(64, 16, 128, 2));
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        panelFormulario.add(spnHoras, gbc);

        // Bloque de Botones de Acción apilados horizontalmente en la parte inferior del formulario
        JPanel panelBotonesForm = new JPanel(new GridLayout(2, 2, 8, 8));
        panelBotonesForm.setOpaque(false);
        
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(28, 112, 219)); // Azul Institucional
        btnRegistrar.setForeground(Color.WHITE);
        
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(231, 76, 60)); // Rojo Alerta
        btnEliminar.setForeground(Color.WHITE);
        
        btnLimpiar = new JButton("Limpiar");

        panelBotonesForm.add(btnRegistrar);
        panelBotonesForm.add(btnModificar);
        panelBotonesForm.add(btnEliminar);
        panelBotonesForm.add(btnLimpiar);

        // Colocar los botones en el layout del formulario
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 6, 5, 6);
        panelFormulario.add(panelBotonesForm, gbc);

        panelPrincipal.add(panelFormulario, BorderLayout.WEST);

        // ==========================================
        // 2. PANEL DER/CENTRO: Cuadrícula JTable
        // ==========================================
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Asignaturas Registradas en el Sistema "));

        String[] columnas = {"ID", "Código", "Nombre del Curso", "Créditos", "Horas"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquea la edición directa sobre las celdas de la cuadrícula
            }
        };

        tblCursos = new JTable(modeloTabla);
        tblCursos.setRowHeight(24);
        tblCursos.getTableHeader().setReorderingAllowed(false);
        
        // Ocultamos la columna ID para fines visuales pero la mantenemos mapeada en el modelo indexado
        tblCursos.getColumnModel().getColumn(0).setMinWidth(0);
        tblCursos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCursos.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scroll = new JScrollPane(tblCursos);
        panelTabla.add(scroll, BorderLayout.CENTER);
        
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        add(panelPrincipal);
    }
}