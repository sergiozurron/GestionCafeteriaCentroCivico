package com.grupoms.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class Principal extends JFrame implements IGUI{

    private JButton buttonPedido;

    @Override
    public void actualizar(Context context) {
       
    }
    public Principal(){
        setTitle("Gestión Cafetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Menú de entidades
        JMenuBar menuBar = new JMenuBar();
        JMenu menuPedidos = new JMenu("Pedidos");
        JMenuItem altaPedido = new JMenuItem("Alta Pedido");
        altaPedido.addActionListener(e -> abrirVista("GUI_ALTA_PEDIDO"));
        menuPedidos.add(altaPedido);

        JMenu menuMesas = new JMenu("Mesas");
        JMenuItem altaMesa = new JMenuItem("Alta Mesa");
        altaMesa.addActionListener(e -> abrirVista("GUI_ALTA_MESA"));
        menuMesas.add(altaMesa);

        menuBar.add(menuPedidos);
        menuBar.add(menuMesas);
        setJMenuBar(menuBar);

        setVisible(true);
    }
    private void abrirVista(String nombreVista) {
        FactoriaVistas.getInstance()
            .creaVista(nombreVista)
            .actualizar(new Context()); // Context vacío, puede pasar datos si quieres
    }

    
}
