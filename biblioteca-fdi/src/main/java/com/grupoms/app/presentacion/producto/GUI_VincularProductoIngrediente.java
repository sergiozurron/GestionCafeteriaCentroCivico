package com.grupoms.app.presentacion.producto;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.producto.TEntradaReceta;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_VincularProductoIngrediente extends JFrame implements IGUI {

    private JTextField campoIdProducto;
    private JTextField campoIdIngrediente;
    private JButton botonVincular;

    public GUI_VincularProductoIngrediente() {
        super("Vincular Ingrediente a Producto");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JLabel labelProducto = new JLabel("ID Producto:");
        campoIdProducto = new JTextField(10);

        JLabel labelIngrediente = new JLabel("ID Ingrediente:");
        campoIdIngrediente = new JTextField(10);

        botonVincular = new JButton("Vincular");
        botonVincular.addActionListener(e -> vincular());

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelProducto, gbc);
        gbc.gridx = 1;
        panel.add(campoIdProducto, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(labelIngrediente, gbc);
        gbc.gridx = 1;
        panel.add(campoIdIngrediente, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(botonVincular, gbc);

        add(panel);
    }

    private void vincular() {
        try {
            int idProducto = Integer.parseInt(campoIdProducto.getText());
            int idIngrediente = Integer.parseInt(campoIdIngrediente.getText());

            TEntradaReceta entrada = new TEntradaReceta();
            entrada.setProductoID(idProducto);
            entrada.setIngredienteID(idIngrediente);

            Context contexto = new Context(Evento.VINCULAR_PRODUCTO_INGREDIENTE, entrada);
            Controlador.getInstance().handle(contexto);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los campos deben ser numéricos");
        }
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }

        switch (context.getEvento()) {
            case Evento.VINCULAR_PRODUCTO_INGREDIENTE_OK:
                JOptionPane.showMessageDialog(this, "Ingrediente vinculado correctamente");
                campoIdProducto.setText("");
                campoIdIngrediente.setText("");
                dispose();
                break;

            case Evento.VINCULAR_PRODUCTO_INGREDIENTE_KO:
            	String mensaje = (String) context.getDatos();
			    JOptionPane.showMessageDialog(this, mensaje);
        }
    }

	
}
