package com.grupoms.app.presentacion.promocionJPA;

import javax.swing.*;

import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;

public class GUI_AltaPromocion extends JFrame implements IGUI {

	private JTextField campoTipo, campoDescuento;

	public GUI_AltaPromocion() {
		super("Alta Promocion");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelTipo = new JLabel("Tipo de Promoción:");
		campoTipo = new JTextField(20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelTipo, gbc);
		gbc.gridx = 1;
		panel.add(campoTipo, gbc);

		JLabel labelDescuento = new JLabel("Descuento:");
		campoDescuento = new JTextField(20);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelDescuento, gbc);
		gbc.gridx = 1;
		panel.add(campoDescuento, gbc);

		JButton aceptar = new JButton("Aceptar");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		aceptar.addActionListener(e -> crearPromocion());

		panel.add(aceptar, gbc);
		add(panel, BorderLayout.CENTER);
	}

	private void crearPromocion() {
		try {
			String tipo = campoTipo.getText();
			String descuentoStr = campoDescuento.getText();

			if (tipo.isEmpty() || descuentoStr.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			double descuento;
			try {
				descuento = Double.parseDouble(descuentoStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Descuento debe ser un número válido.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			TPromocion promocion = new TPromocion(descuento, tipo);
			Context contexto = new Context(Evento.ALTA_PROMOCION, promocion);
			Controlador.getInstance().handle(contexto);

			JOptionPane.showMessageDialog(this, "Promoción creada con éxito.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al crear la promoción.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.ALTA_PROMOCION_OK:
			JOptionPane.showMessageDialog(this, "Promoción creada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			campoTipo.setText("");
			campoDescuento.setText("");
			break;
		case Evento.ALTA_PROMOCION_KO:
			JOptionPane.showMessageDialog(this, "Error al crear la promoción", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}
