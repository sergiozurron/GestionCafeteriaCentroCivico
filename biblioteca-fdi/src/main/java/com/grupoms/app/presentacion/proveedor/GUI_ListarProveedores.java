package com.grupoms.app.presentacion.proveedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarProveedores extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton btnCargar;

	public GUI_ListarProveedores() {
		setTitle("Listado de Proveedores");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Nombre");
		modeloTabla.addColumn("Tarifa");
		modeloTabla.addColumn("Tiempo Entrega (días)");
		modeloTabla.addColumn("Activo");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		btnCargar = new JButton("Cargar Proveedores");
		btnCargar.addActionListener(e -> {
			try {
				Context contexto = new Context(Evento.MOSTRAR_LISTA_PROVEEDOR, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		panelPrincipal.add(scrollPane, BorderLayout.CENTER);
		panelPrincipal.add(btnCargar, BorderLayout.SOUTH);

		add(panelPrincipal);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}

		if (context.getEvento() == Evento.MOSTRAR_LISTA_PROVEEDOR_OK) {
			modeloTabla.setRowCount(0);

			List<TProveedor> proveedores = (List<TProveedor>) context.getDatos();

			if (proveedores == null || proveedores.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay proveedores activos en la base de datos.", "Información",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (TProveedor proveedor : proveedores) {
				Object[] fila = { proveedor.getId(), proveedor.getNombre(),
						String.format("%.2f €", proveedor.getTarifa()), proveedor.getTiempoEntrega(),
						(proveedor.getActivo() != null && proveedor.getActivo()) ? "Sí" : "No" };
				modeloTabla.addRow(fila);
			}
		} else if (context.getEvento() == Evento.MOSTRAR_LISTA_PROVEEDOR_KO) {
			JOptionPane.showMessageDialog(this, "Error al cargar los proveedores.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
