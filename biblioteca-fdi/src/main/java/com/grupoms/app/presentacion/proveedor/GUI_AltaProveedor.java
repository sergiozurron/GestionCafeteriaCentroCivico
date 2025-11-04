package com.grupoms.app.presentacion.proveedor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaProveedor extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoNombre;
    private JTextField campoTarifa;
    private JTextField campoTiempoEntrega;

    public GUI_AltaProveedor() {
        setTitle("[ALTA PROVEEDOR]");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        initGUI();
        setVisible(true);
    }

    private void initGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiquetaNombre = new JLabel("Nombre");
        JLabel etiquetaTarifa = new JLabel("Tarifa");
        JLabel etiquetaTiempoEntrega = new JLabel("Tiempo de Entrega");

        campoNombre = new JTextField(20);
        campoTarifa = new JTextField(20);
        campoTiempoEntrega = new JTextField(20);

        JButton botonAlta = new JButton("Alta Proveedor");
        botonAlta.addActionListener(e -> {
            try {
                TProveedor proveedor = new TProveedor();
                proveedor.setNombre(campoNombre.getText());
                proveedor.setTarifa(Double.parseDouble(campoTarifa.getText()));
                proveedor.setTiempoEntrega(Integer.parseInt(campoTiempoEntrega.getText()));
                Context contexto = new Context(Evento.ALTA_PROVEEDOR, proveedor);
                Controlador.getInstance().handle(contexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(etiquetaNombre, gbc);
        gbc.gridx = 1;
        panel.add(campoNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(etiquetaTarifa, gbc);
        gbc.gridx = 1;
        panel.add(campoTarifa, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(etiquetaTiempoEntrega, gbc);
        gbc.gridx = 1;
        panel.add(campoTiempoEntrega, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(botonAlta, gbc);

        add(panel);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.ALTA_PROVEEDOR) {
            JOptionPane.showMessageDialog(this, "Proveedor creado con éxito");
            campoNombre.setText("");
            campoTarifa.setText("");
            campoTiempoEntrega.setText("");
        }
    }
}
