package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TOrden;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaPedido extends JFrame implements IGUI {

    private JTextField campoMesa;
    private JTextField campoEmpleado;
    private JButton crear;
    private JButton botonanyadir;
    private JButton botonquitar;

    private Integer pedidoIdTemp;

    public GUI_AltaPedido() {
        super("Alta Pedido");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initGUI() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel labelMesa = new JLabel("ID Mesa:");
        campoMesa = new JTextField(10);

        JLabel labelEmpleado = new JLabel("ID Empleado:");
        campoEmpleado = new JTextField(10);

        crear = new JButton("Crear Pedido");
        crear.addActionListener(e -> crearPedido());

         botonanyadir = new JButton("Añadir Producto");
        botonanyadir.setEnabled(false);
        botonanyadir.addActionListener(e -> agregarProducto());

        botonquitar = new JButton("Quitar Producto");
        botonquitar.setEnabled(false);
        botonquitar.addActionListener(e -> quitarProducto());

       gbc.gridx = 0; gbc.gridy = 0; panel.add(labelMesa, gbc);
        gbc.gridx = 1; panel.add(campoMesa, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(labelEmpleado, gbc);
        gbc.gridx = 1; panel.add(campoEmpleado, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(crear, gbc);
        gbc.gridy = 3; panel.add(botonanyadir, gbc);
        gbc.gridy = 4; panel.add(botonquitar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void crearPedido() {
        try {
            int idMesa = Integer.parseInt(campoMesa.getText());
            int idEmpleado = Integer.parseInt(campoEmpleado.getText());

            TPedido pedido = new TPedido();
            pedido.setIdMesa(idMesa);
            pedido.setIdEmpleado(idEmpleado);

            Context contexto = new Context(Evento.ALTA_PEDIDO, pedido);
            Controlador.getInstance().handle(contexto);

            

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
        }
    }

    private void agregarProducto(){
        if (pedidoIdTemp == null) {
            JOptionPane.showMessageDialog(this, "Primero crea un pedido.");
            return;
        }
        JDialog dialog = new JDialog(this, "Añadir Producto", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelProducto = new JLabel("ID Producto:");
        JTextField campoProducto = new JTextField(10);
        JLabel labelCantidad = new JLabel("Cantidad:");
        JTextField campoCantidad = new JTextField(10);

        JButton botonAgregar = new JButton("Añadir");
        botonAgregar.addActionListener(e -> {
            try {
                int productoId = Integer.parseInt(campoProducto.getText());
                int cantidad = Integer.parseInt(campoCantidad.getText());

                TOrden orden = new TOrden();
                orden.setPedidoID(pedidoIdTemp);
                orden.setProductID(productoId);
                orden.setCantidad(cantidad);

                Context contexto = new Context(Evento.ANADIR_PRODUCTO, orden);
                Controlador.getInstance().handle(contexto);

                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Campos numéricos inválidos");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(labelProducto, gbc);
        gbc.gridx = 1; dialog.add(campoProducto, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(labelCantidad, gbc);
        gbc.gridx = 1; dialog.add(campoCantidad, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(botonAgregar, gbc);

        dialog.setVisible(true);
    }

    private void quitarProducto() {
        if (pedidoIdTemp == null) {
            JOptionPane.showMessageDialog(this, "Primero crea un pedido.");
            return;
        }

        JDialog dialog = new JDialog(this, "Quitar Producto", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelProducto = new JLabel("ID Producto:");
        JTextField campoProducto = new JTextField(10);

        JButton botonQuitar = new JButton("Quitar");
        botonQuitar.addActionListener(e -> {
            try {
                int productoId = Integer.parseInt(campoProducto.getText());

                TOrden orden = new TOrden();
                orden.setPedidoID(pedidoIdTemp);
                orden.setProductID(productoId);

                Context contexto = new Context(Evento.QUITAR_PRODUCTO, orden);
                Controlador.getInstance().handle(contexto);

                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "ID Producto inválido");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(labelProducto, gbc);
        gbc.gridx = 1; dialog.add(campoProducto, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(botonQuitar, gbc);

        dialog.setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.ALTA_PEDIDO) {
            JOptionPane.showMessageDialog(this, "Pedido creado con éxito");
            pedidoIdTemp = ((TPedido) context.getDatos()).getId();
            campoMesa.setText("");
            campoEmpleado.setText("");
            botonanyadir.setEnabled(true);
            botonquitar.setEnabled(true);
        }
    }
}
