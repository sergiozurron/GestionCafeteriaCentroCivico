package com.grupoms.app;

import javax.swing.*;
import java.awt.*;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;
import com.grupoms.app.presentacion.IGUI;

public class Principal extends JFrame {

    private static final long serialVersionUID = 1L;

    public Principal() {
        setTitle("Gestión Cafetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // --- Título superior ---
        JLabel titulo = new JLabel("Gestión de la Cafetería", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // --- Panel principal con botones ---
        JPanel panelCentral = new JPanel(new GridLayout(2, 3, 20, 20));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Crear secciones
        panelCentral.add(crearPanelCategoria("Pedidos", new String[][]{
                {"Alta Pedido", FactoriaVistas.GUI_ALTA_PEDIDO},
                {"Mostrar Pedido", FactoriaVistas.GUI_MOSTRAR_PEDIDO},
                {"Devolver Pedido", FactoriaVistas.GUI_DEVOLVER_PEDIDO},
                {"Modificar Pedido", FactoriaVistas.GUI_MODIFICAR_PEDIDO},
                {"Listar Pedidos", FactoriaVistas.GUI_LISTAR_PEDIDO},
                {"Anyadir Producto", FactoriaVistas.GUI_ALTA_ORDEN}
        }));

        panelCentral.add(crearPanelCategoria("Mesas", new String[][]{
                {"Alta Mesa", FactoriaVistas.GUI_ALTA_MESA},
                {"Baja Mesa", FactoriaVistas.GUI_BAJA_MESA},
                {"Modificar Mesa", FactoriaVistas.GUI_MODIFICAR_MESA},
                {"Mostrar Mesa", FactoriaVistas.GUI_MOSTRAR_MESA},
                {"Listar Mesas", FactoriaVistas.GUI_LISTAR_MESAS}
        }));

        panelCentral.add(crearPanelCategoria("Proveedores", new String[][]{
                {"Alta Proveedor", FactoriaVistas.GUI_ALTA_PROVEEDOR},
                {"Baja Proveedor", FactoriaVistas.GUI_BAJA_PROVEEDOR},
                {"Modificar Proveedor", FactoriaVistas.GUI_MODIFICAR_PROVEEDOR},
                {"Mostrar Proveedor", FactoriaVistas.GUI_MOSTRAR_PROVEEDOR},
                {"Listar Proveedores", FactoriaVistas.GUI_LISTAR_PROVEEDORES}
        }));

        panelCentral.add(crearPanelCategoria("Ingredientes", new String[][]{
                {"Alta Ingrediente", FactoriaVistas.GUI_ALTA_INGREDIENTE},
                {"Baja Ingrediente", FactoriaVistas.GUI_BAJA_INGREDIENTE},
                {"Modificar Ingrediente", FactoriaVistas.GUI_MODIFICAR_INGREDIENTE},
                {"Mostrar Ingrediente", FactoriaVistas.GUI_MOSTRAR_INGREDIENTE},
                {"Listar Ingredientes", FactoriaVistas.GUI_LISTAR_INGREDIENTES}
        }));

        panelCentral.add(crearPanelCategoria("Empleados", new String[][]{
                {"Alta Empleado", FactoriaVistas.GUI_ALTA_EMPLEADO},
                {"Baja Empleado", FactoriaVistas.GUI_BAJA_EMPLEADO},
                {"Modificar Empleado", FactoriaVistas.GUI_MODIFICAR_EMPLEADO},
                {"Mostrar Empleado", FactoriaVistas.GUI_MOSTRAR_EMPLEADO},
                {"Listar Empleados", FactoriaVistas.GUI_LISTAR_EMPLEADOS}
        }));

        panelCentral.add(crearPanelCategoria("Productos", new String[][]{
                {"Alta Producto", FactoriaVistas.GUI_ALTA_PRODUCTO},
                {"Baja Producto", FactoriaVistas.GUI_BAJA_PRODUCTO},
                {"Modificar Producto", FactoriaVistas.GUI_MODIFICAR_PRODUCTO},
                {"Mostrar Producto", FactoriaVistas.GUI_MOSTRAR_PRODUCTO},
                {"Listar Productos", FactoriaVistas.GUI_LISTAR_PRODUCTOS}
        }));

        add(panelCentral, BorderLayout.CENTER);

        // --- Pie de página ---
        JLabel footer = new JLabel("Gestión Cafetería - GrupoMS", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footer, BorderLayout.SOUTH);

        // Mostrar ventana principal
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

            // Acción: abrir la vista solo cuando se haga click
            boton.addActionListener(e -> {
                IGUI vista = FactoriaVistas.getInstance().creaVista(opcion[1]);
                if (vista != null) {
                    vista.actualizar(null); // inicializamos la vista
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo abrir la vista: " + opcion[0],
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            botonesPanel.add(boton);
        }

        panel.add(botonesPanel, BorderLayout.CENTER);
        return panel;
    }
}
