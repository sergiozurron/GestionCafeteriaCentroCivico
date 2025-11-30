package com.grupoms.app.presentacion.salaJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarSala extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JTextField campoNombre;
	private JTextField campoCapacidad;
	private JCheckBox checkActivo;
	private JButton btnModificar;

	public GUI_ModificarSala() {
		setTitle("Modificar Sala");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelId = new JLabel("ID Sala:");
		campoId = new JTextField(10);

		JLabel labelNombre = new JLabel("Nombre:");
		campoNombre = new JTextField(15);

		JLabel labelCapacidad = new JLabel("Capacidad:");
		campoCapacidad = new JTextField(10);

		checkActivo = new JCheckBox("Activa");
		checkActivo.setSelected(true);

		btnModificar = new JButton("Modificar Sala");

		btnModificar.addActionListener(e -> {
			String idTexto = campoId.getText().trim();
			String nombre = campoNombre.getText().trim();
			String capacidadTexto = campoCapacidad.getText().trim();

			if (idTexto.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El ID de la sala es obligatorio");
				return;
			}
			if (nombre.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
				return;
			}
			if (capacidadTexto.isEmpty()) {
				JOptionPane.showMessageDialog(this, "La capacidad es obligatoria");
				return;
			}

			Integer idSala;
			try {
				idSala = Integer.parseInt(idTexto);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID debe ser un número válido");
				return;
			}

			Integer capacidad;
			try {
				capacidad = Integer.parseInt(capacidadTexto);
                if (capacidad <= 0) throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "La capacidad debe ser un número entero positivo");
				return;
			}

			TSala sala = new TSala();
			sala.setId(idSala);
			sala.setNombre(nombre);
			sala.setCapacidad(capacidad);
			sala.setActivo(checkActivo.isSelected());

			Context contexto = new Context(Evento.MODIFICAR_SALA, sala);
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
		panel.add(campoNombre, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelCapacidad, gbc);
		gbc.gridx = 1;
		panel.add(campoCapacidad, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panel.add(checkActivo, gbc);

		gbc.gridy = 4;
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
		case Evento.MODIFICAR_SALA_OK:
			JOptionPane.showMessageDialog(this, "Sala modificada correctamente");
			break;
		case Evento.MODIFICAR_SALA_KO:
			JOptionPane.showMessageDialog(this, "Error al modificar la sala", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}