package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_CerrarPedido extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoIdPedido;
	private JButton botonCerrar;

	private JLabel labelEstado;
	private JLabel labelTotal;

	public GUI_CerrarPedido() {
		setTitle("Cerrar Pedido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);
		initGUI();
	}

	private void initGUI() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		int y = 0;

		// Campo para ID
		JLabel labelId = new JLabel("ID Pedido:");
		campoIdPedido = new JTextField(10);
		gbc.gridx = 0; gbc.gridy = y;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoIdPedido, gbc);

		y++;
		// Botón Cerrar Pedido
		botonCerrar = new JButton("Cerrar Pedido");
		gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
		panel.add(botonCerrar, gbc);

		botonCerrar.addActionListener(e -> {
			try {
				int idPedido = Integer.parseInt(campoIdPedido.getText().trim());
				Context contexto = new Context(Evento.CERRAR_PEDIDO, idPedido);
				Controlador.getInstance().handle(contexto);
			} catch (NumberFormatException ex) {
				// No hacemos lógica de negocio aquí
				JOptionPane.showMessageDialog(this, "El ID debe ser numérico");
			}
		});

		y++;
		gbc.gridwidth = 1;
		// Labels para mostrar resultados
		labelEstado = new JLabel("Estado: ");
		gbc.gridx = 0; gbc.gridy = y;
		panel.add(labelEstado, gbc);

		labelTotal = new JLabel("Total: ");
		gbc.gridx = 1;
		panel.add(labelTotal, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}

		if (context.getEvento() == Evento.CERRAR_PEDIDO_OK) {
			TPedido pedido = (TPedido) context.getDatos();
			if (pedido != null) {
				labelEstado.setText("Estado: " + pedido.getEstado());
				labelTotal.setText("Total: " + (pedido.getTotal()));
			}
			JOptionPane.showMessageDialog(this, "Pedido cerrado con éxito");
		} else if (context.getEvento() == Evento.CERRAR_PEDIDO_KO) {
			JOptionPane.showMessageDialog(this, "Error al cerrar el pedido");
		}
	}
}