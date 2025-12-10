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

public class GUI_ModificarSala extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoId;
    private JTextField campoNombre;
    private JTextField campoCapacidad;
    
    private JButton btnModificar;

    public GUI_ModificarSala() {
        super("Modificar Sala");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack(); 
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panel = new JPanel(new GridBagLayout());

        JLabel labelId = new JLabel("ID Sala:");
        campoId = new JTextField(10);

        JLabel labelNombre = new JLabel("Nombre:");
        campoNombre = new JTextField(15);

        JLabel labelCapacidad = new JLabel("Capacidad:");
        campoCapacidad = new JTextField(10);

        btnModificar = new JButton("Modificar Sala");

        btnModificar.addActionListener(e -> {
            try {
                if(campoId.getText().isEmpty() || campoNombre.getText().isEmpty() || campoCapacidad.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos.");
                    return;
                }

                int idSala = Integer.parseInt(campoId.getText());
                String nombre = campoNombre.getText();
                int capacidad = Integer.parseInt(campoCapacidad.getText());

                if (capacidad <= 0) {
                     JOptionPane.showMessageDialog(this, "La capacidad debe ser mayor a 0");
                     return;
                }

                TSala sala = new TSala();
                sala.setId(idSala);
                sala.setNombre(nombre);
                sala.setCapacidad(capacidad);

                Context contexto = new Context(Evento.MODIFICAR_SALA, sala);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: ID y Capacidad deben ser números válidos");
            }
        });

        // Posicionamiento
        gbc.gridx = 0; gbc.gridy = 0; 
        panel.add(labelId, gbc);
        gbc.gridx = 1; 
        panel.add(campoId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; 
        panel.add(labelNombre, gbc);
        gbc.gridx = 1; 
        panel.add(campoNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; 
        panel.add(labelCapacidad, gbc);
        gbc.gridx = 1; 
        panel.add(campoCapacidad, gbc);

        gbc.gridx = 0; gbc.gridy = 3; 
        gbc.gridwidth = 2;
        panel.add(btnModificar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }
        
        if (context.getEvento() == Evento.MODIFICAR_SALA_OK) {
            JOptionPane.showMessageDialog(this, "Sala modificada con éxito");
            campoId.setText("");
            campoNombre.setText("");
            campoCapacidad.setText("");
            
        } else if (context.getEvento() == Evento.MODIFICAR_SALA_KO) {
            JOptionPane.showMessageDialog(this, "No se ha podido modificar la sala", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}