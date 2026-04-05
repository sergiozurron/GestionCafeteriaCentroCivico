package com.grupoms.app.presentacion.prestamoJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

	private JTextField campoPlazo;
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

		JLabel labelPlazo = new JLabel("Plazo (días):");
		campoPlazo = new JTextField(15);

		JLabel labelIdSocio = new JLabel("Id Socio:");
		campoIdSocio = new JTextField(15);

		JLabel labelIdEjemplar = new JLabel("Id Ejemplar:");
		campoIdEjemplar = new JTextField(15);

		btnCrear = new JButton("Crear Préstamo");

		btnCrear.addActionListener(e -> {
			String plazoString = campoPlazo.getText().trim();
			String idSocioString = campoIdSocio.getText().trim();
			String idEjemplarString = campoIdEjemplar.getText().trim();

			if (plazoString.isEmpty()) {
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
			
			Date fechaDevolucion;
			try {
				int plazo = Integer.parseInt(plazoString);
				if (plazo <= 0) {
					JOptionPane.showMessageDialog(this, "El plazo debe ser un número positivo");
					return;
				}
				fechaDevolucion = new Date(System.currentTimeMillis() + (long) plazo * 24 * 60 * 60 * 1000);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El plazo debe ser un número válido");
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

			TPrestamo prestamo = new TPrestamo();
			prestamo.setFechaMaxima(fechaDevolucion);
			prestamo.setIdSocio(idSocio);
			prestamo.setIdEjemplar(idEjemplar);

			Context contexto = new Context(Evento.ALTA_PRESTAMO, prestamo);
			Controlador.getInstance().handle(contexto);
		});

		int y = 0;
		
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelPlazo, gbc);
		gbc.gridx = 1;
		panel.add(campoPlazo, gbc);

		y++;
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
