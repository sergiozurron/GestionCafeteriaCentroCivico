package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TLineaVenta;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AnyadirProducto extends JFrame implements IGUI {

	private JTextField campoPedido;
	private JTextField campoProducto;
	private JTextField campoCantidad;
	private JButton botonAgregar;

	public GUI_AnyadirProducto() {
		super("Añadir Producto");
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

		JLabel labelPedido = new JLabel("ID Pedido:");
		campoPedido = new JTextField(10);
		JLabel labelProducto = new JLabel("ID Producto:");
		campoProducto = new JTextField(10);
		JLabel labelCantidad = new JLabel("Cantidad:");
		campoCantidad = new JTextField(10);

		botonAgregar = new JButton("Añadir Producto");
		botonAgregar.addActionListener(e -> agregarProducto());

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelPedido, gbc);
		gbc.gridx = 1;
		panel.add(campoPedido, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelProducto, gbc);
		gbc.gridx = 1;
		panel.add(campoProducto, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelCantidad, gbc);
		gbc.gridx = 1;
		panel.add(campoCantidad, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panel.add(botonAgregar, gbc);

		add(panel);
	}

	private void agregarProducto() {
		try {
			int idPedido = Integer.parseInt(campoPedido.getText());
			int idProducto = Integer.parseInt(campoProducto.getText());
			int cantidad = Integer.parseInt(campoCantidad.getText());

			if (idPedido <= 0 || idProducto <= 0 || cantidad <= 0) {
				JOptionPane.showMessageDialog(this, "Todos los valores deben ser mayores que 0");
				return;
			}

			TLineaVenta orden = new TLineaVenta();
			orden.setPedidoID(idPedido);
			orden.setProductID(idProducto);
			orden.setCantidad(cantidad);

			Context contexto = new Context(Evento.ALTA_ORDEN, orden);
			Controlador.getInstance().handle(contexto);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Los campos deben ser numéricos");
		}
	}

	@Override
	public void actualizar(Context context) {
		if (context == null)
			setVisible(true);
		else if (context.getEvento() == Evento.ALTA_ORDEN_OK) {
			JOptionPane.showMessageDialog(this, "Producto añadido al pedido correctamente");
			campoProducto.setText("");
			campoCantidad.setText("");
		} else if (context.getEvento() == Evento.ALTA_ORDEN_KO) {
			JOptionPane.showMessageDialog(this, "Error: No se pudo añadir el producto al pedido", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
