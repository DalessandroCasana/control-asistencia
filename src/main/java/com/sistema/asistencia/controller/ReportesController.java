package com.sistema.asistencia.controller;

import com.sistema.asistencia.model.dao.SeccionDAO;
import com.sistema.asistencia.model.entity.Seccion;
import com.sistema.asistencia.service.ReporteExcelService; // Importación corregida a tu ruta exacta
import com.sistema.asistencia.view.FrmReportes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class ReportesController implements ActionListener {

    private final FrmReportes vista;
    private final SeccionDAO seccionDAO;
    private List<Seccion> listaSeccionesCache;

    public ReportesController(FrmReportes vista) {
        this.vista = vista;
        this.seccionDAO = new SeccionDAO();

        this.vista.btnExportarExcel.addActionListener(this);
        this.vista.btnCerrar.addActionListener(this);

        cargarSeccionesEnCombo();
    }

    private void cargarSeccionesEnCombo() {
        try {
            vista.cboSeccionesReporte.removeAllItems();
            vista.cboSeccionesReporte.addItem("-- Seleccione Curso Operativo --");

            listaSeccionesCache = seccionDAO.listar();
            for (Seccion s : listaSeccionesCache) {
                vista.cboSeccionesReporte.addItem("Sección " + s.getCodigoSeccion() + " - " + s.getNombreCurso());
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar combo reportes: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnCerrar) {
            vista.dispose();
        } else if (e.getSource() == vista.btnExportarExcel) {
            ejecutarProcesoExportacion();
        }
    }

    private void ejecutarProcesoExportacion() {
        int idx = vista.cboSeccionesReporte.getSelectedIndex();
        if (idx == 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un curso del listado para generar el archivo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idSeccionReal = listaSeccionesCache.get(idx - 1).getIdSeccion();
        String codigoSec = listaSeccionesCache.get(idx - 1).getCodigoSeccion();

        JFileChooser exploradorArchivos = new JFileChooser();
        exploradorArchivos.setDialogTitle("Guardar Reporte Estadístico - Sistema de Asistencia");
        exploradorArchivos.setSelectedFile(new File("Reporte_Asistencia_Seccion_" + codigoSec + ".xlsx"));

        int OasisSelection = exploradorArchivos.showSaveDialog(vista);

        if (OasisSelection == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = exploradorArchivos.getSelectedFile();
            String rutaAbsoluta = archivoSeleccionado.getAbsolutePath();

            if (!rutaAbsoluta.toLowerCase().endsWith(".xlsx")) {
                rutaAbsoluta += ".xlsx";
            }

            try {
                // Invocación segura con el empaquetado correcto
                ReporteExcelService.exportarAsistenciaSeccion(idSeccionReal, rutaAbsoluta);
                
                JOptionPane.showMessageDialog(vista, 
                    "¡Libro Excel compilado y exportado con éxito!\nRuta: " + rutaAbsoluta, 
                    "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, 
                    "Error crítico al compilar la estructura binaria del Excel:\n" + ex.getMessage(), 
                    "Error de Sistema", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}