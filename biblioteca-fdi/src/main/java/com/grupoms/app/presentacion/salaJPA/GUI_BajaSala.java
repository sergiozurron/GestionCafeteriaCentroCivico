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

	private JTextField campoIdSala;
	private JButton btnBaja;

	public GUI_BajaSala() {
		setTitle("Baja Sala");
		setSize(350, 160);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelId = new JLabel("ID Sala:");
		campoIdSala = new JTextField(10);

		btnBaja = new JButton("Dar de baja");

		btnBaja.addActionListener(e -> {
			String idTexto = campoIdSala.getText().trim();

			if (idTexto.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El ID de la sala es obligatorio");
				return;
			}

			Integer idSala;
			try {
				idSala = Integer.parseInt(idTexto);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID de la sala debe ser un número válido");
				return;
			}

			Context contexto = new Context(Evento.BAJA_SALA, idSala);
			Controlador.getInstance().handle(contexto);
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoIdSala, gbc);

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
		case Evento.BAJA_SALA_OK:
			JOptionPane.showMessageDialog(this, "Sala dada de baja correctamente");
			break;
		case Evento.BAJA_SALA_KO:
            // Manejamos el error específico de clases asignadas (-2) si devuelve Integer
            if (context.getDatos() instanceof Integer && (Integer)context.getDatos() == -2) {
                JOptionPane.showMessageDialog(this, "No se puede dar de baja: La sala tiene clases asignadas.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
			    JOptionPane.showMessageDialog(this, "Error al dar de baja la sala", "Error", JOptionPane.ERROR_MESSAGE);
            }
			break;
		default:
			break;
		}
	}
}