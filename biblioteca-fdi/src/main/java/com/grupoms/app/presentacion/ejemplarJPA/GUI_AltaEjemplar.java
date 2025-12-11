package com.grupoms.app.presentacion.ejemplarJPA;

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

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaEjemplar extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoIdMaterial;
	private JButton btnCrear;

	public GUI_AltaEjemplar() {
		setTitle("Alta Ejemplar");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelIdMaterial = new JLabel("Id Material:");
		campoIdMaterial = new JTextField(15);

		btnCrear = new JButton("Crear Ejemplar");

		btnCrear.addActionListener(e -> {
			Integer idMaterial;
			try {
				idMaterial = Integer.parseInt(campoIdMaterial.getText().trim());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID del material debe ser un número válido");
				return;
			}

			TEjemplar ejemplar = new TEjemplar();
			ejemplar.setIdMaterial(idMaterial);

			Context contexto = new Context(Evento.ALTA_EJEMPLAR, ejemplar);
			Controlador.getInstance().handle(contexto);

		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelIdMaterial, gbc);
		gbc.gridx = 1;
		panel.add(campoIdMaterial, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
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
		case Evento.ALTA_EJEMPLAR_OK:
			int idEjemplar = (int) context.getDatos();
			JOptionPane.showMessageDialog(this, "Ejemplar creado con ID: " + idEjemplar);
			break;
		case Evento.ALTA_EJEMPLAR_KO:
			JOptionPane.showMessageDialog(this, "Error al crear el ejemplar", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}

}
