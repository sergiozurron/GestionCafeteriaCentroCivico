package com.grupoms.app.presentacion.proveedor;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JTextField campoNombre;
	private JTextField campoTarifa;
	private JTextField campoTiempoEntrega;
	private JCheckBox checkActivo;
	private JButton btnModificar;

	public GUI_ModificarProveedor() {
		super("Modificar Proveedor");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
		} else if (context.getEvento() == Evento.MODIFICAR_PROVEEDOR_OK) {
			JOptionPane.showMessageDialog(this, "Proveedor modificado con éxito");
			// Limpiar campos
			campoId.setText("");
			campoNombre.setText("");
			campoTarifa.setText("");
			campoTiempoEntrega.setText("");
			checkActivo.setSelected(true);
		} else if (context.getEvento() == Evento.MODIFICAR_PROVEEDOR_KO) {
			JOptionPane.showMessageDialog(this, "Error: No se pudo modificar el proveedor. Verifique los datos.");
		}
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelId = new JLabel("ID Proveedor:");
		campoId = new JTextField(15);

		JLabel labelNombre = new JLabel("Nombre Proveedor:");
		campoNombre = new JTextField(15);

		JLabel labelTarifa = new JLabel("Tarifa:");
		campoTarifa = new JTextField(15);

		JLabel labelTiempo = new JLabel("Tiempo Entrega (días):");
		campoTiempoEntrega = new JTextField(15);

		JLabel labelActivo = new JLabel("Activo:");
		checkActivo = new JCheckBox();
		checkActivo.setSelected(true);

		btnModificar = new JButton("Modificar Proveedor");
		btnModificar.addActionListener(e -> {
			try {
				Integer id = Integer.parseInt(campoId.getText().trim());
				String nombre = campoNombre.getText().trim();
				Double tarifa = Double.parseDouble(campoTarifa.getText().trim());
				Integer tiempoEntrega = Integer.parseInt(campoTiempoEntrega.getText().trim());
				Boolean activo = checkActivo.isSelected();

				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0");
					return;
				}
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

				// Crear TProveedor
				TProveedor proveedor = new TProveedor();
				proveedor.setId(id);
				proveedor.setNombre(nombre);
				proveedor.setTarifa(tarifa);
				proveedor.setTiempoEntrega(tiempoEntrega);
				proveedor.setActivo(activo);

				// Enviar al controlador
				Context contexto = new Context(Evento.MODIFICAR_PROVEEDOR, proveedor);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: ID, tarifa y tiempo de entrega deben ser números válidos");
			}
		});

		// Colocación
		int y = 0;
		gbc.gridx = 0; gbc.gridy = y;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoId, gbc);

		y++;
		gbc.gridx = 0; gbc.gridy = y;
		panel.add(labelNombre, gbc);
		gbc.gridx = 1;
		panel.add(campoNombre, gbc);

		y++;
		gbc.gridx = 0; gbc.gridy = y;
		panel.add(labelTarifa, gbc);
		gbc.gridx = 1;
		panel.add(campoTarifa, gbc);

		y++;
		gbc.gridx = 0; gbc.gridy = y;
		panel.add(labelTiempo, gbc);
		gbc.gridx = 1;
		panel.add(campoTiempoEntrega, gbc);

		y++;
		gbc.gridx = 0; gbc.gridy = y;
		panel.add(labelActivo, gbc);
		gbc.gridx = 1;
		panel.add(checkActivo, gbc);

		y++;
		gbc.gridx = 0; gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(btnModificar, gbc);

		add(panel, BorderLayout.CENTER);
	}
}
