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

public class GUI_AltaSala extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoNombre;
    private JTextField campoCapacidad;
    // JCheckBox eliminado
    private JButton btnCrear;

    public GUI_AltaSala() {
        super("Alta Sala");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelNombre = new JLabel("Nombre:");
        campoNombre = new JTextField(20);

        JLabel labelCapacidad = new JLabel("Capacidad:");
        campoCapacidad = new JTextField(20);

        btnCrear = new JButton("Crear Sala");
        btnCrear.addActionListener(e -> crearSala());

        // Colocación de componentes
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelNombre, gbc);
        gbc.gridx = 1;
        panel.add(campoNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(labelCapacidad, gbc);
        gbc.gridx = 1;
        panel.add(campoCapacidad, gbc);

        // El checkbox estaba en gridy=2, ahora ponemos el botón ahí
        gbc.gridx = 0; 
        gbc.gridy = 2; 
        gbc.gridwidth = 2; // Ocupa todo el ancho
        panel.add(btnCrear, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void crearSala() {
        try {
            String nombre = campoNombre.getText().trim();
            
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
                return;
            }

            int capacidad = Integer.parseInt(campoCapacidad.getText().trim());
            
            // Validación extra para lógica de negocio básica
            if (capacidad <= 0) {
                 JOptionPane.showMessageDialog(this, "La capacidad debe ser mayor a 0");
                 return;
            }

            TSala sala = new TSala();
            sala.setNombre(nombre);
            sala.setCapacidad(capacidad);
            sala.setActivo(true); // Siempre activa por defecto

            Context contexto = new Context(Evento.ALTA_SALA, sala);
            Controlador.getInstance().handle(contexto);
            setVisible(false);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un número entero válido", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }
        switch (context.getEvento()) {
        case Evento.ALTA_SALA_OK:
            JOptionPane.showMessageDialog(this, "Sala creada con éxito. ID: " + context.getDatos());
            // Limpiamos los campos
            campoNombre.setText("");
            campoCapacidad.setText("");
            break;
        case Evento.ALTA_SALA_KO:
            JOptionPane.showMessageDialog(this, "Error al crear la sala", "Error", JOptionPane.ERROR_MESSAGE);
            break;
        }
    }
}