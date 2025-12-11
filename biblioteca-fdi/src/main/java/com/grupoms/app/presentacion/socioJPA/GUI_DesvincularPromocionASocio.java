package com.grupoms.app.presentacion.socioJPA;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;

public class GUI_DesvincularPromocionASocio extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	private JTextField idSocio, idPromocion;
	private JButton btnDesvincular;

	public GUI_DesvincularPromocionASocio() {
		super("Desvincular Promoción a Socio");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelIdSocio = new JLabel("ID Socio: ");
		idSocio = new JTextField(15);

		JLabel labelIdPromocion = new JLabel("ID Promoción: ");
		idPromocion = new JTextField(15);

		btnDesvincular = new JButton("Desvincular");
		btnDesvincular.addActionListener(e -> {
			try {
				Integer idS = Integer.parseInt(idSocio.getText().trim());
				Integer idP = Integer.parseInt(idPromocion.getText().trim());
				if (idSocio.getText().trim().isEmpty() || idPromocion.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Los IDs son obligatorios");
					return;
				}
				if (idS <= 0 || idP <= 0) {
					JOptionPane.showMessageDialog(this, "Los IDs deben ser mayor que 0");
					return;
				}
				Integer[] ids = new Integer[] { idS, idP };
				Context contexto = new Context(Evento.DESVINCULAR_PROMOCION, ids);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: Los IDs deben ser un número válido");
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelIdSocio, gbc);
		gbc.gridx = 1;
		panel.add(idSocio, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelIdPromocion, gbc);
		gbc.gridx = 1;
		panel.add(idPromocion, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(btnDesvincular, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.DESVINCULAR_PROMOCION_OK:
			JOptionPane.showMessageDialog(this, "Desvinculado con éxito.");
			idSocio.setText("");
			idPromocion.setText("");
			break;
		case Evento.DESVINCULAR_PROMOCION_KO:
			JOptionPane.showMessageDialog(this, "Error al desvincular Socio y Promoción.", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}
