package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarPedido extends JFrame implements IGUI {

	private JTextField campoIdPedido;
	private JTextField campoMesa;
	private JTextField campoEmpleado;
	private JComboBox<String> comboEstado;
	private JCheckBox checkActivo;
	private JButton botonModificar;

	public GUI_ModificarPedido() {
		setTitle("Modificar Pedido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		initGUI();
	}

	private void initGUI() {

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelIdPedido = new JLabel("ID Pedido:");
		campoIdPedido = new JTextField(10);

		JLabel labelMesa = new JLabel("ID Mesa:");
		campoMesa = new JTextField(10);

		JLabel labelEmpleado = new JLabel("ID Empleado:");
		campoEmpleado = new JTextField(10);

		JLabel labelEstado = new JLabel("Estado:");
		comboEstado = new JComboBox<>(new String[] {
				"ABIERTO", "CERRADO", "DEVUELTO"
		});

		JLabel labelActivo = new JLabel("Activo:");
		checkActivo = new JCheckBox();

		botonModificar = new JButton("Modificar Pedido");

		botonModificar.addActionListener(e -> modificarPedido());

		gbc.gridx = 0; gbc.gridy = 0;
		panel.add(labelIdPedido, gbc);
		gbc.gridx = 1;
		panel.add(campoIdPedido, gbc);

		gbc.gridx = 0; gbc.gridy = 1;
		panel.add(labelMesa, gbc);
		gbc.gridx = 1;
		panel.add(campoMesa, gbc);

		gbc.gridx = 0; gbc.gridy = 2;
		panel.add(labelEmpleado, gbc);
		gbc.gridx = 1;
		panel.add(campoEmpleado, gbc);

		gbc.gridx = 0; gbc.gridy = 3;
		panel.add(labelEstado, gbc);
		gbc.gridx = 1;
		panel.add(comboEstado, gbc);

		gbc.gridx = 0; gbc.gridy = 4;
		panel.add(labelActivo, gbc);
		gbc.gridx = 1;
		panel.add(checkActivo, gbc);

		gbc.gridx = 0; gbc.gridy = 5;
		gbc.gridwidth = 2;
		panel.add(botonModificar, gbc);

		add(panel, BorderLayout.CENTER);
	}

	private void modificarPedido() {

		try {
			int idPedido = Integer.parseInt(campoIdPedido.getText());

			Integer idMesa = campoMesa.getText().isEmpty() ?
					null : Integer.parseInt(campoMesa.getText());

			Integer idEmpleado = campoEmpleado.getText().isEmpty() ?
					null : Integer.parseInt(campoEmpleado.getText());

			String estado = (String) comboEstado.getSelectedItem();
			boolean activo = checkActivo.isSelected();

			TPedido pedido = new TPedido();
			pedido.setId(idPedido);

			if (idMesa != null)
				pedido.setIdMesa(idMesa);

			if (idEmpleado != null)
				pedido.setIdEmpleado(idEmpleado);

			pedido.setEstado(estado);
			pedido.setActivo(activo);

			Context contexto = new Context(
					Evento.MODIFICAR_PEDIDO, pedido);

			Controlador.getInstance().handle(contexto);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Los campos numéricos no son válidos");
		}
	}

	@Override
	public void actualizar(Context context) {

		if (context == null) {
			setVisible(true);
			return;
		}

		switch (context.getEvento()) {

		case Evento.MODIFICAR_PEDIDO_OK:
			JOptionPane.showMessageDialog(this,
					"Pedido modificado con éxito");
			limpiarCampos();
			break;

		case Evento.MODIFICAR_PEDIDO_KO:
		    JOptionPane.showMessageDialog(this,
		        "Error modificando el pedido. Revise que los IDs existen y están activos.",
		        "Error",
		        JOptionPane.ERROR_MESSAGE);
		    break;
		}
		limpiarCampos();
	}

	private void limpiarCampos() {
		campoIdPedido.setText("");
		campoMesa.setText("");
		campoEmpleado.setText("");
		checkActivo.setSelected(false);
	}
}