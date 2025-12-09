package com.grupoms.app.presentacion.prestamoJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.prestamoJPA.TPrestamo;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaPrestamo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoPrecioMulta;
	private JTextField campoFechaDevolucion;
	private JTextField campoIdEjemplar;
	private JTextField campoIdSocio;
	private JButton btnCrear;

	public GUI_AltaPrestamo() {
		setTitle("Alta Préstamo");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelPrecioMulta = new JLabel("Precio Multa:");
		campoPrecioMulta = new JTextField(15);

		JLabel labelFechaDevolucion = new JLabel("Fecha Devolución (yyyy-MM-dd):");
		campoFechaDevolucion = new JTextField(15);

		JLabel labelIdSocio = new JLabel("Id Socio:");
		campoIdSocio = new JTextField(15);

		JLabel labelIdEjemplar = new JLabel("Id Ejemplar:");
		campoIdEjemplar = new JTextField(15);

		btnCrear = new JButton("Crear Préstamo");

		btnCrear.addActionListener(_ -> {
			String precioMultaString = campoPrecioMulta.getText().trim();
			String fechaDevolucionString = campoFechaDevolucion.getText().trim();
			String idSocioString = campoIdSocio.getText().trim();
			String idEjemplarString = campoIdEjemplar.getText().trim();

			if (precioMultaString.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Precio multa es obligatorio");
				return;
			}

			if (fechaDevolucionString.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Fecha de devolución es obligatoria");
				return;
			}

			if (idSocioString.isEmpty()) {
				JOptionPane.showMessageDialog(this, "ID del Socio es obligatorio");
				return;
			}

			if (idEjemplarString.isEmpty()) {
				JOptionPane.showMessageDialog(this, "ID del Ejemplar es obligatorio");
				return;
			}

			Double precioMulta;
			try {
				precioMulta = Double.parseDouble(precioMultaString);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El precio de la multa debe ser un número válido");
				return;
			}

			Date fechaDevolucion;
			try {
				fechaDevolucion = new SimpleDateFormat("yyyy-MM-dd").parse(fechaDevolucionString);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Formato de fecha de devolución inválido. Use yyyy-MM-dd");
				return;
			}

			Integer idSocio;
			try {
				idSocio = Integer.parseInt(campoIdSocio.getText().trim());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID del Socio debe ser un número válido");
				return;
			}

			Integer idEjemplar;
			try {
				idEjemplar = Integer.parseInt(campoIdEjemplar.getText().trim());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID del Ejemplar debe ser un número válido");
				return;
			}

			// Crear TPrestamo
			TPrestamo prestamo = new TPrestamo();
			prestamo.setPrecioMulta(precioMulta);
			prestamo.setFechaMaxima(fechaDevolucion);
			prestamo.setIdSocio(idSocio);
			prestamo.setIdEjemplar(idEjemplar);

			// Enviar al controlador
			Context contexto = new Context(Evento.ALTA_PRESTAMO, prestamo);
			Controlador.getInstance().handle(contexto);
		});

		// Colocación
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelPrecioMulta, gbc);
		gbc.gridx = 1;
		panel.add(campoPrecioMulta, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelFechaDevolucion, gbc);
		gbc.gridx = 1;
		panel.add(campoFechaDevolucion, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelIdSocio, gbc);
		gbc.gridx = 1;
		panel.add(campoIdSocio, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(labelIdEjemplar, gbc);
		gbc.gridx = 1;
		panel.add(campoIdEjemplar, gbc);

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
		case Evento.ALTA_PRESTAMO_OK:
			Integer idPrestamo = (Integer) context.getDatos();
			JOptionPane.showMessageDialog(this, "Préstamo creado con id " + idPrestamo);
			setVisible(false);
			dispose();
			break;
		case Evento.ALTA_PRESTAMO_KO:
			JOptionPane.showMessageDialog(this, "Error al crear el préstamo");
			break;
		}
	}

}
