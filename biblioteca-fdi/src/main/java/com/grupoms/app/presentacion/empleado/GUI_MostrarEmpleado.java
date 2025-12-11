package com.grupoms.app.presentacion.empleado;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarEmpleado extends JFrame implements IGUI {

	private JTextField campoID;
	private JButton btnMostrar;

	private JLabel nombreLabel;
	private JLabel dondeAtiendeLabel;
	private JLabel sueldoLabel;
	private JLabel activoLabel;

	public GUI_MostrarEmpleado() {
		super("Mostrar Empleado");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void initGUI() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelID = new JLabel("ID Empleado:");
		campoID = new JTextField(10);

		btnMostrar = new JButton("Mostrar Empleado");
		btnMostrar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(campoID.getText().trim());
				TEmpleado emp = new TEmpleado();
				emp.setID(id);

				Context contexto = new Context();
				contexto.setEvento(Evento.MOSTRAR_EMPLEADO);
				contexto.setDato(emp);

				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
			}
		});

		nombreLabel = new JLabel();
		dondeAtiendeLabel = new JLabel();
		sueldoLabel = new JLabel();
		activoLabel = new JLabel();

		int y = 0;

		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelID, gbc);
		gbc.gridx = 1;
		panel.add(campoID, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(btnMostrar, gbc);

		y++;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Nombre:"), gbc);
		gbc.gridx = 1;
		panel.add(nombreLabel, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Donde atiende:"), gbc);
		gbc.gridx = 1;
		panel.add(dondeAtiendeLabel, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Sueldo:"), gbc);
		gbc.gridx = 1;
		panel.add(sueldoLabel, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Activo:"), gbc);
		gbc.gridx = 1;
		panel.add(activoLabel, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			return;
		}

		SwingUtilities.invokeLater(() -> {
			if (context.getEvento() == Evento.MOSTRAR_EMPLEADO_OK) {
				TEmpleado emp = (TEmpleado) context.getDatos();
				if (emp != null) {
					nombreLabel.setText(emp.getNombre() != null ? emp.getNombre() : "N/A");
					dondeAtiendeLabel.setText(emp.getDondeAtiende() != null ? emp.getDondeAtiende() : "N/A");
					sueldoLabel.setText(emp.getSueldo() != null ? String.valueOf(emp.getSueldo()) : "0.00");
					Boolean act = emp.getActivo();
					activoLabel.setText(act != null && act ? "Sí" : "No");
				} else {
					limpiarLabels();
				}
			} else if (context.getEvento() == Evento.MOSTRAR_EMPLEADO_KO) {
				JOptionPane.showMessageDialog(this, "Empleado no encontrado en la base de datos");
				limpiarLabels();
			}
		});
	}

	private void limpiarLabels() {
		nombreLabel.setText("");
		dondeAtiendeLabel.setText("");
		sueldoLabel.setText("");
		activoLabel.setText("");
	}
}
