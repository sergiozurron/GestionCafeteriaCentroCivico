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

public class GUI_ModificarEjemplar extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JTextField campoEstado;
	private JTextField campoIdMaterial;
	private JButton btnModificar;

	public GUI_ModificarEjemplar() {
		setTitle("Modificar Ejemplar");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelId = new JLabel("ID:");
		campoId = new JTextField(15);

		JLabel labelNombre = new JLabel("Estado:");
		campoEstado = new JTextField(15);

		JLabel labelIdMaterial = new JLabel("Id Material:");
		campoIdMaterial = new JTextField(15);

		btnModificar = new JButton("Modificar Ejemplar");

		btnModificar.addActionListener(e -> {
			String estado = campoEstado.getText().trim();

			Integer id;
			try {
				id = Integer.parseInt(campoIdMaterial.getText().trim());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID debe ser un número válido");
				return;
			}

			if (estado.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El estado es obligatorio");
				return;
			}

			Integer idMaterial;
			try {
				idMaterial = Integer.parseInt(campoIdMaterial.getText().trim());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID del material debe ser un número válido");
				return;
			}

			TEjemplar ejemplar = new TEjemplar();
			ejemplar.setId(id);
			ejemplar.setEstado(estado);
			ejemplar.setIdMaterial(idMaterial);

			Context contexto = new Context(Evento.MODIFICAR_EJEMPLAR, ejemplar);
			Controlador.getInstance().handle(contexto);

		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoId, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelNombre, gbc);
		gbc.gridx = 1;
		panel.add(campoEstado, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelIdMaterial, gbc);
		gbc.gridx = 1;
		panel.add(campoIdMaterial, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panel.add(btnModificar, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.MODIFICAR_EJEMPLAR_OK:
			limpiarCampos();
			dispose();
			JOptionPane.showMessageDialog(this, "Ejemplar modificado con éxito");
			break;
		case Evento.MODIFICAR_EJEMPLAR_KO:
			JOptionPane.showMessageDialog(this, "Error al modificar el ejemplar", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}

	private void limpiarCampos() {
		campoId.setText("");
		campoEstado.setText("");
		campoIdMaterial.setText("");		
	}
}
