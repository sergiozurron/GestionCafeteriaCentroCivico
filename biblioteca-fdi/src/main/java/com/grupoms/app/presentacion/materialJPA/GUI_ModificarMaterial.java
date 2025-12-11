package com.grupoms.app.presentacion.materialJPA;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.materialJPA.TLibro;
import com.grupoms.app.negocio.materialJPA.TMaterial;
import com.grupoms.app.negocio.materialJPA.TPintura;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarMaterial extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField id;
	private JTextField nombre;
	private JTextField autor;

	private JTextField isbn;
	private JTextField editorial;

	private JTextField fecha;
	private JTextField numero;

	private JComboBox<String> tipoCombo;

	private JButton modificar;

	public GUI_ModificarMaterial() {
		super("Modificar Material");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
		} else if (context.getEvento() == Evento.MODIFICAR_MATERIAL_OK) {
			JOptionPane.showMessageDialog(this, "Material modificado con éxito");

			id.setText("");
			nombre.setText("");
			autor.setText("");
			isbn.setText("");
			editorial.setText("");
			fecha.setText("");
			numero.setText("");
		} else if (context.getEvento() == Evento.MODIFICAR_MATERIAL_KO) {
			JOptionPane.showMessageDialog(this, "No se ha podido modificar el material");
		}
	}

	public void initGUI() {
		setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelId = new JLabel("ID Material:");
		id = new JTextField(10);

		JLabel labelNombre = new JLabel("Nombre:");
		nombre = new JTextField(10);

		JLabel labelAutor = new JLabel("Autor:");
		autor = new JTextField(10);

		JLabel labelTipo = new JLabel("Tipo:");
		tipoCombo = new JComboBox<>(new String[] { "Libro", "Pintura" });
		tipoCombo.addActionListener(e -> actualizarCamposTipo());

		JLabel labelIsbn = new JLabel("ISBN:");
		isbn = new JTextField(10);
		JLabel labelEditorial = new JLabel("Editorial:");
		editorial = new JTextField(10);

		JLabel labelFecha = new JLabel("Fecha:");
		fecha = new JTextField(10);
		JLabel labelNumero = new JLabel("Número:");
		numero = new JTextField(10);

		modificar = new JButton("Modificar Material");
		modificar.addActionListener(e -> {
			try {
				int idI = Integer.parseInt(id.getText());
				String nombreI = nombre.getText();
				String autorI = autor.getText();

				TMaterial mat;
				if (tipoCombo.getSelectedItem().equals("Libro")) {
					TLibro libro = new TLibro();
					libro.setISBN(Integer.parseInt(isbn.getText()));
					libro.setEditorial(editorial.getText());
					libro.setTipoMaterial(1);
					mat = libro;
				} else {
					TPintura pintura = new TPintura();
					pintura.setFecha(fecha.getText());
					pintura.setNumero(Integer.parseInt(numero.getText()));
					pintura.setTipoMaterial(0);
					mat = pintura;
				}

				mat.setID(idI);
				mat.setNombre(nombreI);
				mat.setAutor(autorI);

				Context contexto = new Context(Evento.MODIFICAR_MATERIAL, mat);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: campos numéricos no válidos");
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
		panel.add(labelAutor, gbc);
		gbc.gridx = 1;
		panel.add(autor, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(labelTipo, gbc);
		gbc.gridx = 1;
		panel.add(tipoCombo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add(labelIsbn, gbc);
		gbc.gridx = 1;
		panel.add(isbn, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		panel.add(labelEditorial, gbc);
		gbc.gridx = 1;
		panel.add(editorial, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		panel.add(labelFecha, gbc);
		gbc.gridx = 1;
		panel.add(fecha, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		panel.add(labelNumero, gbc);
		gbc.gridx = 1;
		panel.add(numero, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		panel.add(modificar, gbc);

		add(panel, BorderLayout.CENTER);

		actualizarCamposTipo();
	}

	private void actualizarCamposTipo() {
		boolean isLibro = tipoCombo.getSelectedItem().equals("Libro");
		isbn.setEnabled(isLibro);
		editorial.setEnabled(isLibro);
		fecha.setEnabled(!isLibro);
		numero.setEnabled(!isLibro);
	}
}
