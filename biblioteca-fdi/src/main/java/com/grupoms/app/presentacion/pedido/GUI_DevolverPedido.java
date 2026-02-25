package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TLineaVenta;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.negocio.pedido.TPedidoLinea;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_DevolverPedido extends JFrame implements IGUI {

	private JTextField campoID;
	private JButton devolver;

	@Override
	public void actualizar(Context context) {
		if (context == null)
			setVisible(true);
		else if (context.getEvento() == Evento.DEVOLVER_PEDIDO_OK) {
			JOptionPane.showMessageDialog(this, "Pedido devuelto con éxito");
			campoID.setText("");
		} else if (context.getEvento() == Evento.DEVOLVER_PEDIDO_KO) {
			JOptionPane.showMessageDialog(this, "Error: No se pudo devolver el pedido", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public GUI_DevolverPedido() {
		super("Devolver Pedido");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);

	}

	public void initGUI() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		setLayout(new BorderLayout());

		JLabel labelIdPedido = new JLabel("ID Pedido:");
		campoID = new JTextField(10);

		devolver = new JButton("Devolver Pedido");
		devolver.addActionListener(e -> {
			try {
				int idPedido = Integer.parseInt(campoID.getText());
				TLineaVenta pedido = new TLineaVenta();
				pedido.setPedidoID(idPedido);
				Context contexto = new Context(Evento.DEVOLVER_PEDIDO, pedido);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
			}
		});
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelIdPedido, gbc);

		gbc.gridx = 1;
		panel.add(campoID, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		panel.add(devolver, gbc);

		add(panel, BorderLayout.CENTER);
	}

}
