package com.sistema.asistencia.controller;

import com.sistema.asistencia.view.FrmTomaAsistencia;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TomaAsistenciaController implements ActionListener {

    private final FrmTomaAsistencia vista;
    private final int idSeccion;

    public TomaAsistenciaController(FrmTomaAsistencia vista, int idSeccion) {
        this.vista = vista;
        this.idSeccion = idSeccion;

        
        this.vista.btnGuardarAsistencia.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);

        
        cargarEstudiantesMatriculados();
    }

    private void cargarEstudiantesMatriculados() {
        vista.modeloTabla.setRowCount(0);
        
        
        
        vista.modeloTabla.addRow(new Object[]{10, "U20265501", "Alvarado Ruiz, Luis Fernando", "Asistió"});
        vista.modeloTabla.addRow(new Object[]{11, "U20265502", "Gomez Peralta, Ana Claudia", "Asistió"});
        vista.modeloTabla.addRow(new Object[]{12, "U20265503", "Palacios Vite, Renzo Omar", "Asistió"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnCancelar) {
            vista.dispose();
        } else if (e.getSource() == vista.btnGuardarAsistencia) {
            procesarGuardadoDeAsistencia();
        }
    }

    private void procesarGuardadoDeAsistencia() {
        int totalFilas = vista.modeloTabla.getRowCount();
        if (totalFilas == 0) return;

        
        int opc = JOptionPane.showConfirmDialog(vista, 
            "¿Desea cerrar el registro y guardar la asistencia de los " + totalFilas + " alumnos?", 
            "Confirmar Guardado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opc == JOptionPane.YES_OPTION) {
            
            
            for (int i = 0; i < totalFilas; i++) {
                int idAlumno = Integer.parseInt(vista.modeloTabla.getValueAt(i, 0).toString());
                String codigo = vista.modeloTabla.getValueAt(i, 1).toString();
                String estadoSelected = vista.modeloTabla.getValueAt(i, 3).toString();

                
                System.out.println("Insertando en PostgreSQL -> Seccion: " + idSeccion + 
                                   " | AlumnoID: " + idAlumno + " (" + codigo + ") | Estado: " + estadoSelected);
            }

            JOptionPane.showMessageDialog(vista, "La asistencia ha sido guardada y congelada con éxito en el servidor.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.dispose(); 
        }
    }
}