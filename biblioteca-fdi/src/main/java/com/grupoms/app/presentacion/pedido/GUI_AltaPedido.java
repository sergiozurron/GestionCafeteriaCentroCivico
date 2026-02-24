package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaPedido extends JFrame implements IGUI {

	private JTextField campoMesa;
	private JTextField campoEmpleado;
	private JButton crearPedido;

	public GUI_AltaPedido() {
		super("Alta Pedido");
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

		JLabel labelMesa = new JLabel("ID Mesa:");
		campoMesa = new JTextField(10);

		JLabel labelEmpleado = new JLabel("ID Empleado:");
		campoEmpleado = new JTextField(10);

		crearPedido = new JButton("Crear Pedido");
		crearPedido.addActionListener(e -> crearPedido());

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelMesa, gbc);
		gbc.gridx = 1;
		panel.add(campoMesa, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelEmpleado, gbc);
		gbc.gridx = 1;
		panel.add(campoEmpleado, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(crearPedido, gbc);

		add(panel);
	}

	private void crearPedido() {
		try {
			int idMesa = Integer.parseInt(campoMesa.getText());
			int idEmpleado = Integer.parseInt(campoEmpleado.getText());

			TPedido pedido = new TPedido();
			pedido.setIdMesa(idMesa);
			pedido.setIdEmpleado(idEmpleado);

			Context contexto = new Context(Evento.ALTA_PEDIDO, pedido);
			Controlador.getInstance().handle(contexto);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
		}
	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.ALTA_PEDIDO_OK) {
			TPedido pedidoCreado = (TPedido) context.getDatos();
			if (pedidoCreado != null && pedidoCreado.getId() != null) {
				JOptionPane.showMessageDialog(this, "Pedido creado con éxito. ID del pedido: " + pedidoCreado.getId());

				campoMesa.setText("");
				campoEmpleado.setText("");
			}
		} else if (context.getEvento() == Evento.ALTA_PEDIDO_KO) {
			JOptionPane.showMessageDialog(this, "Error al crear el pedido");
		}
	}
}
