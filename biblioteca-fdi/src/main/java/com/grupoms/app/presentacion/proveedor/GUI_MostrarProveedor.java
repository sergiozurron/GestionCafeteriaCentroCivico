package com.grupoms.app.presentacion.proveedor;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JButton btnMostrar;

	private JLabel labelIdValor;
	private JLabel labelNombreValor;
	private JLabel labelTarifaValor;
	private JLabel labelTiempoValor;
	private JLabel labelActivoValor;

	public GUI_MostrarProveedor() {
		super("Mostrar Proveedor");
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

		JLabel labelId = new JLabel("ID Proveedor:");
		campoId = new JTextField(10);

		btnMostrar = new JButton("Mostrar Proveedor");
		btnMostrar.addActionListener(e -> {
			try {
				Integer id = Integer.parseInt(campoId.getText().trim());

				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0");
					return;
				}

				Context contexto = new Context(Evento.MOSTRAR_PROVEEDOR, id);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: El ID debe ser un número válido");
			}
		});

		labelIdValor = new JLabel();
		labelNombreValor = new JLabel();
		labelTarifaValor = new JLabel();
		labelTiempoValor = new JLabel();
		labelActivoValor = new JLabel();

		int y = 0;

		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoId, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(btnMostrar, gbc);

		y++;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("ID:"), gbc);
		gbc.gridx = 1;
		panel.add(labelIdValor, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Nombre:"), gbc);
		gbc.gridx = 1;
		panel.add(labelNombreValor, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Tarifa:"), gbc);
		gbc.gridx = 1;
		panel.add(labelTarifaValor, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Tiempo de Entrega:"), gbc);
		gbc.gridx = 1;
		panel.add(labelTiempoValor, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Activo:"), gbc);
		gbc.gridx = 1;
		panel.add(labelActivoValor, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}

		SwingUtilities.invokeLater(() -> {
			if (context.getEvento() == Evento.MOSTRAR_PROVEEDOR_OK) {
				TProveedor proveedor = (TProveedor) context.getDatos();
				if (proveedor != null) {
					labelIdValor.setText(proveedor.getId() != null ? proveedor.getId().toString() : "N/A");
					labelNombreValor.setText(proveedor.getNombre() != null ? proveedor.getNombre() : "N/A");
					labelTarifaValor.setText(
							proveedor.getTarifa() != null ? String.format("%.2f €", proveedor.getTarifa()) : "0.00 €");
					labelTiempoValor.setText(
							proveedor.getTiempoEntrega() != null ? proveedor.getTiempoEntrega() + " días" : "N/A");
					Boolean activo = proveedor.getActivo();
					labelActivoValor.setText(activo != null && activo ? "Sí" : "No");
				} else {
					limpiarLabels();
				}
			} else if (context.getEvento() == Evento.MOSTRAR_PROVEEDOR_KO) {
				String mensaje = (String) context.getDatos();
			    JOptionPane.showMessageDialog(this, mensaje);
				limpiarLabels();
			}
		});
	}

	private void limpiarLabels() {
		labelIdValor.setText("");
		labelNombreValor.setText("");
		labelTarifaValor.setText("");
		labelTiempoValor.setText("");
		labelActivoValor.setText("");
	}
}
