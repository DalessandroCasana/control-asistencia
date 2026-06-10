package com.sistema.asistencia;

import com.sistema.asistencia.controller.LoginController;
import com.sistema.asistencia.view.FrmLogin;
import java.awt.EventQueue;

public class App {
    public static void main(String[] args) {
        // Hilo seguro de Swing para inicializar interfaces gráficas en entornos de escritorio (EDT)
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Instanciamos la vista
                    FrmLogin formularioLogin = new FrmLogin();
                    
                    // Inicializamos el controlador pasándole la vista para que la administre
                    new LoginController(formularioLogin);
                    
                    // Hacemos visible la interfaz gráfica
                    formularioLogin.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}