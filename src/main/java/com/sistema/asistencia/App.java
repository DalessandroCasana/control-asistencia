package com.sistema.asistencia;

import com.formdev.flatlaf.FlatLightLaf; 
import com.sistema.asistencia.controller.LoginController;
import com.sistema.asistencia.view.FrmLogin;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
        try {
            
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo inicializar el tema visual: " + ex.getMessage());
        }

        
        FrmLogin vistaLogin = new FrmLogin();
        new LoginController(vistaLogin);
        vistaLogin.setVisible(true);
    }
}