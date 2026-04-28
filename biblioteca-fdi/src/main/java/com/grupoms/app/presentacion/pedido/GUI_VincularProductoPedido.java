package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TLineaPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_VincularProductoPedido extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoIdPedido;
	private JTextField campoIdProducto;
	private JTextField campoCantidad;
	private JButton botonVincular;

	public GUI_VincularProductoPedido() {
		super("Vincular Producto a Pedido");
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
		campoIdPedido = new JTextField(10);

		JLabel labelProducto = new JLabel("ID Producto:");
		campoIdProducto = new JTextField(10);

		JLabel labelCantidad = new JLabel("Cantidad:");
		campoCantidad = new JTextField(10);

		botonVincular = new JButton("Vincular Producto");
		botonVincular.addActionListener(e -> vincularProducto());

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelPedido, gbc);

		gbc.gridx = 1;
		panel.add(campoIdPedido, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelProducto, gbc);

		gbc.gridx = 1;
		panel.add(campoIdProducto, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelCantidad, gbc);

		gbc.gridx = 1;
		panel.add(campoCantidad, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panel.add(botonVincular, gbc);

		add(panel);
	}

	private void vincularProducto() {

		try {
			int idPedido = Integer.parseInt(campoIdPedido.getText());
			int idProducto = Integer.parseInt(campoIdProducto.getText());
			int cantidad = Integer.parseInt(campoCantidad.getText());

			TLineaPedido linea = new TLineaPedido();
			linea.setPedidoID(idPedido);
			linea.setProductID(idProducto);
			linea.setCantidad(cantidad);
			linea.setActivo(true);

			Context contexto = new Context(
					Evento.VINCULAR_PRODUCTO_PEDIDO, linea);

			Controlador.getInstance().handle(contexto);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Todos los campos deben ser numéricos");
		}
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {

		case Evento.VINCULAR_PRODUCTO_PEDIDO_OK:
			JOptionPane.showMessageDialog(this,
					"Producto vinculado correctamente");
			limpiarCampos();
			dispose();
			break;

		case Evento.VINCULAR_PRODUCTO_PEDIDO_KO:
			String mensaje = (String) context.getDatos();
		    JOptionPane.showMessageDialog(this, mensaje);
			break;

		default:
			break;
		}
	}

	private void limpiarCampos() {
		campoIdPedido.setText("");
		campoIdProducto.setText("");
		campoCantidad.setText("");
	}
}