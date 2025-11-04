package com.grupoms.app.presentacion.mesa;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.mesa.TMesa;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_BajaMesa extends JFrame implements IGUI{
    
    private JTextField idMesa;
    private JButton baja;

    public GUI_BajaMesa() {
        super("Baja Mesa");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.ALTA_MESA_OK) {
            JOptionPane.showMessageDialog(this, "Mesa dada de baja con éxito");
            idMesa.setText("");
        } else if (context.getEvento() == Evento.ALTA_MESA_KO) {
            JOptionPane.showMessageDialog(this, "Error al dar de baja la mesa");
        }

    }

    private void initGUI() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelmesa = new JLabel("ID Mesa:");
        idMesa = new JTextField(10);
        
        baja = new JButton("Baja Mesa");
        baja.addActionListener(e -> {
            try {
                TMesa mesa = new TMesa();
                mesa.setId(Integer.parseInt(idMesa.getText()));

                Context contexto = new Context(Evento.BAJA_MESA, mesa);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
            }
        });

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelmesa, gbc);
        gbc.gridx = 1;
        panel.add(idMesa, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(baja, gbc);

        add(panel, BorderLayout.CENTER);
    }
}
