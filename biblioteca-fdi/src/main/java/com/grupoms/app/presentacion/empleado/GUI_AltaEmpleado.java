package com.grupoms.app.presentacion.empleado;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaEmpleado extends JFrame implements IGUI {

	private JTextField nombre;
	private JTextField dondeAtiende;
	private JTextField sueldo;

	private JButton crear;

	public GUI_AltaEmpleado() {
		super("Alta Empleado");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null)
			setVisible(true);
		else if (context.getEvento() == Evento.ALTA_EMPLEADO_OK) {
			JOptionPane.showMessageDialog(this, "Empleado creado con éxito");

			nombre.setText("");
			dondeAtiende.setText("");
			sueldo.setText("");
		} else if (context.getEvento() == Evento.ALTA_EMPLEADO_KO) {
			JOptionPane.showMessageDialog(this, "Error: No se pudo crear el empleado", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelNombre = new JLabel("Nombre Empleado:");
		nombre = new JTextField(12);

		JLabel labelDonde = new JLabel("Donde atiende:");
		dondeAtiende = new JTextField(12);

		JLabel labelSueldo = new JLabel("Sueldo:");
		sueldo = new JTextField(12);

		crear = new JButton("Crear Empleado");
		crear.addActionListener(e -> {
			try {
				String nombreEmp = nombre.getText().trim();
				String dondeEmp = dondeAtiende.getText().trim();
				Double sueldoEmp = Double.parseDouble(sueldo.getText().trim());

				if (nombreEmp.isEmpty()) {
					JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
					return;
				}
				if (sueldoEmp < 0) {
					JOptionPane.showMessageDialog(this, "El sueldo no puede ser negativo");
					return;
				}

				TEmpleado emp = new TEmpleado();
				emp.setNombre(nombreEmp);
				emp.setDondeAtiende(dondeEmp);
				emp.setSueldo(sueldoEmp);

				Context contexto = new Context(Evento.ALTA_EMPLEADO, emp);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: el sueldo debe ser numérico");
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelNombre, gbc);
		gbc.gridx = 1;
		panel.add(nombre, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelDonde, gbc);
		gbc.gridx = 1;
		panel.add(dondeAtiende, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelSueldo, gbc);
		gbc.gridx = 1;
		panel.add(sueldo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panel.add(crear, gbc);

		add(panel, BorderLayout.CENTER);
	}
}
