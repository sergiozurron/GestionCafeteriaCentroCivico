package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.util.List;

public class GUI_ListarIngrediente extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton botonCargar;

	public GUI_ListarIngrediente() {
		setTitle("Listado de Ingredientes");
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
		modeloTabla.addColumn("Activo");
		modeloTabla.addColumn("ID Proveedor");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		botonCargar = new JButton("Cargar Ingredientes");
		botonCargar.addActionListener(e -> {
			try {
				Context contexto = new Context(Evento.MOSTRAR_INGREDIENTES, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar ingredientes: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		panelPrincipal.add(scrollPane, BorderLayout.CENTER);
		panelPrincipal.add(botonCargar, BorderLayout.SOUTH);

		add(panelPrincipal);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}

		if (context.getEvento() == Evento.MOSTRAR_INGREDIENTES_OK) {
			modeloTabla.setRowCount(0);
			List<TIngrediente> ingredientes = (List<TIngrediente>) context.getDatos();

			if (ingredientes == null || ingredientes.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay ingredientes activos en la base de datos.");
				return;
			}

			for (TIngrediente ing : ingredientes) {
				Object[] fila = { ing.getID(), ing.getNombre(), ing.getPrecio(), ing.getActivo() ? "Sí" : "No",
						ing.getIDProveedor() };
				modeloTabla.addRow(fila);
			}
		} else if (context.getEvento() == Evento.MOSTRAR_INGREDIENTES_KO) {
			JOptionPane.showMessageDialog(this, "Error al cargar los ingredientes.");
		}

	}
}
