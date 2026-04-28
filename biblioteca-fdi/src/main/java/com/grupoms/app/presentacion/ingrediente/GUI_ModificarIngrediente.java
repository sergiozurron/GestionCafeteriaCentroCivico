package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarIngrediente extends JFrame implements IGUI {
	private JTextField nombre;
	private JTextField id;
	private JTextField precio;
	private JTextField prov;

	private JButton crear;

	public GUI_ModificarIngrediente() {
		super("Modificar Ingrediente");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
		} else if (context.getEvento() == Evento.MODIFICAR_INGREDIENTE_OK) {

			JOptionPane.showMessageDialog(this, "Ingrediente modificado con éxito");

			id.setText("");
			nombre.setText("");
			precio.setText("");
			prov.setText("");
			dispose();
		} else if (context.getEvento() == Evento.MODIFICAR_INGREDIENTE_KO) {
			String mensaje = (String) context.getDatos();
		    JOptionPane.showMessageDialog(this, mensaje);
		}
	}

	public void initGUI() {
		setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelId = new JLabel("ID Ingrediente:");
		id = new JTextField(10);
		JLabel labelNombre = new JLabel("Nombre Ingrediente:");
		nombre = new JTextField(10);

		JLabel labelPrecio = new JLabel("Precio:");
		precio = new JTextField(10);

		JLabel labelProv = new JLabel("ID proveedor:");
		prov = new JTextField(10);

		crear = new JButton("Modificar Ingrediente");
		crear.addActionListener(e -> {
			try {
				String nombrerI = nombre.getText();
				Double precioI = Double.parseDouble(precio.getText());
				Integer provI = Integer.parseInt(prov.getText());
				Integer idi = Integer.parseInt(id.getText());

				TIngrediente ingr = new TIngrediente();
				ingr.setNombre(nombrerI);
				ingr.setPrecio(precioI);
				ingr.setIDProveedor(provI);
				ingr.setID(idi);

				Context contexto = new Context(Evento.MODIFICAR_INGREDIENTE, ingr);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(id, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelNombre, gbc);
		gbc.gridx = 1;
		panel.add(nombre, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelPrecio, gbc);
		gbc.gridx = 1;
		panel.add(precio, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(labelProv, gbc);
		gbc.gridx = 1;
		panel.add(prov, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		panel.add(crear, gbc);

		add(panel, BorderLayout.CENTER);
	}

}
