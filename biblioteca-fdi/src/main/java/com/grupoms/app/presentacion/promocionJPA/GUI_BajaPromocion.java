package com.grupoms.app.presentacion.promocionJPA;

import javax.swing.*;

import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;

public class GUI_BajaPromocion extends JFrame implements IGUI {
    public GUI_BajaPromocion() {
        super("Baja Promoción");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelId = new JLabel("ID de la Promoción a dar de baja:");
        JTextField campoId = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelId, gbc);
        gbc.gridx = 1;
        panel.add(campoId, gbc);

        // Botón Aceptar
        JButton aceptar = new JButton("Aceptar");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        aceptar.addActionListener(e -> BajaPromocion(campoId.getText()));

        panel.add(aceptar, gbc);
        add(panel, BorderLayout.CENTER);
    }

    private void BajaPromocion(String idText) {
        try {
            int id = Integer.parseInt(idText);
            TPromocion promocion = new TPromocion();
            promocion.setId(id);
            Context contexto = new Context(Evento.BAJA_PROMOCION, promocion);
            Controlador.getInstance().handle(contexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, ingrese un número entero.");
        }
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
        } else if (context.getEvento() == Evento.BAJA_PROMOCION_OK) {
            JOptionPane.showMessageDialog(this, "Promoción dada de baja con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (context.getEvento() == Evento.BAJA_PROMOCION_KO) {
            JOptionPane.showMessageDialog(this, "No se ha podido dar de baja la promoción", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
