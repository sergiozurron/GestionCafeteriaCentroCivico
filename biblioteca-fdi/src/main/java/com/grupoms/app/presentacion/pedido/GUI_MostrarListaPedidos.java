package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.util.List;

public class GUI_MostrarListaPedidos extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton botonCargar;

	public GUI_MostrarListaPedidos() {
		setTitle("Listado de Pedidos");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Empleado ID");
		modeloTabla.addColumn("Mesa ID");
		modeloTabla.addColumn("Fecha");
		modeloTabla.addColumn("Estado");
		modeloTabla.addColumn("Total Factura");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		botonCargar = new JButton("Cargar Pedidos");
		botonCargar.addActionListener(e -> {
			try {
				Context contexto = new Context(Evento.MOSTRAR_PEDIDOS, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar pedidos: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		panelPrincipal.add(scrollPane, BorderLayout.CENTER);
		panelPrincipal.add(botonCargar, BorderLayout.SOUTH);

		add(panelPrincipal);
	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.MOSTRAR_PEDIDOS_OK) {
			modeloTabla.setRowCount(0);
			List<TPedido> pedidos = (List<TPedido>) context.getDatos();

			if (pedidos == null || pedidos.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay pedidos en la base de datos.", "Información",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (TPedido pedido : pedidos) {
				Object[] fila = {
						pedido.getId() != null ? pedido.getId() : "N/A",
						pedido.getIdEmpleado() != null ? pedido.getIdEmpleado() : "N/A",
						pedido.getIdMesa() != null ? pedido.getIdMesa() : "N/A",
						pedido.getFecha() != null ? pedido.getFecha() : "N/A",
						pedido.getEstado() != null ? pedido.getEstado() : "N/A",
						pedido.getTotal() != null ? pedido.getTotal() : 0.0
				};
				modeloTabla.addRow(fila);
			}

		} else if (context.getEvento() == Evento.MOSTRAR_PEDIDOS_KO) {
			JOptionPane.showMessageDialog(this, "Error al cargar los pedidos.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}