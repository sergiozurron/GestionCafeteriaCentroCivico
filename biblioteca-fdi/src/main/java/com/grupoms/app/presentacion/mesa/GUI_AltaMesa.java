package com.grupoms.app.presentacion.mesa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TSala;
import com.grupoms.app.negocio.mesa.TTerraza;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaMesa extends JFrame implements IGUI {

    private JTextField numero, ubicacion, capacidad;
    private JRadioButton rbtnSala, rbtnTerraza;
    private JPanel panelSala, panelTerraza;
    private JCheckBox salaReservada, terrazaCubierta;
    private JLabel lblPrivacidad, lblSuplemento;
    private JTextField salaPrivacidad, terrazaSuplemento;
    private JButton crear;

    public GUI_AltaMesa() {
        super("Alta Mesa");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.ALTA_MESA_OK) {
            JOptionPane.showMessageDialog(this, "Mesa creada con éxito");
            numero.setText("");
            ubicacion.setText("");
            capacidad.setText("");
            salaReservada.setSelected(false);
            salaPrivacidad.setText("");
            terrazaCubierta.setSelected(false);
            terrazaSuplemento.setText("");
            rbtnSala.setSelected(false);
            rbtnTerraza.setSelected(false);
            panelSala.setVisible(false);
            panelTerraza.setVisible(false);
        } else if (context.getEvento() == Evento.ALTA_MESA_KO) {
            JOptionPane.showMessageDialog(this, "Error al crear la mesa");
        }

    }

    private void initGUI() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelNumero = new JLabel("Número:");
        numero = new JTextField(10);
        JLabel labelUbicacion = new JLabel("Ubicación:");
        ubicacion = new JTextField(10);
        JLabel labelCapacidad = new JLabel("Capacidad:");
        capacidad = new JTextField(10);

        rbtnSala = new JRadioButton("Sala");
        rbtnTerraza = new JRadioButton("Terraza");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbtnSala);
        grupoTipo.add(rbtnTerraza);

        panelSala = new JPanel(new GridBagLayout());
        salaReservada = new JCheckBox("Reservada");
        lblPrivacidad = new JLabel("Nivel privacidad:");
        salaPrivacidad = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 0;
        panelSala.add(salaReservada, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panelSala.add(lblPrivacidad, gbc);
        gbc.gridx = 1;
        panelSala.add(salaPrivacidad, gbc);
        panelSala.setVisible(false);

        panelTerraza = new JPanel(new GridBagLayout());
        terrazaCubierta = new JCheckBox("Cubierta");
        lblSuplemento = new JLabel("Suplemento:");
        terrazaSuplemento = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 0;
        panelTerraza.add(terrazaCubierta, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panelTerraza.add(lblSuplemento, gbc);
        gbc.gridx = 1;
        panelTerraza.add(terrazaSuplemento, gbc);
        panelTerraza.setVisible(false);

        rbtnSala.addItemListener(e -> {
            panelSala.setVisible(e.getStateChange() == ItemEvent.SELECTED);
            panelTerraza.setVisible(false);
            pack();
        });

        rbtnTerraza.addItemListener(e -> {
            panelTerraza.setVisible(e.getStateChange() == ItemEvent.SELECTED);
            panelSala.setVisible(false);
            pack();
        });

        crear = new JButton("Crear Mesa");
        crear.addActionListener(e -> {
            try {
                TMesa mesa = new TMesa();
                mesa.setNumero(Integer.parseInt(numero.getText()));
                mesa.setUbicacion(ubicacion.getText());
                mesa.setCapacidad(Integer.parseInt(capacidad.getText()));
                mesa.setActivo(true);

                if (rbtnSala.isSelected()) {
                    TSala sala = new TSala();
                    sala.setReservada(salaReservada.isSelected());
                    sala.setPrivacidad(salaPrivacidad.getText());
                    mesa.setSala(sala);
                } else if (rbtnTerraza.isSelected()) {
                    TTerraza terraza = new TTerraza();
                    terraza.setCubierta(terrazaCubierta.isSelected());
                    terraza.setSuplemento(Double.parseDouble(terrazaSuplemento.getText()));
                    mesa.setTerraza(terraza);
                }

                Context contexto = new Context(Evento.ALTA_MESA, mesa);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
            }
        });

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelNumero, gbc);
        gbc.gridx = 1;
        panel.add(numero, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelUbicacion, gbc);
        gbc.gridx = 1;
        panel.add(ubicacion, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelCapacidad, gbc);
        gbc.gridx = 1;
        panel.add(capacidad, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(rbtnSala, gbc);
        gbc.gridx = 1;
        panel.add(rbtnTerraza, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(panelSala, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(panelTerraza, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(crear, gbc);

        add(panel, BorderLayout.CENTER);
    }
}
