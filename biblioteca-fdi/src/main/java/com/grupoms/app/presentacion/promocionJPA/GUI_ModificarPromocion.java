package com.grupoms.app.presentacion.promocionJPA;


import javax.swing.*;

import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;

public class GUI_ModificarPromocion extends JFrame implements IGUI {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField id;
    private JTextField tipo;
    private JTextField descuento;

    private JButton modificar;

    public GUI_ModificarPromocion() {
        super("Modificar Promoción");
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

        JLabel labelId = new JLabel("ID de la Promoción:");
        id = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelId, gbc);
        gbc.gridx = 1;
        panel.add(id, gbc);

        JLabel labelTipo = new JLabel("Tipo de Promoción:");
        tipo = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(labelTipo, gbc);
        gbc.gridx = 1;
        panel.add(tipo, gbc);

        JLabel labelDescuento = new JLabel("Descuento:");
        descuento = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(labelDescuento, gbc);
        gbc.gridx = 1;
        panel.add(descuento, gbc);

        // Botón Modificar
        modificar = new JButton("Modificar");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        modificar.addActionListener(e -> modificarPromocion());

        panel.add(modificar, gbc);
        add(panel, BorderLayout.CENTER);
    }

    private void modificarPromocion() {
        try {
            int promoId = Integer.parseInt(id.getText());
            String tipoPromocion = tipo.getText();
            double descuentoValor = Double.parseDouble(descuento.getText());

            TPromocion promocion = new TPromocion();
            promocion.setId(promoId);
            promocion.setTipo(tipoPromocion);
            promocion.setDescuento(descuentoValor);

            Context context = new Context(Evento.MODIFICAR_PROMOCION, promocion);
            Controlador.getInstance().handle(context);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID y Descuento deben ser numéricos");
        }
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
        } else if (context.getEvento() == Evento.MODIFICAR_PROMOCION_OK) {
            JOptionPane.showMessageDialog(this, "Promoción modificada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar campos
            id.setText("");
            tipo.setText("");
            descuento.setText("");
        } else if (context.getEvento() == Evento.MODIFICAR_PROMOCION_KO) {
            JOptionPane.showMessageDialog(this, "No se ha podido modificar la promoción", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
