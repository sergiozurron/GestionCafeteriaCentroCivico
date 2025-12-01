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

public class GUI_DesvincularClaseSala extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoIdSala;
	private JTextField campoIdClase;
	private JButton btnDesvincular;

	public GUI_DesvincularClaseSala() {
		setTitle("Desvincular Clase de Sala");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelIdSala = new JLabel("ID Sala:");
		campoIdSala = new JTextField(10);

		JLabel labelIdClase = new JLabel("ID Clase:");
		campoIdClase = new JTextField(10);

		btnDesvincular = new JButton("Desvincular");

		btnDesvincular.addActionListener(e -> {
			String idSalaTexto = campoIdSala.getText().trim();
			String idClaseTexto = campoIdClase.getText().trim();

			if (idSalaTexto.isEmpty() || idClaseTexto.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Ambos IDs son obligatorios");
				return;
			}

			Integer idSala;
			Integer idClase;
			try {
				idSala = Integer.parseInt(idSalaTexto);
				idClase = Integer.parseInt(idClaseTexto);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Los IDs deben ser números válidos");
				return;
			}

			Integer[] params = new Integer[] { idSala, idClase };

			Context contexto = new Context(Evento.DESVINCULAR_CLASE_SALA, params);
			Controlador.getInstance().handle(contexto);
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelIdSala, gbc);
		gbc.gridx = 1;
		panel.add(campoIdSala, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelIdClase, gbc);
		gbc.gridx = 1;
		panel.add(campoIdClase, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(btnDesvincular, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.DESVINCULAR_CLASE_SALA_OK:
			JOptionPane.showMessageDialog(this, "Clase desvinculada correctamente de la sala");
			break;
		case Evento.DESVINCULAR_CLASE_SALA_KO:
			JOptionPane.showMessageDialog(this, "Error al desvincular la clase de la sala", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}