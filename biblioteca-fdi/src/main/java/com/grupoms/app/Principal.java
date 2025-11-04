package com.grupoms.app;

import javax.swing.*;
import java.awt.*;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class Principal extends JFrame implements IGUI {

    public Principal() {
        setTitle("Gestión Cafetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // --- Título superior ---
        JLabel titulo = new JLabel("Gestión de la Cafetería", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // --- Panel principal con botones organizados ---
        JPanel panelCentral = new JPanel(new GridLayout(1, 3, 20, 20));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- Sección Pedidos ---
        JPanel panelPedidos = crearPanelCategoria("Pedidos", new String[][]{
            {"Alta Pedido", "GUI_ALTA_PEDIDO"},
            {"Mostrar Pedido", "GUI_MOSTRAR_PEDIDO"},
            {"Devolver Pedido", "GUI_DEVOLVER_PEDIDO"},
            {"Confirmar Pedido", "GUI_CONFIRMAR_PEDIDO"}
        });

        // --- Sección Mesas ---
        JPanel panelMesas = crearPanelCategoria("Mesas", new String[][]{
            {"Alta Mesa", "GUI_ALTA_MESA"},
            {"Mostrar Mesa", "GUI_MOSTRAR_MESA"}
        });

        // --- Sección Proveedores ---
        JPanel panelProveedores = crearPanelCategoria("Proveedores", new String[][]{
            {"Alta Proveedor", "GUI_ALTA_PROVEEDOR"},
            {"Mostrar Proveedor", "GUI_MOSTRAR_PROVEEDOR"}
        });

         JPanel panelIngrediente = crearPanelCategoria("Ingrerdientes", new String[][]{
            {"Alta Ingrediente", "GUI_ALTA_INGREDIENTE"},
            {"Baja Ingrediente", "GUI_BAJA_INGREDIENTE"},
            {"Modificar Ingrediente", "GUI_MODIFICAR_INGREDIENTE"}
        });

        panelCentral.add(panelPedidos);
        panelCentral.add(panelMesas);
        panelCentral.add(panelProveedores);
        panelCentral.add(panelIngrediente);


        add(panelCentral, BorderLayout.CENTER);

        // --- Pie de página ---
        JLabel footer = new JLabel("Gestión Cafetería - GrupoMS", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel crearPanelCategoria(String titulo, String[][] opciones) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1, true),
                titulo
        ));

        JPanel botonesPanel = new JPanel(new GridLayout(opciones.length, 1, 8, 8));
        for (String[] opcion : opciones) {
            JButton boton = new JButton(opcion[0]);
            boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            boton.setFocusPainted(false);
            boton.addActionListener(e -> abrirVista(opcion[1]));
            botonesPanel.add(boton);
        }

        panel.add(botonesPanel, BorderLayout.CENTER);
        return panel;
    }

    private void abrirVista(String nombreVista) {
        IGUI vista = FactoriaVistas.getInstance().creaVista(nombreVista);
        if (vista != null)
            vista.actualizar(new Context());
    }

    @Override
    public void actualizar(Context context) {}
}
