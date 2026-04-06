package com.grupoms.app.presentacion.prestamoJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.prestamoJPA.TCalculoPrecioPromocion;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_CalcularPrecioPromocion extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoIdPromocion;
	private JTextField campoIdSocio;

	public GUI_CalcularPrecioPromocion() {
		setTitle("Calcular Precio Promoción");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JButton btnCalcular = new JButton("Calcular Precio");
		btnCalcular.addActionListener(e -> {
			String idPromocionString = campoIdPromocion.getText().trim();
			String idSocioString = campoIdSocio.getText().trim();

			if (idPromocionString.isEmpty()) {
				JOptionPane.showMessageDialog(this, "ID del Ejemplar es obligatorio");
				return;
			}

			if (idSocioString.isEmpty()) {
				JOptionPane.showMessageDialog(this, "ID del Socio es obligatorio");
				return;
			}
			
			Integer idPromocion;
			try {
				idPromocion = Integer.parseInt(campoIdPromocion.getText().trim());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID del Ejemplar debe ser un número válido");
				return;
			}
			
			Integer idSocio;
			try {
				idSocio = Integer.parseInt(campoIdSocio.getText().trim());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID del Socio debe ser un número válido");
				return;
			}
			
			TCalculoPrecioPromocion calculo = new TCalculoPrecioPromocion(idPromocion, idSocio);

			Context contexto = new Context(Evento.CALCULO_PRECIO_PROMOCION, calculo);
			Controlador.getInstance().handle(contexto);
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel("ID Promoción:"), gbc);

		gbc.gridx = 1;
		campoIdPromocion = new JTextField(20);
		panel.add(campoIdPromocion, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(new JLabel("ID Socio:"), gbc);
		panel.add(new JLabel("ID Socio:"), gbc);

		gbc.gridx = 1;
		campoIdSocio = new JTextField(20);
		panel.add(campoIdSocio, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(btnCalcular, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.CALCULO_PRECIO_PROMOCION_OK:
			Double resultado = (Double) context.getDatos();
			JOptionPane.showMessageDialog(this, "Precio de prestamo tras aplicar la promoción: " + resultado + "€");
			dispose();
			limpiarCampos();
			break;
		case Evento.CALCULO_PRECIO_PROMOCION_KO:
			JOptionPane.showMessageDialog(this, "Error al calcular el precio de la promoción");
			break;
		}
	}

	private void limpiarCampos() {
		campoIdPromocion.setText("");
		campoIdSocio.setText("");
	}

}
