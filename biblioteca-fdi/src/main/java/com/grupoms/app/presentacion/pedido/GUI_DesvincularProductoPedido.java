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

	private JTextField campoIdLinea;
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

		JLabel labelLinea = new JLabel("ID Línea Pedido:");
		campoIdLinea = new JTextField(10);

		botonDesvincular = new JButton("Desvincular");
		botonDesvincular.addActionListener(e -> desvincularProducto());

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelLinea, gbc);

		gbc.gridx = 1;
		panel.add(campoIdLinea, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		panel.add(botonDesvincular, gbc);

		add(panel);
	}

	private void desvincularProducto() {

		try {
			int idLinea = Integer.parseInt(campoIdLinea.getText());

			TLineaPedido linea = new TLineaPedido();
			linea.setId(idLinea);
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

		switch (context.getEvento()) {

		case Evento.DESVINCULAR_PRODUCTO_PEDIDO_OK:
			JOptionPane.showMessageDialog(this,
					"Producto desvinculado correctamente");
			campoIdLinea.setText("");
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
	}
}