package com.sistema.asistencia.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmAsignarHorarios extends JDialog {

    public JComboBox<String> cboSecciones;
    public JComboBox<String> cboDias;
    public JTextField txtHoraInicio;
    public JTextField txtHoraFin;
    public JTextField txtAula;

    public JButton btnGuardar;
    public JButton btnLimpiar;
    public JButton btnCerrar;

    public JTable tblHorarios;
    public DefaultTableModel modeloTabla;

    public FrmAsignarHorarios(Window padre) {
        super(padre, "Módulo de Gestión - Asignación de Bloques Horarios", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(820, 450);
        setLocationRelativeTo(padre);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(244, 246, 249));

        // FORMULARIO (IZQUIERDA)
        JPanel panelForm = new JPanel(null);
        panelForm.setPreferredSize(new Dimension(310, 0));
        panelForm.setBorder(BorderFactory.createTitledBorder(" Planificación del Horario "));
        panelForm.setOpaque(false);

        JLabel lblSeccion = new JLabel("Seleccione Sección Aperturada:");
        lblSeccion.setBounds(15, 25, 280, 20);
        cboSecciones = new JComboBox<>();
        cboSecciones.setBounds(15, 45, 270, 30);

        JLabel lblDia = new JLabel("Día de la Semana:");
        lblDia.setBounds(15, 85, 280, 20);
        cboDias = new JComboBox<>(new String[]{"-- Seleccione un Día --", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"});
        cboDias.setBounds(15, 105, 270, 30);

        JLabel lblInicio = new JLabel("Hora Inicio (HH:MM - Ej: 08:00):");
        lblInicio.setBounds(15, 145, 280, 20);
        txtHoraInicio = new JTextField();
        txtHoraInicio.setBounds(15, 165, 270, 30);

        JLabel lblFin = new JLabel("Hora Fin (HH:MM - Ej: 10:00):");
        lblFin.setBounds(15, 205, 280, 20);
        txtHoraFin = new JTextField();
        txtHoraFin.setBounds(15, 225, 270, 30);

        JLabel lblAula = new JLabel("Aula Asignada (Ej: A-302):");
        lblAula.setBounds(15, 265, 280, 20);
        txtAula = new JTextField();
        txtAula.setBounds(15, 285, 270, 30);

        btnGuardar = new JButton("Asignar Bloque");
        btnGuardar.setBackground(new Color(155, 89, 182));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setBounds(15, 330, 270, 35);

        panelForm.add(lblSeccion);
        panelForm.add(cboSecciones);
        panelForm.add(lblDia);
        panelForm.add(cboDias);
        panelForm.add(lblInicio);
        panelForm.add(txtHoraInicio);
        panelForm.add(lblFin);
        panelForm.add(txtHoraFin);
        panelForm.add(lblAula);
        panelForm.add(txtAula);
        panelForm.add(btnGuardar);

        panelPrincipal.add(panelForm, BorderLayout.WEST);

        // TABLA DE VISUALIZACIÓN (DERECHA)
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(" Distribución del Tiempo Académico "));
        panelTabla.setOpaque(false);

        String[] columnas = {"ID", "Sección", "Curso / Asignatura", "Día", "Inicio", "Fin", "Aula"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblHorarios = new JTable(modeloTabla);
        tblHorarios.setRowHeight(24);
        tblHorarios.getColumnModel().getColumn(0).setPreferredWidth(35);
        tblHorarios.getColumnModel().getColumn(1).setPreferredWidth(65);
        tblHorarios.getColumnModel().getColumn(2).setPreferredWidth(160);

        JScrollPane scroll = new JScrollPane(tblHorarios);
        panelTabla.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        btnCerrar = new JButton("Cerrar Panel");
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setOpaque(false);
        panelSur.add(btnCerrar);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }
}