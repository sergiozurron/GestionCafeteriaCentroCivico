package com.grupoms.app.presentacion.proveedor;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_BajaProveedor extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JButton btnBaja;

	public GUI_BajaProveedor() {
		super("Baja Proveedor");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
		} else if (context.getEvento() == Evento.BAJA_PROVEEDOR_OK) {
			JOptionPane.showMessageDialog(this, "Proveedor dado de baja con éxito");
			campoId.setText("");
		} else if (context.getEvento() == Evento.BAJA_PROVEEDOR_KO) {
			JOptionPane.showMessageDialog(this, "Error al dar de baja al proveedor. Verifique que el ID existe y está activo.");
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

		btnBaja = new JButton("Dar de Baja");
		btnBaja.addActionListener(e -> {
			try {
				Integer id = Integer.parseInt(campoId.getText().trim());

				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0");
					return;
				}

				// Crear TProveedor con el ID
				TProveedor proveedor = new TProveedor();
				proveedor.setId(id);
				proveedor.setActivo(false); // Baja lógica

				// Enviar al controlador
				Context contexto = new Context(Evento.BAJA_PROVEEDOR, proveedor);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: El ID debe ser un número válido");
			}
		});

		// Colocación
		gbc.gridx = 0; gbc.gridy = 0;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoId, gbc);

		gbc.gridx = 0; gbc.gridy = 1;
		gbc.gridwidth = 2;
		panel.add(btnBaja, gbc);

		add(panel, BorderLayout.CENTER);
	}
}
