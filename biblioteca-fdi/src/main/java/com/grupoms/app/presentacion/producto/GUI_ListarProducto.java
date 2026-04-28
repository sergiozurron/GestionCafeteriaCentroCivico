package com.grupoms.app.presentacion.producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton botonCargar;

	public GUI_ListarProducto() {
		setTitle("Listado de Productos");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Nombre");
		modeloTabla.addColumn("Precio");
		modeloTabla.addColumn("Stock");
		modeloTabla.addColumn("Tipo");
		modeloTabla.addColumn("Activo");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		botonCargar = new JButton("Cargar Productos");
		botonCargar.addActionListener(e -> {
			try {
				Context contexto = new Context(Evento.MOSTRAR_LISTA_PRODUCTO, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar productos: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		panelPrincipal.add(scrollPane, BorderLayout.CENTER);
		panelPrincipal.add(botonCargar, BorderLayout.SOUTH);

		add(panelPrincipal);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if (context == null)
			setVisible(true);
		else if (context.getEvento() == Evento.MOSTRAR_LISTA_PRODUCTO_OK) {
			modeloTabla.setRowCount(0);
			List<TProducto> productos = (List<TProducto>) context.getDatos();

			if (productos == null || productos.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay productos activos en la base de datos.", "Sin datos",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (TProducto p : productos) {
				Object[] fila;
				if (p.getTipo().equals("Bebida")) {
					fila = new Object[] { p.getId(), p.getNombre(), p.getPrecio(), p.getStock(), p.getTipo(),
							p.getActivo() ? "Sí" : "No", p.getTamanho() };
				} else {
					fila = new Object[] { p.getId(), p.getNombre(), p.getPrecio(), p.getStock(), p.getTipo(),
							p.getActivo() ? "Sí" : "No", p.getTiempoPreparacion(), p.getCalorias() };
				}
				modeloTabla.addRow(fila);
			}
		} else if (context.getEvento() == Evento.MOSTRAR_LISTA_PRODUCTO_KO) {
			String mensaje = (String) context.getDatos();
		    JOptionPane.showMessageDialog(this, mensaje);
		}
	}
}
