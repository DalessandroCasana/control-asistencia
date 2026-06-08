package com.sistema.asistencia.controller;

import com.sistema.asistencia.view.FrmDashboardAdmin;
import com.sistema.asistencia.view.FrmReportes;
import com.sistema.asistencia.view.FrmLogin;
// Importa aquí tus otras vistas si las necesitas (ej: FrmCursos, FrmAsignarHorarios)

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardAdminController implements ActionListener {

    private final FrmDashboardAdmin vista;

    public DashboardAdminController(FrmDashboardAdmin vista) {
        this.vista = vista;

        // Suscribir los botones del menú del Administrador
        this.vista.btnCursos.addActionListener(this);
        this.vista.btnSecciones.addActionListener(this);
        this.vista.btnHorarios.addActionListener(this);
        this.vista.btnReportes.addActionListener(this); // Escuchando el botón de reporte
        this.vista.btnCerrarSesion.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

        if (origen == vista.btnReportes) {
            // Disparar la modal de Reportes pasándole este frame como padre
            FrmReportes frmRep = new FrmReportes(vista);
            new ReportesController(frmRep);
            frmRep.setVisible(true);
        } 
        else if (origen == vista.btnCursos) {
            JOptionPane.showMessageDialog(vista, "Abriendo panel de Cursos...", "Módulo Cursos", JOptionPane.INFORMATION_MESSAGE);
            // Aquí puedes instanciar tu controlador de cursos si deseas enlazarlo:
            // new CursosController(new FrmCursos());
        } 
        else if (origen == vista.btnSecciones) {
            // Instanciar la vista de secciones pasándole el frame administrador como padre
            com.sistema.asistencia.view.FrmGestionSecciones frmSec = new com.sistema.asistencia.view.FrmGestionSecciones(vista);
            // Inyectar el controlador para manejar la UI y la persistencia
            new GestionSeccionesController(frmSec);
            // Mostrar la ventana modal
            frmSec.setVisible(true);
        } 
        else if (origen == vista.btnHorarios) {
            JOptionPane.showMessageDialog(vista, "Abriendo asignación de Horarios...", "Módulo Horarios", JOptionPane.INFORMATION_MESSAGE);
            // Ejemplo de enlace previo:
            // com.sistema.asistencia.view.FrmAsignarHorarios frmHor = new com.sistema.asistencia.view.FrmAsignarHorarios();
            // new AsignarHorariosController(frmHor);
            // frmHor.setVisible(true);
        } 
        else if (origen == vista.btnCerrarSesion) {
            int opc = JOptionPane.showConfirmDialog(vista, "¿Desea salir del panel de administración?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
            if (opc == JOptionPane.YES_OPTION) {
                vista.dispose();
                FrmLogin loginVista = new FrmLogin();
                new LoginController(loginVista);
                loginVista.setVisible(true);
            }
        }
    }
}