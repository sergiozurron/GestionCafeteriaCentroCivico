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

public class GUI_AltaSala extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoNombre;
	private JTextField campoCapacidad;
	private JCheckBox checkActivo;
	private JButton btnCrear;

	public GUI_AltaSala() {
		setTitle("Alta Sala");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelNombre = new JLabel("Nombre:");
		campoNombre = new JTextField(15);

		JLabel labelCapacidad = new JLabel("Capacidad:");
		campoCapacidad = new JTextField(15);

		checkActivo = new JCheckBox("Activa");
		checkActivo.setSelected(true);

		btnCrear = new JButton("Crear Sala");

		btnCrear.addActionListener(e -> {
			String nombre = campoNombre.getText().trim();
			String capacidadTexto = campoCapacidad.getText().trim();

			if (nombre.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
				return;
			}
			if (capacidadTexto.isEmpty()) {
				JOptionPane.showMessageDialog(this, "La capacidad es obligatoria");
				return;
			}

			Integer capacidad;
			try {
				capacidad = Integer.parseInt(capacidadTexto);
				if(capacidad <= 0) throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "La capacidad debe ser un número entero positivo");
				return;
			}

			TSala sala = new TSala();
			sala.setNombre(nombre);
			sala.setCapacidad(capacidad);
			sala.setActivo(checkActivo.isSelected());

			Context contexto = new Context(Evento.ALTA_SALA, sala);
			Controlador.getInstance().handle(contexto);
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelNombre, gbc);
		gbc.gridx = 1;
		panel.add(campoNombre, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelCapacidad, gbc);
		gbc.gridx = 1;
		panel.add(campoCapacidad, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(checkActivo, gbc);

		gbc.gridy = 3;
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
		case Evento.ALTA_SALA_OK:
			int idSala = (int) context.getDatos();
			JOptionPane.showMessageDialog(this, "Sala creada con ID: " + idSala);
			break;
		case Evento.ALTA_SALA_KO:
			JOptionPane.showMessageDialog(this, "Error al crear la sala. " + 
                    (context.getDatos() != null ? context.getDatos() : ""), "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}