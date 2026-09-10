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

public class GUI_AltaProducto extends JFrame implements IGUI {

	private JTextField campoNombre, campoPrecio, campoStock;
	private JRadioButton bebidaButton, comidaButton;
	private JButton crear;
	private JPanel panelBebida, panelComida;
	private JLabel labelTamanho, labelTiempoPreparacion, labelCalorias;
	private JTextField campoTamanho, campoTiempoPreparacion, campoCalorias;

	public GUI_AltaProducto() {
		super("Alta Producto");
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

		JLabel labelNombre = new JLabel("Nombre:");
		campoNombre = new JTextField(10);

		JLabel labelPrecio = new JLabel("Precio:");
		campoPrecio = new JTextField(10);

		JLabel labelStock = new JLabel("Stock:");
		campoStock = new JTextField(10);

		bebidaButton = new JRadioButton("Bebida");
		comidaButton = new JRadioButton("Comida");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(bebidaButton);
		grupoTipo.add(comidaButton);

		panelBebida = new JPanel(new GridBagLayout());
		labelTamanho = new JLabel("Tamaño:");
		campoTamanho = new JTextField(10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelBebida.add(labelTamanho, gbc);
		gbc.gridx = 1;
		panelBebida.add(campoTamanho, gbc);
		panelBebida.setVisible(false);

		panelComida = new JPanel(new GridBagLayout());
		labelTiempoPreparacion = new JLabel("Tiempo de preparación:");
		campoTiempoPreparacion = new JTextField(10);
		labelCalorias = new JLabel("Calorías:");
		campoCalorias = new JTextField(10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelComida.add(labelTiempoPreparacion, gbc);
		gbc.gridx = 1;
		panelComida.add(campoTiempoPreparacion, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelComida.add(labelCalorias, gbc);
		gbc.gridx = 1;
		panelComida.add(campoCalorias, gbc);
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

		crear = new JButton("Crear Producto");
		crear.addActionListener(e -> crearProducto());

		int y = 0;
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
		panel.add(crear, gbc);

		add(panel, BorderLayout.CENTER);
	}

	private void crearProducto() {
		try {
			String nombre = campoNombre.getText();
			double precio = Double.parseDouble(campoPrecio.getText());
			int stock = Integer.parseInt(campoStock.getText());

			TProducto producto;
			if (bebidaButton.isSelected()) {
				TBebida bebida = new TBebida();
				bebida.setTamanho(Integer.parseInt(campoTamanho.getText()));
				producto = bebida;
			} else {
				TComida comida = new TComida();
				comida.setTiempoPreparacion(Integer.parseInt(campoTiempoPreparacion.getText()));
				comida.setCalorias(Integer.parseInt(campoCalorias.getText()));
				producto = comida;
			}

			producto.setNombre(nombre);
			producto.setPrecio(precio);
			producto.setStock(stock);

			Context contexto = new Context(Evento.ALTA_PRODUCTO, producto);
			Controlador.getInstance().handle(contexto);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
		}
	}

	@Override
	public void actualizar(Context context) {
		if (context == null)
			setVisible(true);
		else if (context.getEvento() == Evento.ALTA_PRODUCTO_OK) {
			JOptionPane.showMessageDialog(this, "Producto creado con éxito");
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
		} else if (context.getEvento() == Evento.ALTA_PRODUCTO_KO) {
			String mensaje = (String) context.getDatos();
		    JOptionPane.showMessageDialog(this, mensaje);
		}
	}
}
