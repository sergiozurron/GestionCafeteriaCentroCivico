package com.grupoms.app.presentacion.producto;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.producto.TEntradaReceta;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_DesvincularIngredienteProducto extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoIdProducto, campoIdIngrediente;
    private JButton botonDesvincular;

    public GUI_DesvincularIngredienteProducto() {
        super("Desvincular Ingrediente de Producto");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ----- FILA 0: ID PRODUCTO -----
        JLabel labelProducto = new JLabel("ID Producto:");
        campoIdProducto = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelProducto, gbc);

        gbc.gridx = 1;
        panel.add(campoIdProducto, gbc);

        // ----- FILA 1: ID INGREDIENTE -----
        JLabel labelIngrediente = new JLabel("ID Ingrediente:");
        campoIdIngrediente = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelIngrediente, gbc);

        gbc.gridx = 1;
        panel.add(campoIdIngrediente, gbc);

        // ----- FILA 2: BOTÓN -----
        botonDesvincular = new JButton("Desvincular");
        botonDesvincular.addActionListener(e -> desvincular());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(botonDesvincular, gbc);

        add(panel);
    }

    private void desvincular() {

        try {
            int idProducto = Integer.parseInt(campoIdProducto.getText());
            int idIngrediente = Integer.parseInt(campoIdIngrediente.getText());

            TEntradaReceta entrada = new TEntradaReceta();
            entrada.setProductoID(idProducto);
            entrada.setIngredienteID(idIngrediente);

            Context contexto = new Context(
                    Evento.DESVINCULAR_PRODUCTO_INGREDIENTE,
                    entrada
            );

            Controlador.getInstance().handle(contexto);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Los IDs deben ser numéricos");
        }
    }

    @Override
    public void actualizar(Context context) {

        if (context == null) {
            setVisible(true);
            return;
        }

        switch (context.getEvento()) {

        case Evento.DESVINCULAR_PRODUCTO_INGREDIENTE_OK:
            JOptionPane.showMessageDialog(this,
                    "Ingrediente desvinculado correctamente");
            campoIdProducto.setText("");
            campoIdIngrediente.setText("");
            dispose();
            break;

        case Evento.DESVINCULAR_PRODUCTO_INGREDIENTE_KO:
        	String mensaje = (String) context.getDatos();
		    JOptionPane.showMessageDialog(this, mensaje);

        default:
            break;
        }
    }
}
