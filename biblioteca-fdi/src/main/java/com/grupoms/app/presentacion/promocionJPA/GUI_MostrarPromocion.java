package com.grupoms.app.presentacion.promocionJPA;

import javax.swing.*;

import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;

public class GUI_MostrarPromocion extends JFrame implements IGUI{
    public GUI_MostrarPromocion() {
        super("Mostrar Promoción");
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

        JLabel labelId = new JLabel("ID de la Promoción a mostrar:");
        JTextField campoId = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelId, gbc);
        gbc.gridx = 1;
        panel.add(campoId, gbc);

        // Botón Aceptar
        JButton aceptar = new JButton("Aceptar");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        aceptar.addActionListener(e -> MostrarPromocion(campoId.getText()));

        panel.add(aceptar, gbc);
        add(panel, BorderLayout.CENTER);
    }

    private void MostrarPromocion(String idText) {
        try {
            int id = Integer.parseInt(idText);
            TPromocion promocion = new TPromocion();
            promocion.setId(id);
            Context contexto = new Context(Evento.MOSTRAR_PROMOCION, promocion);
            Controlador.getInstance().handle(contexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, ingrese un número entero.");
        }
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            JOptionPane.showMessageDialog(this, "Error al mostrar la promoción.");
            return;
        } else if (context.getEvento() == Evento.MOSTRAR_PROMOCION_OK) {
            TPromocion promocion = (TPromocion) context.getDatos();

            if (promocion == null) {
                JOptionPane.showMessageDialog(this, "La promoción no existe.");
            } else {
                StringBuilder info = new StringBuilder();
                info.append("ID: ").append(promocion.getId()).append("\n");
                info.append("Tipo: ").append(promocion.getTipo()).append("\n");
                info.append("Descuento: ").append(promocion.getDescuento()).append("\n");
                info.append("Activo: ").append(promocion.getActivo() ? "Sí" : "No").append("\n");

                JOptionPane.showMessageDialog(this, info.toString(), "Detalles de la Promoción", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (context.getEvento() == Evento.MOSTRAR_PROMOCION_KO) {
            JOptionPane.showMessageDialog(this, "No se ha podido mostrar la promoción.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
