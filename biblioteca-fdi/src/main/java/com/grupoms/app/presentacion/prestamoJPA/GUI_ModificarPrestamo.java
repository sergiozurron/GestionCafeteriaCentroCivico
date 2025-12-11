package com.grupoms.app.presentacion.prestamoJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import com.grupoms.app.negocio.prestamoJPA.TPrestamo;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarPrestamo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JTextField campoIdEjemplar;
	private JTextField campoIdSocio;
	private JTextField campoFechaInicial;
	private JTextField campoFechaMaxima;
	private JTextField campoFechaDevuelto;
	private JTextField campoPrecioMulta;
	private JTextField campoActivo;

	private JButton btnModificar;

	public GUI_ModificarPrestamo() {
		setTitle("Modificar Préstamo");
		setSize(450, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		campoId = new JTextField(15);
		campoIdEjemplar = new JTextField(15);
		campoIdSocio = new JTextField(15);
		campoFechaInicial = new JTextField(15);
		campoFechaMaxima = new JTextField(15);
		campoFechaDevuelto = new JTextField(15);
		campoPrecioMulta = new JTextField(15);
		campoActivo = new JTextField(15);

		btnModificar = new JButton("Modificar Préstamo");

		btnModificar.addActionListener(e -> {

			try {

				int id = Integer.parseInt(campoId.getText().trim());
				int idEjemplar = Integer.parseInt(campoIdEjemplar.getText().trim());
				int idSocio = Integer.parseInt(campoIdSocio.getText().trim());
				double precioMulta = Double.parseDouble(campoPrecioMulta.getText().trim());
				boolean activo = Boolean.parseBoolean(campoActivo.getText().trim());

				String fechaInicialString = campoFechaInicial.getText().trim();
				String fechaMaxString = campoFechaMaxima.getText().trim();
				String fechaDevueltoString = campoFechaDevuelto.getText().trim();

				if (fechaInicialString.isEmpty() || fechaMaxString.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Las fechas inicial y máxima son obligatorias");
					return;
				}

				Date fechaInicial = null, fechaMax = null, fechaDevuelto = null;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
					fechaInicial = formato.parse(fechaInicialString);
					fechaMax = formato.parse(fechaMaxString);
					fechaDevuelto = formato.parse(fechaDevueltoString);
				} catch (ParseException pe) {
					JOptionPane.showMessageDialog(this, "Formato de fecha inválido");
				}

				TPrestamo prestamo = new TPrestamo();
				prestamo.setId(id);
				prestamo.setIdEjemplar(idEjemplar);
				prestamo.setIdSocio(idSocio);
				prestamo.setFechaInicial(fechaInicial);
				prestamo.setFechaMaxima(fechaMax);
				prestamo.setFechaDevuelto(fechaDevuelto);
				prestamo.setPrecioMulta(precioMulta);
				prestamo.setActivo(activo);

				Context contexto = new Context(Evento.MODIFICAR_PRESTAMO, prestamo);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Revisa que los campos numéricos sean válidos");
			}
		});

		int y = 0;

		addField(panel, gbc, y++, "ID:", campoId);
		addField(panel, gbc, y++, "ID Ejemplar:", campoIdEjemplar);
		addField(panel, gbc, y++, "ID Socio:", campoIdSocio);
		addField(panel, gbc, y++, "Fecha Inicial:", campoFechaInicial);
		addField(panel, gbc, y++, "Fecha Máxima:", campoFechaMaxima);
		addField(panel, gbc, y++, "Fecha Devuelto:", campoFechaDevuelto);
		addField(panel, gbc, y++, "Precio Multa:", campoPrecioMulta);
		addField(panel, gbc, y++, "Activo (true/false):", campoActivo);

		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(btnModificar, gbc);

		add(panel, BorderLayout.CENTER);
	}

	private void addField(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField campo) {
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel(label), gbc);

		gbc.gridx = 1;
		panel.add(campo, gbc);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}

		switch (context.getEvento()) {
		case Evento.MODIFICAR_PRESTAMO_OK:
			JOptionPane.showMessageDialog(this, "Préstamo modificado con éxito");
			setVisible(false);
			break;

		case Evento.MODIFICAR_PRESTAMO_KO:
			JOptionPane.showMessageDialog(this, "Error al modificar préstamo", "Error", JOptionPane.ERROR_MESSAGE);
			break;

		default:
			break;
		}
	}
}
