package com.sistema.asistencia.view;

import javax.swing.*;
import java.awt.*;

public class FrmLogin extends JFrame {

    public JTextField txtUsuario;
    public JPasswordField txtPassword;
    public JButton btnIngresar;

    public FrmLogin() {
        setTitle("Acceso al Sistema de Asistencia");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(244, 246, 249));

        JLabel lblTitulo = new JLabel("INICIAR SESIÓN", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(44, 62, 80));
        lblTitulo.setBounds(20, 25, 360, 30);
        panel.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Correo Institucional:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUsuario.setBounds(50, 80, 300, 20);
        panel.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsuario.setBounds(50, 105, 300, 30);
        panel.add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPassword.setBounds(50, 145, 300, 20);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setBounds(50, 170, 300, 30);
        panel.add(txtPassword);

        btnIngresar = new JButton("Ingresar al Sistema");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(41, 128, 185));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBounds(50, 225, 300, 38);
        panel.add(btnIngresar);

        add(panel);
    }
}