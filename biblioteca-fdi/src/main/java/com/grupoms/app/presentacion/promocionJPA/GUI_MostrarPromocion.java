package com.grupoms.app.presentacion.promocionJPA;

import javax.swing.*;

import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;

public class GUI_MostrarPromocion extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoID;
    private JButton mostrar;

    private JLabel tipoLabel;
    private JLabel descuentoLabel;
    private JLabel activoLabel;

    public GUI_MostrarPromocion() {
        super("Mostrar Promoción");
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
        JLabel labelID = new JLabel("ID Promoción:");
        campoID = new JTextField(10);

        mostrar = new JButton("Mostrar Promoción");
        mostrar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoID.getText());
                Context contexto = new Context(Evento.MOSTRAR_PROMOCION, id);
                Controlador.getInstance().handle(contexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
            }
        });

        tipoLabel = new JLabel();
        descuentoLabel = new JLabel();
        activoLabel = new JLabel();

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
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        panel.add(tipoLabel, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Descuento:"), gbc);
        gbc.gridx = 1;
        panel.add(descuentoLabel, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Activo:"), gbc);
        gbc.gridx = 1;
        panel.add(activoLabel, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }

        switch (context.getEvento()) {

            case Evento.MOSTRAR_PROMOCION_OK:
                TPromocion p = (TPromocion) context.getDatos();
                if (p != null) {
                    tipoLabel.setText(p.getTipo());
                    descuentoLabel.setText(String.valueOf(p.getDescuento()));
                    activoLabel.setText(p.getActivo() ? "Sí" : "No");
                }
                break;

            case Evento.MOSTRAR_PROMOCION_KO:
                JOptionPane.showMessageDialog(this, "Promoción no encontrada en la base de datos");
                tipoLabel.setText("");
                descuentoLabel.setText("");
                activoLabel.setText("");
                break;
        }
    }
}
