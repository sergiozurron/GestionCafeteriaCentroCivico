package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarIngrediente extends JFrame implements IGUI {

    private JTextField campoID;
    private JButton mostrar;
    private JLabel nombreLabel;
    private JLabel precioLabel;
    private JLabel provLabel;

    public GUI_MostrarIngrediente() {
        super("Mostrar Ingrediente");
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

        // Campo ID
        JLabel labelID = new JLabel("ID Ingrediente:");
        campoID = new JTextField(10);

        mostrar = new JButton("Mostrar Ingrediente");
        mostrar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoID.getText());
                TIngrediente ingrediente = new TIngrediente();
                ingrediente.setID(id);

                Context contexto = new Context();
                contexto.setEvento(Evento.MOSTRAR_INGREDIENTE);
                contexto.setDato(ingrediente);

                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
            }
        });

        // Labels para mostrar datos
        nombreLabel = new JLabel();
        precioLabel = new JLabel();
        provLabel = new JLabel();

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelID, gbc);
        gbc.gridx = 1;
        panel.add(campoID, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(mostrar, gbc);

        y++;
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(nombreLabel, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        panel.add(precioLabel, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Proveedor:"), gbc);
        gbc.gridx = 1;
        panel.add(provLabel, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
    	if (context == null)
    		setVisible(true);
       else if (context.getEvento() == Evento.MOSTRAR_INGREDIENTE_OK) {
        TIngrediente ing = (TIngrediente) context.getDatos();
        nombreLabel.setText(ing.getNombre());
        precioLabel.setText(String.valueOf(ing.getPrecio()));
        provLabel.setText(String.valueOf(ing.getIDProveedor()));
    } else if (context.getEvento() == Evento.MOSTRAR_INGREDIENTE_KO) {
        JOptionPane.showMessageDialog(this, "Ingrediente no encontrado en la base de datos");
        nombreLabel.setText("");
        precioLabel.setText("");
        provLabel.setText("");
    }
    }
}
