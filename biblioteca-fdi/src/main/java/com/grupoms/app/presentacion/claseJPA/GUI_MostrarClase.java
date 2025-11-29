package com.grupoms.app.presentacion.claseJPA;

import javax.swing.*;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.text.SimpleDateFormat;

public class GUI_MostrarClase extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    public GUI_MostrarClase() {
        super("Mostrar Clase");
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
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelId = new JLabel("ID de la Clase a mostrar:");
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
        aceptar.addActionListener(e -> MostrarClase(campoId.getText()));

        panel.add(aceptar, gbc);
        add(panel, BorderLayout.CENTER);
    }

    private void MostrarClase(String idText) {
        try {
            int id = Integer.parseInt(idText);
            TClase clase = new TClase();
            clase.setId(id);
            Context contexto = new Context(Evento.MOSTRAR_CLASE, clase);
            Controlador.getInstance().handle(contexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, ingrese un número entero.");
        }
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            JOptionPane.showMessageDialog(this, "Error al mostrar la clase.");
            return;
        } else if (context.getEvento() == Evento.MOSTRAR_CLASE_OK) {
            TClase clase = (TClase) context.getDatos();

            if (clase == null) {
                JOptionPane.showMessageDialog(this, "La clase no existe.");
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                StringBuilder info = new StringBuilder();
                info.append("ID: ").append(clase.getId()).append("\n");
                info.append("Tipo: ").append(clase.getTipo()).append("\n");
                info.append("Fecha inicio: ")
                        .append(clase.getFechaInicio() != null ? sdf.format(clase.getFechaInicio()) : "N/A")
                        .append("\n");
                info.append("Duración (min): ").append(clase.getDuracion()).append("\n");
                info.append("Activo: ").append(
                        (clase.getActivo() != null && clase.getActivo()) ? "Sí" : "No"
                ).append("\n");

                JOptionPane.showMessageDialog(this, info.toString(),
                        "Detalles de la Clase", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (context.getEvento() == Evento.MOSTRAR_CLASE_KO) {
            JOptionPane.showMessageDialog(this, "No se ha podido mostrar la clase.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
