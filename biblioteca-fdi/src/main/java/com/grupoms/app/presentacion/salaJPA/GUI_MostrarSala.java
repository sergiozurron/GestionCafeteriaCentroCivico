package com.grupoms.app.presentacion.salaJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarSala extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoID;
    private JButton mostrar;

    private JLabel nombreLabel;
    private JLabel capacidadLabel;

    public GUI_MostrarSala() {
        super("Mostrar Sala");
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

        JLabel labelID = new JLabel("ID Sala:");
        campoID = new JTextField(10);

        mostrar = new JButton("Mostrar Sala");
        mostrar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoID.getText().trim());
                Context contexto = new Context(Evento.MOSTRAR_SALA, id);
                Controlador.getInstance().handle(contexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
            }
        });

        nombreLabel = new JLabel();
        capacidadLabel = new JLabel();

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
        // Fila 3: Capacidad
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1;
        panel.add(capacidadLabel, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }

        switch (context.getEvento()) {
        case Evento.MOSTRAR_SALA_OK:
            TSala s = (TSala) context.getDatos();
            if (s != null) {
                nombreLabel.setText(s.getNombre());
                capacidadLabel.setText(String.valueOf(s.getCapacidad()));
            }
            break;
        case Evento.MOSTRAR_SALA_KO:
            JOptionPane.showMessageDialog(this, "Sala no encontrada en la base de datos");
            nombreLabel.setText("");
            capacidadLabel.setText("");
            break;
        }
    }
}