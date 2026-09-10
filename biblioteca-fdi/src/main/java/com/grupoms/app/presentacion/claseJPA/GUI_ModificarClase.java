package com.grupoms.app.presentacion.claseJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarClase extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JTextField campoTipo;
	private JTextField campoFechaInicio;
	private JTextField campoDuracion;
	private JTextField campoIdSala;

	private JButton btnModificar;

	public GUI_ModificarClase() {
		super("Modificar Clase");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelId = new JLabel("ID Clase:");
		campoId = new JTextField(10);

		JLabel labelTipo = new JLabel("Tipo:");
		campoTipo = new JTextField(15);

		JLabel labelFechaInicio = new JLabel("Fecha inicio (yyyy-MM-dd HH:mm):");
		campoFechaInicio = new JTextField(15);

		JLabel labelDuracion = new JLabel("Duración (minutos):");
		campoDuracion = new JTextField(10);

		JLabel labelIdSala = new JLabel("ID Sala:");
		campoIdSala = new JTextField(10);

		btnModificar = new JButton("Modificar Clase");

		btnModificar.addActionListener(e -> {
			try {

				if (campoId.getText().isEmpty() || campoTipo.getText().isEmpty() || campoFechaInicio.getText().isEmpty()
						|| campoDuracion.getText().isEmpty() || campoIdSala.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos.");
					return;
				}

				int idClase = Integer.parseInt(campoId.getText());
				String tipo = campoTipo.getText();
				int duracion = Integer.parseInt(campoDuracion.getText());
				int idSala = Integer.parseInt(campoIdSala.getText());

				if (duracion <= 0) {
					JOptionPane.showMessageDialog(this, "La duración debe ser mayor a 0");
					return;
				}

				Date fechaInicio;
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					sdf.setLenient(false);
					fechaInicio = sdf.parse(campoFechaInicio.getText());
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use: yyyy-MM-dd HH:mm");
					return;
				}

				TClase clase = new TClase();
				clase.setId(idClase);
				clase.setTipo(tipo);
				clase.setFechaInicio(fechaInicio);
				clase.setDuracion(duracion);
				clase.setIdSala(idSala);

				Context contexto = new Context(Evento.MODIFICAR_CLASE, clase);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: ID, Duración e ID Sala deben ser números válidos");
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoId, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelTipo, gbc);
		gbc.gridx = 1;
		panel.add(campoTipo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelFechaInicio, gbc);
		gbc.gridx = 1;
		panel.add(campoFechaInicio, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(labelDuracion, gbc);
		gbc.gridx = 1;
		panel.add(campoDuracion, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add(labelIdSala, gbc);
		gbc.gridx = 1;
		panel.add(campoIdSala, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
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

		if (context.getEvento() == Evento.MODIFICAR_CLASE_OK) {
			JOptionPane.showMessageDialog(this, "Clase modificada con éxito");

			campoId.setText("");
			campoTipo.setText("");
			campoFechaInicio.setText("");
			campoDuracion.setText("");
			campoIdSala.setText("");

		} else if (context.getEvento() == Evento.MODIFICAR_CLASE_KO) {
			JOptionPane.showMessageDialog(this, "No se ha podido modificar la clase", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}