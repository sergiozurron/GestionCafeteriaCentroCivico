package com.grupoms.app.presentacion.salaJPA;

import javax.swing.*;
import java.awt.*;
import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarSala extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    public GUI_MostrarSala() {
        super("Mostrar Sala");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelId = new JLabel("ID de la Sala a mostrar:");
        JTextField campoId = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelId, gbc);
        gbc.gridx = 1;
        panel.add(campoId, gbc);

        JButton aceptar = new JButton("Aceptar");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        aceptar.addActionListener(e -> mostrarSala(campoId.getText()));

        panel.add(aceptar, gbc);
        add(panel, BorderLayout.CENTER);
    }

    private void mostrarSala(String idText) {
        try {
            int id = Integer.parseInt(idText);
            // Normalmente para mostrar solo enviamos el ID en el contexto, 
            // pero si tu controlador espera un Transfer, lo envolvemos.
            Context contexto = new Context(Evento.MOSTRAR_SALA, id);
            Controlador.getInstance().handle(contexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, ingrese un número entero.");
        }
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            JOptionPane.showMessageDialog(this, "Error al mostrar la sala.");
            return;
        } else if (context.getEvento() == Evento.MOSTRAR_SALA_OK) {
            TSala sala = (TSala) context.getDatos();

            if (sala == null) {
                JOptionPane.showMessageDialog(this, "La sala no existe.");
            } else {
                StringBuilder info = new StringBuilder();
                info.append("ID: ").append(sala.getId()).append("\n");
                info.append("Nombre: ").append(sala.getNombre()).append("\n");
                info.append("Capacidad: ").append(sala.getCapacidad()).append("\n");
                info.append("Activo: ").append(
                        (sala.getActivo() != null && sala.getActivo()) ? "Sí" : "No"
                ).append("\n");

                JOptionPane.showMessageDialog(this, info.toString(),
                        "Detalles de la Sala", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (context.getEvento() == Evento.MOSTRAR_SALA_KO) {
            JOptionPane.showMessageDialog(this, "No se ha podido mostrar la sala.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}