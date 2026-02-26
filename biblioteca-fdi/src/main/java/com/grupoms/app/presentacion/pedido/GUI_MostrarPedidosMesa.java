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

public class GUI_MostrarPedidosMesa extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton botonCargar;
	private JTextField campoIdMesa;

	public GUI_MostrarPedidosMesa() {
		setTitle("Pedidos de Mesa");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {

		JPanel panelPrincipal = new JPanel(new BorderLayout());

		// Panel superior para ingresar ID mesa
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelSuperior.add(new JLabel("ID Mesa:"));
		campoIdMesa = new JTextField(10);
		panelSuperior.add(campoIdMesa);

		botonCargar = new JButton("Cargar Pedidos");
		botonCargar.addActionListener(e -> cargarPedidosMesa());
		panelSuperior.add(botonCargar);

		// Tabla
		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Empleado ID");
		modeloTabla.addColumn("Fecha");
		modeloTabla.addColumn("Estado");
		modeloTabla.addColumn("Total Factura");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelPrincipal.add(scrollPane, BorderLayout.CENTER);

		add(panelPrincipal);
	}

	private void cargarPedidosMesa() {
		try {
			int idMesa = Integer.parseInt(campoIdMesa.getText());

			Context contexto = new Context(Evento.MOSTRAR_PEDIDOS_MESA, idMesa);
			Controlador.getInstance().handle(contexto);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El ID de mesa debe ser numérico", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actualizar(Context context) {

		if (context.getEvento() == Evento.MOSTRAR_PEDIDOS_MESA_OK) {

			modeloTabla.setRowCount(0);
			List<TPedido> pedidos = (List<TPedido>) context.getDatos();

			if (pedidos == null || pedidos.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay pedidos para esta mesa.", "Información",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (TPedido pedido : pedidos) {
				Object[] fila = {
						pedido.getId() != null ? pedido.getId() : "N/A",
						pedido.getIdEmpleado() != null ? pedido.getIdEmpleado() : "N/A",
						pedido.getFecha() != null ? pedido.getFecha() : "N/A",
						pedido.getEstado() != null ? pedido.getEstado() : "N/A",
						pedido.getTotal() != null ? pedido.getTotal() : 0.0
				};
				modeloTabla.addRow(fila);
			}

		} else if (context.getEvento() == Evento.MOSTRAR_PEDIDOS_MESA_KO) {
			JOptionPane.showMessageDialog(this, "Error al cargar los pedidos de la mesa.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}