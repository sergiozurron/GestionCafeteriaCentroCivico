package com.grupoms.app.presentacion.empleado;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_BajaEmpleado extends JFrame implements IGUI {

	private JTextField idEmpleado;
	private JButton baja;

	public GUI_BajaEmpleado() {
		super("Baja Empleado");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null)
			setVisible(true);
		else if (context.getEvento() == Evento.BAJA_EMPLEADO_OK) {
			JOptionPane.showMessageDialog(this, "Empleado dado de baja con éxito");
			idEmpleado.setText("");
		} else if (context.getEvento() == Evento.BAJA_EMPLEADO_KO) {
			JOptionPane.showMessageDialog(this, "Error al dar de baja al empleado");
		}
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelEmpleado = new JLabel("ID Empleado:");
		idEmpleado = new JTextField(10);

		baja = new JButton("Baja Empleado");
		baja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idEmpleado.getText().trim());
				TEmpleado empleado = new TEmpleado();
				empleado.setID(id);
				empleado.setActivo(false);

				Context contexto = new Context(Evento.BAJA_EMPLEADO, empleado);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
			}
		});

		int y = 0;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelEmpleado, gbc);
		gbc.gridx = 1;
		panel.add(idEmpleado, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(baja, gbc);

		add(panel, BorderLayout.CENTER);
	}
}
