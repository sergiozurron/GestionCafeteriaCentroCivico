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

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_BajaSala extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JButton btnBaja;

	public GUI_BajaSala() {
		super("Baja Sala");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelId = new JLabel("ID Sala:");
		campoId = new JTextField(15);

		btnBaja = new JButton("Dar de Baja");
		btnBaja.addActionListener(e -> {
			try {
				Integer id = Integer.parseInt(campoId.getText().trim());

				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0");
					return;
				}

				Context contexto = new Context(Evento.BAJA_SALA, id);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: El ID debe ser un número válido");
			}
		});

		// Colocación
		gbc.gridx = 0; gbc.gridy = 0;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoId, gbc);

		gbc.gridx = 0; gbc.gridy = 1;
		gbc.gridwidth = 2;
		panel.add(btnBaja, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.BAJA_SALA_OK:
			JOptionPane.showMessageDialog(this, "Sala dada de baja correctamente");
			campoId.setText(""); // Limpiamos el campo al terminar con éxito
			break;
		case Evento.BAJA_SALA_KO:
			JOptionPane.showMessageDialog(this, "Error al dar de baja la sala", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}