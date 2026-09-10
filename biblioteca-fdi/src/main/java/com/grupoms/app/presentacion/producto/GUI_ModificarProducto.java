package com.grupoms.app.presentacion.producto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

import com.grupoms.app.negocio.producto.TBebida;
import com.grupoms.app.negocio.producto.TComida;
import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarProducto extends JFrame implements IGUI {

	private JTextField campoId;
	private JTextField campoNombre;
	private JTextField campoPrecio;
	private JTextField campoStock;
	private JRadioButton bebidaButton;
	private JRadioButton comidaButton;
	private JPanel panelBebida;
	private JPanel panelComida;
	private JTextField campoTamanho;
	private JTextField campoTiempoPreparacion;
	private JTextField campoCalorias;
	private JButton modificar;

	public GUI_ModificarProducto() {
		super("Modificar Producto");
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

		JLabel labelId = new JLabel("ID:");
		campoId = new JTextField(10);

		JLabel labelNombre = new JLabel("Nombre:");
		campoNombre = new JTextField(10);

		JLabel labelPrecio = new JLabel("Precio:");
		campoPrecio = new JTextField(10);

		JLabel labelStock = new JLabel("Stock:");
		campoStock = new JTextField(10);

		bebidaButton = new JRadioButton("Bebida");
		comidaButton = new JRadioButton("Comida");
		ButtonGroup tipoGroup = new ButtonGroup();
		tipoGroup.add(bebidaButton);
		tipoGroup.add(comidaButton);

		panelBebida = new JPanel(new GridBagLayout());
		JLabel labelTamanho = new JLabel("Tamaño (ml):");
		campoTamanho = new JTextField(10);
		GridBagConstraints gbcBebida = new GridBagConstraints();
		gbcBebida.insets = new Insets(5, 5, 5, 5);
		gbcBebida.fill = GridBagConstraints.HORIZONTAL;
		gbcBebida.gridx = 0;
		gbcBebida.gridy = 0;
		panelBebida.add(labelTamanho, gbcBebida);
		gbcBebida.gridx = 1;
		panelBebida.add(campoTamanho, gbcBebida);
		panelBebida.setVisible(false);

		panelComida = new JPanel(new GridBagLayout());
		JLabel labelTiempoPreparacion = new JLabel("Tiempo prep. (min):");
		campoTiempoPreparacion = new JTextField(10);
		JLabel labelCalorias = new JLabel("Calorías:");
		campoCalorias = new JTextField(10);
		GridBagConstraints gbcComida = new GridBagConstraints();
		gbcComida.insets = new Insets(5, 5, 5, 5);
		gbcComida.fill = GridBagConstraints.HORIZONTAL;
		gbcComida.gridx = 0;
		gbcComida.gridy = 0;
		panelComida.add(labelTiempoPreparacion, gbcComida);
		gbcComida.gridx = 1;
		panelComida.add(campoTiempoPreparacion, gbcComida);
		gbcComida.gridx = 0;
		gbcComida.gridy = 1;
		panelComida.add(labelCalorias, gbcComida);
		gbcComida.gridx = 1;
		panelComida.add(campoCalorias, gbcComida);
		panelComida.setVisible(false);

		bebidaButton.addItemListener(e -> {
			panelBebida.setVisible(e.getStateChange() == ItemEvent.SELECTED);
			panelComida.setVisible(false);
			pack();
		});

		comidaButton.addItemListener(e -> {
			panelComida.setVisible(e.getStateChange() == ItemEvent.SELECTED);
			panelBebida.setVisible(false);
			pack();
		});

		modificar = new JButton("Modificar Producto");
		modificar.addActionListener(e -> modificarProducto());

		int y = 0;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoId, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelNombre, gbc);
		gbc.gridx = 1;
		panel.add(campoNombre, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelPrecio, gbc);
		gbc.gridx = 1;
		panel.add(campoPrecio, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelStock, gbc);
		gbc.gridx = 1;
		panel.add(campoStock, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(bebidaButton, gbc);
		gbc.gridx = 1;
		panel.add(comidaButton, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(panelBebida, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(panelComida, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(modificar, gbc);

		add(panel, BorderLayout.CENTER);
	}

	private void modificarProducto() {
		try {
			int id = Integer.parseInt(campoId.getText().trim());
			String nombre = campoNombre.getText().trim();
			double precio = Double.parseDouble(campoPrecio.getText().trim());
			int stock = Integer.parseInt(campoStock.getText().trim());

			TProducto producto;
			if (bebidaButton.isSelected()) {
				TBebida bebida = new TBebida();
				bebida.setTamanho(Integer.parseInt(campoTamanho.getText().trim()));
				producto = bebida;
			} else if (comidaButton.isSelected()) {
				TComida comida = new TComida();
				comida.setTiempoPreparacion(Integer.parseInt(campoTiempoPreparacion.getText().trim()));
				comida.setCalorias(Integer.parseInt(campoCalorias.getText().trim()));
				producto = comida;
			} else {
				JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de producto");
				return;
			}

			producto.setId(id);
			producto.setNombre(nombre);
			producto.setPrecio(precio);
			producto.setStock(stock);
			producto.setActivo(true);

			Context contexto = new Context(Evento.MODIFICAR_PRODUCTO, producto);
			Controlador.getInstance().handle(contexto);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
		} else if (context.getEvento() == Evento.MODIFICAR_PRODUCTO_OK) {
			JOptionPane.showMessageDialog(this, "Producto modificado con éxito");
			limpiarCampos();
		} else if (context.getEvento() == Evento.MODIFICAR_PRODUCTO_KO) {
			String mensaje = (String) context.getDatos();
		    JOptionPane.showMessageDialog(this, mensaje);
		}
	}

	private void limpiarCampos() {
		campoId.setText("");
		campoNombre.setText("");
		campoPrecio.setText("");
		campoStock.setText("");
		campoTamanho.setText("");
		campoTiempoPreparacion.setText("");
		campoCalorias.setText("");
		bebidaButton.setSelected(false);
		comidaButton.setSelected(false);
		panelBebida.setVisible(false);
		panelComida.setVisible(false);
	}
}
