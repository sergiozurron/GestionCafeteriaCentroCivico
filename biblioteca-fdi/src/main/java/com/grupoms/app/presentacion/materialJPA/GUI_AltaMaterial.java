package com.grupoms.app.presentacion.materialJPA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.grupoms.app.negocio.materialJPA.TLibro;
import com.grupoms.app.negocio.materialJPA.TMaterial;
import com.grupoms.app.negocio.materialJPA.TPintura;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaMaterial extends JFrame implements IGUI {

    private JTextField campoAutor, campoEditorial, campoIsbn, campoNumero, campoFecha;
    private JRadioButton libroButton, pinturaButton;
    private JButton aceptar;
    private JPanel panelLibro, panelPintura;

    public GUI_AltaMaterial() {
        super("Alta Material");
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

        // Campos comunes
        JLabel labelAutor = new JLabel("Autor:");
        campoAutor = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelAutor, gbc);
        gbc.gridx = 1;
        panel.add(campoAutor, gbc);

        // Radio buttons para tipo
        libroButton = new JRadioButton("Libro");
        pinturaButton = new JRadioButton("Pintura");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(libroButton);
        grupoTipo.add(pinturaButton);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(libroButton, gbc);
        gbc.gridx = 1;
        panel.add(pinturaButton, gbc);

        // Panel Libro
        panelLibro = new JPanel(new GridBagLayout());
        JLabel labelEditorial = new JLabel("Editorial:");
        JLabel labelIsbn = new JLabel("ISBN:");
        campoEditorial = new JTextField(15);
        campoIsbn = new JTextField(20);
        GridBagConstraints gbcLibro = new GridBagConstraints();
        gbcLibro.insets = new Insets(5,5,5,5);
        gbcLibro.gridx = 0; gbcLibro.gridy = 0;
        panelLibro.add(labelEditorial, gbcLibro);
        gbcLibro.gridx = 1;
        panelLibro.add(campoEditorial, gbcLibro);
        gbcLibro.gridx = 0; gbcLibro.gridy = 1;
        panelLibro.add(labelIsbn, gbcLibro);
        gbcLibro.gridx = 1;
        panelLibro.add(campoIsbn, gbcLibro);
        panelLibro.setVisible(false);

        // Panel Pintura
        panelPintura = new JPanel(new GridBagLayout());
        JLabel labelNumero = new JLabel("Número:");
        JLabel labelFecha = new JLabel("Fecha:");
        campoNumero = new JTextField(10);
        campoFecha = new JTextField(10);
        GridBagConstraints gbcPintura = new GridBagConstraints();
        gbcPintura.insets = new Insets(5,5,5,5);
        gbcPintura.gridx = 0; gbcPintura.gridy = 0;
        panelPintura.add(labelNumero, gbcPintura);
        gbcPintura.gridx = 1;
        panelPintura.add(campoNumero, gbcPintura);
        gbcPintura.gridx = 0; gbcPintura.gridy = 1;
        panelPintura.add(labelFecha, gbcPintura);
        gbcPintura.gridx = 1;
        panelPintura.add(campoFecha, gbcPintura);
        panelPintura.setVisible(false);

        // Listeners para mostrar panel correspondiente
        libroButton.addItemListener(e -> {
            panelLibro.setVisible(e.getStateChange() == ItemEvent.SELECTED);
            panelPintura.setVisible(false);
            pack();
        });

        pinturaButton.addItemListener(e -> {
            panelPintura.setVisible(e.getStateChange() == ItemEvent.SELECTED);
            panelLibro.setVisible(false);
            pack();
        });

        // Botón Aceptar
        aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> crearMaterial());

        // Ubicación de paneles específicos
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(panelLibro, gbc);
        gbc.gridy = 3;
        panel.add(panelPintura, gbc);
        gbc.gridy = 4;
        panel.add(aceptar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void crearMaterial() {
        try {
            String autor = campoAutor.getText();
            TMaterial material;

            if (libroButton.isSelected()) {
                String editorial = campoEditorial.getText();
                String isbn = campoIsbn.getText();
                material = new TLibro(autor, 1, isbn, editorial); // tipo 1 = Libro
            } else if (pinturaButton.isSelected()) {
                int numero = Integer.parseInt(campoNumero.getText());
                String fecha = campoFecha.getText();
                material = new TPintura(autor, 0, numero, fecha); // tipo 0 = Pintura
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de material");
                return;
            }

            Context contexto = new Context(Evento.ALTA_MATERIAL, material);
            Controlador.getInstance().handle(contexto);
            setVisible(false);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizar(Context context) {
    	if (context == null) {
            setVisible(true);
        }
    	switch(context.getEvento()) {
        case Evento.ALTA_MATERIAL_OK:
        case Evento.ALTA_LIBRO_OK:
        case Evento.ALTA_PINTURA_OK:
        	JOptionPane.showMessageDialog(this, "Material creado con éxito");
        	campoAutor.setText("");
            campoEditorial.setText("");
            campoIsbn.setText("");
            campoNumero.setText("");
            campoFecha.setText("");
            libroButton.setSelected(false);
            pinturaButton.setSelected(false);
            panelLibro.setVisible(false);
            panelPintura.setVisible(false);
            break;
        case Evento.ALTA_MATERIAL_KO:
        case Evento.ALTA_LIBRO_KO:
        case Evento.ALTA_PINTURA_KO:
            JOptionPane.showMessageDialog(this, "Error al añadir el material", "Error", JOptionPane.ERROR_MESSAGE);
            break;
        }
      
    }
}
