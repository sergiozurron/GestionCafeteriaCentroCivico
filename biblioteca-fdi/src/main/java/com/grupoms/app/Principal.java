package com.grupoms.app;

import javax.swing.*;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class Principal extends JFrame implements IGUI {

    public Principal() {
        setTitle("Gestión Cafetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // MENÚ PEDIDOS
        JMenu menuPedidos = new JMenu("Pedidos");

        JMenuItem altaPedido = new JMenuItem("Alta Pedido");
        altaPedido.addActionListener(e -> abrirVista("GUI_ALTA_PEDIDO"));
        menuPedidos.add(altaPedido);

        JMenuItem mostrarPedido = new JMenuItem("Mostrar Pedido");
        mostrarPedido.addActionListener(e -> abrirVista("GUI_MOSTRAR_PEDIDO"));
        menuPedidos.add(mostrarPedido);

        JMenuItem devolverPedido = new JMenuItem("Devolver Pedido");
        devolverPedido.addActionListener(e -> abrirVista("GUI_DEVOLVER_PEDIDO"));
        menuPedidos.add(devolverPedido);

        JMenuItem confirmarPedido = new JMenuItem("Confirmar Pedido");
        confirmarPedido.addActionListener(e -> abrirVista("GUI_CONFIRMAR_PEDIDO"));
        menuPedidos.add(confirmarPedido);

        // MENÚ MESAS
        JMenu menuMesas = new JMenu("Mesas");
        JMenuItem altaMesa = new JMenuItem("Alta Mesa");
        altaMesa.addActionListener(e -> abrirVista("GUI_ALTA_MESA"));
        menuMesas.add(altaMesa);

        // MENÚ PROVEEDORES
        JMenu menuProveedores = new JMenu("Proveedores");
        JMenuItem altaProveedor = new JMenuItem("Alta Proveedor");
        altaProveedor.addActionListener(e -> abrirVista("GUI_ALTA_PROVEEDOR"));
        menuProveedores.add(altaProveedor);

        menuBar.add(menuPedidos);
        menuBar.add(menuMesas);
        menuBar.add(menuProveedores);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    private void abrirVista(String nombreVista) {
        IGUI vista = FactoriaVistas.getInstance().creaVista(nombreVista);
        if (vista != null)
            vista.actualizar(new Context());
    }

    @Override
    public void actualizar(Context context) {}
}
