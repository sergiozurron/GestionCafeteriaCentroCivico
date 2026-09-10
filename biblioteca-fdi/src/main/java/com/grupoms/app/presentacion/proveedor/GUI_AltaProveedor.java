package com.grupoms.app.presentacion.proveedor;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoNombre;
	private JTextField campoTarifa;
	private JTextField campoTiempoEntrega;
	private JButton btnCrear;

	public GUI_AltaProveedor() {
		super("Alta Proveedor");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
		} else if (context.getEvento() == Evento.ALTA_PROVEEDOR_OK) {
			JOptionPane.showMessageDialog(this, "Proveedor creado con éxito");

			campoNombre.setText("");
			campoTarifa.setText("");
			campoTiempoEntrega.setText("");
		} else if (context.getEvento() == Evento.ALTA_PROVEEDOR_KO) {
			String mensaje = (String) context.getDatos();
		    JOptionPane.showMessageDialog(this, mensaje);
		}
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelNombre = new JLabel("Nombre Proveedor:");
		campoNombre = new JTextField(15);

		JLabel labelTarifa = new JLabel("Tarifa:");
		campoTarifa = new JTextField(15);

		JLabel labelTiempo = new JLabel("Tiempo Entrega (días):");
		campoTiempoEntrega = new JTextField(15);

		btnCrear = new JButton("Crear Proveedor");
		btnCrear.addActionListener(e -> {
			try {
				String nombre = campoNombre.getText().trim();
				Double tarifa = Double.parseDouble(campoTarifa.getText().trim());
				Integer tiempoEntrega = Integer.parseInt(campoTiempoEntrega.getText().trim());

				if (nombre.isEmpty()) {
					JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
					return;
				}
				if (tarifa < 0) {
					JOptionPane.showMessageDialog(this, "La tarifa no puede ser negativa");
					return;
				}
				if (tiempoEntrega < 0) {
					JOptionPane.showMessageDialog(this, "El tiempo de entrega no puede ser negativo");
					return;
				}

				TProveedor proveedor = new TProveedor();
				proveedor.setNombre(nombre);
				proveedor.setTarifa(tarifa);
				proveedor.setTiempoEntrega(tiempoEntrega);

				Context contexto = new Context(Evento.ALTA_PROVEEDOR, proveedor);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: Tarifa y tiempo de entrega deben ser números válidos");
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelNombre, gbc);
		gbc.gridx = 1;
		panel.add(campoNombre, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelTarifa, gbc);
		gbc.gridx = 1;
		panel.add(campoTarifa, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelTiempo, gbc);
		gbc.gridx = 1;
		panel.add(campoTiempoEntrega, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panel.add(btnCrear, gbc);

		add(panel, BorderLayout.CENTER);
	}
}
