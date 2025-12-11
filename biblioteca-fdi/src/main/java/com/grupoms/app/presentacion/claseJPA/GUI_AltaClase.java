package com.grupoms.app.presentacion.claseJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaClase extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoTipo;
    private JTextField campoFechaInicio;
    private JTextField campoDuracion;
    private JTextField campoIdSala;
    // Eliminado JCheckBox checkActivo
    private JButton btnCrear;

    public GUI_AltaClase() {
        setTitle("Alta Clase");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        initGUI();
    }

    private void initGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelTipo = new JLabel("Tipo:");
        campoTipo = new JTextField(15);

        JLabel labelFechaInicio = new JLabel("Fecha inicio (yyyy-MM-dd HH:mm):");
        campoFechaInicio = new JTextField(15);

        JLabel labelDuracion = new JLabel("Duración (minutos):");
        campoDuracion = new JTextField(15);

        JLabel labelIdSala = new JLabel("ID Sala:");
        campoIdSala = new JTextField(15);

        btnCrear = new JButton("Crear Clase");

        btnCrear.addActionListener(e -> {
            String tipo = campoTipo.getText().trim();
            String fechaTexto = campoFechaInicio.getText().trim();
            String duracionTexto = campoDuracion.getText().trim();
            String idSalaTexto = campoIdSala.getText().trim();

            // ---- Validaciones básicas ----
            if (tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El tipo es obligatorio");
                return;
            }
            if (fechaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La fecha de inicio es obligatoria");
                return;
            }
            if (duracionTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La duración es obligatoria");
                return;
            }
            if (idSalaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La ID de la sala es obligatoria");
                return;
            }

            Integer duracion;
            try {
                duracion = Integer.parseInt(duracionTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La duración debe ser un número válido");
                return;
            }

            Integer idSala;
            try {
                idSala = Integer.parseInt(idSalaTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La ID de sala debe ser un número válido");
                return;
            }

            Date fechaInicio;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                sdf.setLenient(false);
                fechaInicio = sdf.parse(fechaTexto);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Formato de fecha incorrecto. Use: yyyy-MM-dd HH:mm");
                return;
            }

            // ---- Construcción del transfer ----
            TClase clase = new TClase();
            clase.setTipo(tipo);
            clase.setFechaInicio(fechaInicio);
            clase.setDuracion(duracion);
            clase.setActivo(true); // Siempre activa por defecto
            clase.setIdSala(idSala); // Se asigna el ID de la sala introducido
            
            Context contexto = new Context(Evento.ALTA_CLASE, clase);
            Controlador.getInstance().handle(contexto);
            setVisible(false); // Cierra la ventana tras pulsar aceptar
        });

        // ---- Layout ----
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelTipo, gbc);
        gbc.gridx = 1;
        panel.add(campoTipo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(labelFechaInicio, gbc);
        gbc.gridx = 1;
        panel.add(campoFechaInicio, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(labelDuracion, gbc);
        gbc.gridx = 1;
        panel.add(campoDuracion, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(labelIdSala, gbc);
        gbc.gridx = 1;
        panel.add(campoIdSala, gbc);

        // Botón subido a la posición 4 (donde antes estaba el checkbox)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(btnCrear, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }
        switch (context.getEvento()) {
        case Evento.ALTA_CLASE_OK:
            int idClase = (int) context.getDatos();
            JOptionPane.showMessageDialog(this, "Clase creada con ID: " + idClase);
            // Limpiar campos
            campoTipo.setText("");
            campoFechaInicio.setText("");
            campoDuracion.setText("");
            campoIdSala.setText("");
            break;
        case Evento.ALTA_CLASE_KO:
            JOptionPane.showMessageDialog(this, "Error al crear la clase", "Error", JOptionPane.ERROR_MESSAGE);
            break;
        default:
            break;
        }
    }
}