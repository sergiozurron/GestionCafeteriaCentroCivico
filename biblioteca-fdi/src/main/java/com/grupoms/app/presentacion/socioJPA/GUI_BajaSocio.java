package com.grupoms.app.presentacion.socioJPA;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import javax.swing.*;
import java.awt.*;

public class GUI_BajaSocio extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JButton btnBaja;

	public GUI_BajaSocio() {
		super("Baja Socio");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelId = new JLabel("ID Socio: ");
		campoId = new JTextField(15);

		btnBaja = new JButton("Dar de Baja");
		btnBaja.addActionListener(e -> {
			try {
				Integer id = Integer.parseInt(campoId.getText().trim());

				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0");
					return;
				}

				Context contexto = new Context(Evento.BAJA_SOCIO, id);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: El ID debe ser un número válido");
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoId, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
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
		case Evento.BAJA_SOCIO_OK:
			JOptionPane.showMessageDialog(this, "Socio dado de baja");
			campoId.setText("");
			break;
		case Evento.BAJA_SOCIO_KO:
			JOptionPane.showMessageDialog(this, "Error al dar de baja el socio", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}
