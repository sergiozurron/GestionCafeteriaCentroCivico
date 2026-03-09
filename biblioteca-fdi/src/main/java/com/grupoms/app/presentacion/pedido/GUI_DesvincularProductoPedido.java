package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TLineaPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_DesvincularProductoPedido extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoIdPedido, campoIdProducto;
	private JButton botonDesvincular;

	public GUI_DesvincularProductoPedido() {
		super("Desvincular Producto de Pedido");
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

		// ----- FILA 0: ID PEDIDO -----
		JLabel labelPedido = new JLabel("ID Pedido:");
		campoIdPedido = new JTextField(10);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		panel.add(labelPedido, gbc);

		gbc.gridx = 1;
		panel.add(campoIdPedido, gbc);

		// ----- FILA 1: ID PRODUCTO -----
		JLabel labelProducto = new JLabel("ID Producto:");
		campoIdProducto = new JTextField(10);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelProducto, gbc);

		gbc.gridx = 1;
		panel.add(campoIdProducto, gbc);

		// ----- FILA 2: BOTÓN -----
		botonDesvincular = new JButton("Desvincular");
		botonDesvincular.addActionListener(e -> desvincularProducto());

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(botonDesvincular, gbc);

		add(panel);
	}

	private void desvincularProducto() {
		
		try {

			int idProducto= Integer.parseInt(campoIdProducto.getText());
			int idPedido = Integer.parseInt(campoIdPedido.getText());
			TLineaPedido linea = new TLineaPedido();
			linea.setPedidoID(idPedido);
			linea.setProductID(idProducto);
			linea.setActivo(false); // Baja lógica

			Context contexto = new Context(
					Evento.DESVINCULAR_PRODUCTO_PEDIDO, linea);

			Controlador.getInstance().handle(contexto);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"El ID debe ser numérico");
		}
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {

		case Evento.DESVINCULAR_PRODUCTO_PEDIDO_OK:
			JOptionPane.showMessageDialog(this,
					"Producto desvinculado correctamente");
			campoIdProducto.setText("");
			campoIdPedido.setText("");
			dispose();
			break;

		case Evento.DESVINCULAR_PRODUCTO_PEDIDO_KO:

			int error = (int) context.getDatos();

			switch (error) {
			case -1:
				JOptionPane.showMessageDialog(this,
						"La línea no existe");
				break;
			case -2:
				JOptionPane.showMessageDialog(this,
						"La línea ya estaba desactivada");
				break;
			case -3:
				JOptionPane.showMessageDialog(this,
						"El pedido está cerrado");
				break;
			default:
				JOptionPane.showMessageDialog(this,
						"Error desconocido");
				break;
			}
			break;

		default:
			break;
		}
		campoIdProducto.setText("");
		campoIdPedido.setText("");
	}
}