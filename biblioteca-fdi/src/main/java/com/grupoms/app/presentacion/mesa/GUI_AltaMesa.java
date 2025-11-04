package com.grupoms.app.presentacion.mesa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TSala;
import com.grupoms.app.negocio.mesa.TTerraza;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaMesa extends JFrame implements IGUI {
    
	private JTextField numero;
    private JTextField ubicacion;
    private JTextField capacidad;

    private JRadioButton rbtnSala;
    private JRadioButton rbtnTerraza;
    private JPanel panelSala;
    private JPanel panelTerraza;

    // Campos Sala
    private JCheckBox salaReservada;
    private JTextField salaPrivacidad;

    // Campos Terraza
    private JCheckBox terrazaCubierta;
    private JTextField terrazaSuplemento;
    private JButton crear;

    public GUI_AltaMesa(){
       super("Alta Mesa");
       initGUI(); //iniciamos el front por asi ddecirlo
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //destruye la ventana sin cerrar la app
       pack(); //ajusta
       setLocationRelativeTo(null); //centra
       setVisible(true); //es visible
    }
    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.ALTA_MESA) {
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
        }
    }
    
    public void initGUI(){
        setLayout(new BorderLayout()); //layout general

        JPanel panel = new JPanel(new GridBagLayout()); //panel principal
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //CAMPOS COMUNES

        JLabel labelNumero = new JLabel("Número:");
        numero = new JTextField(10);

        JLabel labelUbicacion = new JLabel("Ubicación:");
        ubicacion = new JTextField(10);

        JLabel labelCapacidad = new JLabel("Capacidad:");
        capacidad = new JTextField(10);

        //TIPO DE MESA
        rbtnSala = new JRadioButton("Sala");
        rbtnTerraza = new JRadioButton("Terraza");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbtnSala);
        grupoTipo.add(rbtnTerraza);


        //SALA
        panelSala = new JPanel(new GridBagLayout());
        salaReservada = new JCheckBox("Reservada");
        salaPrivacidad = new JTextField(10);

        gbc.gridx = 0; gbc.gridy = 0;
        panelSala.add(salaReservada, gbc);
        gbc.gridx = 1;
        panelSala.add(salaPrivacidad, gbc);

        panelSala.setVisible(false);
        
        rbtnSala.addItemListener(e -> {
            panelSala.setVisible(e.getStateChange() == ItemEvent.SELECTED);
            panelTerraza.setVisible(false);
            pack();
        });

        //TERRAZA

        panelTerraza = new JPanel(new GridBagLayout());
        terrazaCubierta = new JCheckBox("Cubierta");
        terrazaSuplemento = new JTextField(10);

        gbc.gridx = 0; gbc.gridy = 0;
        panelTerraza.add(terrazaCubierta, gbc);
        gbc.gridx = 1;
        panelTerraza.add(terrazaSuplemento, gbc);

        panelTerraza.setVisible(false);
        rbtnTerraza.addItemListener(e -> {
            panelTerraza.setVisible(e.getStateChange() == ItemEvent.SELECTED);
            panelSala.setVisible(false);
            pack();
        });

        crear = new JButton("Crear Pedido");
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
