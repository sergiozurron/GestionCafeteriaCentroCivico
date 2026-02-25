package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.negocio.pedido.TPedidoLinea;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarPedido extends JFrame implements IGUI {
	private JTextField campoPedido;
	private JButton btnMostrar;

	private JLabel fecha;
	private JLabel total_factura;
	private JLabel estado;
	private JLabel empleado_id;
	private JLabel mesa_id;
	private JLabel activo;

	public GUI_MostrarPedido() {
		super("Mostrar Pedido");
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

		JLabel labelId = new JLabel("ID Pedido:");
		campoPedido = new JTextField(10);

		btnMostrar = new JButton("Mostrar Pedido");
		btnMostrar.addActionListener(e -> {
			try {
				int idPedido = Integer.parseInt(campoPedido.getText().trim());
				TPedidoLinea pedidoLinea = new TPedidoLinea();
				TPedido pedido = pedidoLinea.gettPedido();

				Context contexto = new Context(Evento.MOSTRAR_PEDIDO, pedido);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: el ID debe ser un número entero");
			}
		});

		fecha = new JLabel();
		total_factura = new JLabel();
		estado = new JLabel();
		empleado_id = new JLabel();
		mesa_id = new JLabel();
		activo = new JLabel();

		int y = 0;

		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoPedido, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(btnMostrar, gbc);

		y++;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Fecha:"), gbc);
		gbc.gridx = 1;
		panel.add(fecha, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Total Factura:"), gbc);
		gbc.gridx = 1;
		panel.add(total_factura, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Estado:"), gbc);
		gbc.gridx = 1;
		panel.add(estado, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("ID Empleado:"), gbc);
		gbc.gridx = 1;
		panel.add(empleado_id, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("ID Mesa:"), gbc);
		gbc.gridx = 1;
		panel.add(mesa_id, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Activo:"), gbc);
		gbc.gridx = 1;
		panel.add(activo, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null)
			return;

		SwingUtilities.invokeLater(() -> {
			if (context.getEvento() == Evento.MOSTRAR_PEDIDO_OK) {
				TPedido pedido = (TPedido) context.getDatos();
				if (pedido != null) {
					fecha.setText(pedido.getFecha() != null ? String.valueOf(pedido.getFecha()) : "N/A");
					total_factura.setText(pedido.getTotal() != null ? String.valueOf(pedido.getTotal()) : "0.00");
					estado.setText(pedido.getEstado() != null ? pedido.getEstado() : "N/A");
					empleado_id
							.setText(pedido.getIdEmpleado() != null ? String.valueOf(pedido.getIdEmpleado()) : "N/A");
					mesa_id.setText(pedido.getIdMesa() != null ? String.valueOf(pedido.getIdMesa()) : "N/A");
					Boolean act = pedido.getActivo();
					activo.setText(act != null && act ? "Sí" : "No");
				} else {
					limpiarLabels();
				}
			} else if (context.getEvento() == Evento.MOSTRAR_PEDIDO_KO) {
				JOptionPane.showMessageDialog(this, "Pedido no encontrado en la base de datos");
				limpiarLabels();
			}
		});
	}

	private void limpiarLabels() {
		fecha.setText("");
		total_factura.setText("");
		estado.setText("");
		empleado_id.setText("");
		mesa_id.setText("");
		activo.setText("");
	}
}
