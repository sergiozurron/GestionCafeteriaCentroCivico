package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_BajaIngrediente extends JFrame implements IGUI {

	private JTextField idIngrediente;
	private JButton baja;

	public GUI_BajaIngrediente() {
		super("Baja Ingrediente");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null)
			setVisible(true);
		else if (context.getEvento() == Evento.BAJA_INGREDIENTE_OK) {
			JOptionPane.showMessageDialog(this, "Ingrediente dado de baja con éxito");
			idIngrediente.setText("");
		} else if (context.getEvento() == Evento.BAJA_INGREDIENTE_KO) {
			JOptionPane.showMessageDialog(this, "Error al dar de baja el ingrediente");
		}
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelIngrediente = new JLabel("ID Ingrediente:");
		idIngrediente = new JTextField(10);

		baja = new JButton("Baja Ingrediente");
		baja.addActionListener(e -> {
			try {
				int id = Integer.parseInt(idIngrediente.getText().trim());
				TIngrediente ingrediente = new TIngrediente();
				ingrediente.setID(id);
				ingrediente.setActivo(false);

				Context contexto = new Context(Evento.BAJA_INGREDIENTE, ingrediente);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
			}
		});

		int y = 0;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelIngrediente, gbc);
		gbc.gridx = 1;
		panel.add(idIngrediente, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(baja, gbc);

		add(panel, BorderLayout.CENTER);
	}
}
