package com.grupoms.app.presentacion.claseJPA;

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

public class GUI_BajaClase extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoIdClase;
	private JButton btnBaja;

	public GUI_BajaClase() {
		setTitle("Baja Clase");
		setSize(350, 160);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelId = new JLabel("ID Clase:");
		campoIdClase = new JTextField(10);

		btnBaja = new JButton("Dar de baja");

		btnBaja.addActionListener(e -> {
			String idTexto = campoIdClase.getText().trim();

			if (idTexto.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El ID de la clase es obligatorio");
				return;
			}

			Integer idClase;
			try {
				idClase = Integer.parseInt(idTexto);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID de la clase debe ser un número válido");
				return;
			}

			Context contexto = new Context(Evento.BAJA_CLASE, idClase);
			Controlador.getInstance().handle(contexto);
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoIdClase, gbc);

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
		case Evento.BAJA_CLASE_OK:
			JOptionPane.showMessageDialog(this, "Clase dada de baja correctamente");
			break;
		case Evento.BAJA_CLASE_KO:
			JOptionPane.showMessageDialog(this, "Error al dar de baja la clase", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}

}
