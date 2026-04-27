package com.grupoms.app.presentacion.empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarEmpleado extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton botonCargar;

	public GUI_ListarEmpleado() {
		setTitle("Listado de Empleados");
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
		modeloTabla.addColumn("Donde Atiende");
		modeloTabla.addColumn("Sueldo");
		modeloTabla.addColumn("Activo");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		botonCargar = new JButton("Cargar Empleados");
		botonCargar.addActionListener(e -> {
			try {
				Context contexto = new Context(Evento.MOSTRAR_EMPLEADOS, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + ex.getMessage(), "Error",
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
		SwingUtilities.invokeLater(() -> {
			if (context == null) {
				setVisible(true);
			} else if (context.getEvento() == Evento.MOSTRAR_EMPLEADOS_OK) {
				modeloTabla.setRowCount(0);
				List<TEmpleado> empleados = (List<TEmpleado>) context.getDatos();

				if (empleados == null || empleados.isEmpty()) {
					JOptionPane.showMessageDialog(this, "No hay empleados activos en la base de datos.");
					return;
				}

				for (TEmpleado emp : empleados) {
					if (emp != null) {
						Object[] fila = { emp.getID() != null ? emp.getID() : 0,
								emp.getNombre() != null ? emp.getNombre() : "N/A",
								emp.getDondeAtiende() != null ? emp.getDondeAtiende() : "N/A",
								emp.getSueldo() != null ? emp.getSueldo() : 0.0,
								(emp.getActivo() != null && emp.getActivo()) ? "Sí" : "No" };
						modeloTabla.addRow(fila);
					}
				}
			} else if (context.getEvento() == Evento.MOSTRAR_EMPLEADOS_KO) {
				String mensaje = (String) context.getDatos();
			    JOptionPane.showMessageDialog(this, mensaje);
				modeloTabla.setRowCount(0);
			}
		});
	}
}
