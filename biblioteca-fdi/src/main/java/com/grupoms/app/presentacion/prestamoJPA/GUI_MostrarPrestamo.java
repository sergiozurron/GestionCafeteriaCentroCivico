package com.grupoms.app.presentacion.prestamoJPA;

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

import com.grupoms.app.negocio.prestamoJPA.PrestamoId;
import com.grupoms.app.negocio.prestamoJPA.TPrestamo;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarPrestamo extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoIdSocio;
    private JTextField campoIdEjemplar;
    private JTextField campoFechaInicio;
    private JButton btnMostrar;

    private JTextField campoValorId;
    private JTextField campoValorIdEjemplar;
    private JTextField campoValorIdSocio;
    private JTextField campoValorFechaInicial;
    private JTextField campoValorFechaMaxima;
    private JTextField campoValorFechaDevuelto;
    private JTextField campoValorPrecioMulta;
    private JTextField campoValorActivo;

    public GUI_MostrarPrestamo() {
        super("Mostrar Préstamo");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Allow horizontal expansion

        JLabel labelIdSocio = new JLabel("ID Socio:");
        campoIdSocio = new JTextField(20);
        
        JLabel labelIdEjemplar = new JLabel("ID Ejemplar:");
        campoIdEjemplar = new JTextField(20);
        
        JLabel labelFechaInicio = new JLabel("Fecha Inicio (YYYY-MM-DD):");
        campoFechaInicio = new JTextField(20);

        btnMostrar = new JButton("Mostrar Préstamo");
        btnMostrar.addActionListener(e -> {
        	int idSocio;
			try {
				idSocio = Integer.parseInt(campoIdSocio.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID Socio debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int idEjemplar;
			try {
				idEjemplar = Integer.parseInt(campoIdEjemplar.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID Ejemplar debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Date fechaInicio;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				fechaInicio = sdf.parse(campoFechaInicio.getText());
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this, "Fecha Inicio debe tener formato YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

            Context contexto = new Context(Evento.MOSTRAR_PRESTAMO, new PrestamoId(idSocio, idEjemplar, fechaInicio));
            Controlador.getInstance().handle(contexto);

        });

        // Campos de visualización
        campoValorId = new JTextField(20);
        campoValorId.setEditable(false);
        JLabel labelIdValor = new JLabel("ID:");

        campoValorIdEjemplar = new JTextField(20);
        campoValorIdEjemplar.setEditable(false);
        JLabel labelIdEjemplarValor = new JLabel("ID Ejemplar:");

        campoValorIdSocio = new JTextField(20);
        campoValorIdSocio.setEditable(false);
        JLabel labelIdSocioValor = new JLabel("ID Socio:");

        campoValorFechaInicial = new JTextField(20);
        campoValorFechaInicial.setEditable(false);
        JLabel labelFechaInicialValor = new JLabel("Fecha Inicial:");

        campoValorFechaMaxima = new JTextField(20);
        campoValorFechaMaxima.setEditable(false);
        JLabel labelFechaMaximaValor = new JLabel("Fecha Máxima:");

        campoValorFechaDevuelto = new JTextField(20);
        campoValorFechaDevuelto.setEditable(false);
        JLabel labelFechaDevueltoValor = new JLabel("Fecha Devuelto:");

        campoValorPrecioMulta = new JTextField(20);
        campoValorPrecioMulta.setEditable(false);
        JLabel labelPrecioMultaValor = new JLabel("Precio Multa:");

        int y = 0;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelIdSocio, gbc);
        gbc.gridx = 1;
        panel.add(campoIdSocio, gbc);
        
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelIdEjemplar, gbc);
        gbc.gridx = 1;
        panel.add(campoIdEjemplar, gbc);
        
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelFechaInicio, gbc);
        gbc.gridx = 1;
        panel.add(campoFechaInicio, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        panel.add(btnMostrar, gbc);

        y++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelIdValor, gbc);
        gbc.gridx = 1;
        panel.add(campoValorId, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelIdEjemplarValor, gbc);
        gbc.gridx = 1;
        panel.add(campoValorIdEjemplar, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelIdSocioValor, gbc);
        gbc.gridx = 1;
        panel.add(campoValorIdSocio, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelFechaInicialValor, gbc);
        gbc.gridx = 1;
        panel.add(campoValorFechaInicial, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelFechaMaximaValor, gbc);
        gbc.gridx = 1;
        panel.add(campoValorFechaMaxima, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelFechaDevueltoValor, gbc);
        gbc.gridx = 1;
        panel.add(campoValorFechaDevuelto, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(labelPrecioMultaValor, gbc);
        gbc.gridx = 1;
        panel.add(campoValorPrecioMulta, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        } else if (context.getEvento() == Evento.MOSTRAR_PRESTAMO_OK) {
            TPrestamo prestamo = (TPrestamo) context.getDatos();
            if (prestamo == null) {
                // proteccion adicional: si por algun motivo llega null
                JOptionPane.showMessageDialog(this, "No se ha encontrado el préstamo.");
                clearFields();
                return;
            }

            campoValorIdEjemplar.setText(String.valueOf(prestamo.getIdEjemplar()));
            campoValorIdSocio.setText(String.valueOf(prestamo.getIdSocio()));
            campoValorFechaInicial.setText(String.valueOf(prestamo.getFechaInicial()));
            campoValorFechaMaxima.setText(String.valueOf(prestamo.getFechaMaxima()));
            campoValorFechaDevuelto.setText(String.valueOf(prestamo.getFechaDevuelto()));
            campoValorPrecioMulta.setText(String.valueOf(prestamo.getPrecioMulta()));

        } else if (context.getEvento() == Evento.MOSTRAR_PRESTAMO_KO) {
            JOptionPane.showMessageDialog(this, "Error al mostrar el préstamo. Puede que no exista o haya sido eliminado.");
            clearFields();
        }
    }

    private void clearFields() {
        campoValorId.setText("");
        campoValorIdEjemplar.setText("");
        campoValorIdSocio.setText("");
        campoValorFechaInicial.setText("");
        campoValorFechaMaxima.setText("");
        campoValorFechaDevuelto.setText("");
        campoValorPrecioMulta.setText("");
        campoValorActivo.setText("");
    }

}
