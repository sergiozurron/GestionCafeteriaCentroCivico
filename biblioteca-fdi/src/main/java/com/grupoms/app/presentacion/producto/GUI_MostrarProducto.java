package com.grupoms.app.presentacion.producto;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.producto.TBebida;
import com.grupoms.app.negocio.producto.TComida;
import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarProducto extends JFrame implements IGUI {

	private JTextField idProducto;
	private JButton mostrar;

	public GUI_MostrarProducto() {
		super("Mostrar Producto");
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

		mostrar = new JButton("Mostrar Producto");
		mostrar.addActionListener(e -> mostrarProducto());
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel("ID del Producto: "), gbc);

		gbc.gridx = 1;
		idProducto = new JTextField(10);
		panel.add(idProducto, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(mostrar, gbc);


		add(panel, BorderLayout.CENTER);
	}

	private void mostrarProducto() {

		int id;

		try {
			id = Integer.parseInt(idProducto.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Context contexto = new Context(Evento.MOSTRAR_PRODUCTO, id);
		Controlador.getInstance().handle(contexto);
	}

	@Override
	public void actualizar(Context context) {

    	if (context == null) {
       		setVisible(true);
        	return;
    	}

    	if (context.getEvento() == Evento.MOSTRAR_PRODUCTO_OK) {

        	TProducto producto = (TProducto) context.getDatos();

        	StringBuilder info = new StringBuilder();
        	info.append("ID: ").append(producto.getId()).append("\n")
            	.append("Nombre: ").append(producto.getNombre()).append("\n")
            	.append("Precio: ").append(producto.getPrecio()).append("\n")
            	.append("Stock: ").append(producto.getStock()).append("\n")
            	.append("Activo: ").append(producto.getActivo() ? "Sí" : "No");

        	if (producto instanceof TBebida) {

				TBebida bebida = (TBebida) producto;
				info.append("\nTipo: Bebida");
				info.append("\nTamaño: ").append(bebida.getTamanho());

			} else if (producto instanceof TComida) {

				TComida comida = (TComida) producto;
				info.append("\nTipo: Comida");
				info.append("\nTiempo de Preparación: ")
					.append(comida.getTiempoPreparacion());
				info.append("\nCalorías: ")
					.append(comida.getCalorias());
			}

			JOptionPane.showMessageDialog(
				this,
				info.toString(),
				"Información del Producto",
				JOptionPane.INFORMATION_MESSAGE
			);
			
			this.idProducto.setText("");

		} else if (context.getEvento() == Evento.MOSTRAR_PRODUCTO_KO) {
			String mensaje = (String) context.getDatos();
		    JOptionPane.showMessageDialog(this, mensaje);
		}
	}

}
