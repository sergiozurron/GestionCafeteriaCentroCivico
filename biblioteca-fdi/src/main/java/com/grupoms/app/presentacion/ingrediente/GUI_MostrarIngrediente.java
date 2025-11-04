package com.grupoms.app.presentacion.ingrediente;
import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarIngrediente extends JFrame implements IGUI{
    private JTextField nombre;
    private JTextField precio;
    private JTextField prov;

    public GUI_MostrarIngrediente() {
        super("Mostrar Ingrediente");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void initGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        JLabel labelNombre = new JLabel("Nombre:");
        nombre = new JTextField(15);
        nombre.setEditable(false);

        JLabel labelPrecio = new JLabel("Precio:");
        precio = new JTextField(15);
        precio.setEditable(false);

        JLabel labelProv = new JLabel("Proveedor:");
        prov = new JTextField(15);
        prov.setEditable(false);

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelNombre, gbc);
        gbc.gridx = 1;
        panel.add(nombre, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelPrecio, gbc);
        gbc.gridx = 1;
        panel.add(precio, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelProv, gbc);
        gbc.gridx = 1;
        panel.add(prov, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.MOSTRAR_INGREDIENTE_OK) {
            TIngrediente ing = (TIngrediente) context.getDatos();
            if (ing != null) {
                nombre.setText(ing.getNombre());
                precio.setText(String.valueOf(ing.getPrecio()));
                prov.setText(String.valueOf(ing.getIDProveedor()));
            } else {
                JOptionPane.showMessageDialog(this, "Ingrediente no encontrado");
            }
        } else if (context.getEvento() == Evento.MOSTRAR_INGREDIENTE_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar el ingrediente");
        }
    }
}
