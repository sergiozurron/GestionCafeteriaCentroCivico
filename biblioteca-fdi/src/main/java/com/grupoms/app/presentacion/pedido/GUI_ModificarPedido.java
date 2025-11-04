package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarPedido extends JFrame implements IGUI{
    
    private JTextField campoIdPedido;
    private JTextField campoMesa;
    private JTextField campoEmpleado;
    private JButton botonModificar;

    public GUI_ModificarPedido() {
        setTitle("Modificar Pedido");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        initGUI();
        setVisible(true);
    }

    private void initGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelIdPedido = new JLabel("ID Pedido:");
        campoIdPedido = new JTextField(10);

        JLabel labelMesa = new JLabel("ID Mesa:");
        campoMesa = new JTextField(10);

        JLabel labelEmpleado = new JLabel("ID Empleado:");
        campoEmpleado = new JTextField(10);

        botonModificar = new JButton("Modificar Pedido");
        botonModificar.addActionListener(e -> {
            try {
                int idPedido = Integer.parseInt(campoIdPedido.getText());
                Integer idMesa = campoMesa.getText().isEmpty() ? null : Integer.parseInt(campoMesa.getText());
                Integer idEmpleado = campoEmpleado.getText().isEmpty() ? null : Integer.parseInt(campoEmpleado.getText());

                TPedido pedido = new TPedido();
                pedido.setId(idPedido);
                if (idMesa != null) pedido.setIdMesa(idMesa);
                if (idEmpleado != null) pedido.setIdEmpleado(idEmpleado);

                Context contexto = new Context(Evento.MODIFICAR_PEDIDO, pedido);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelIdPedido, gbc);
        gbc.gridx = 1;
        panel.add(campoIdPedido, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(labelMesa, gbc);
        gbc.gridx = 1;
        panel.add(campoMesa, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(labelEmpleado, gbc);
        gbc.gridx = 1;
        panel.add(campoEmpleado, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(botonModificar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.MODIFICAR_PEDIDO) {
            JOptionPane.showMessageDialog(this, "Pedido modificado con éxito");
            campoIdPedido.setText("");
            campoMesa.setText("");
            campoEmpleado.setText("");
        }
    }
}
