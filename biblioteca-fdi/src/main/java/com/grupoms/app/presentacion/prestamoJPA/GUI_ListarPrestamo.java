package com.grupoms.app.presentacion.prestamoJPA;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.prestamoJPA.TPrestamo;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarPrestamo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton btnCargar;

	public GUI_ListarPrestamo() {
		setTitle("Listar Préstamos");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panelPrincipal = new JPanel(new BorderLayout());

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("ID Ejemplar");
		modeloTabla.addColumn("ID Socio");
		modeloTabla.addColumn("Fecha Inicial");
		modeloTabla.addColumn("Fecha Máxima");
		modeloTabla.addColumn("Fecha Devuelto");
		modeloTabla.addColumn("Precio Multa");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		btnCargar = new JButton("Cargar Préstamos");
		btnCargar.addActionListener(e -> {
			try {
				Context contexto = new Context(Evento.LISTAR_PRESTAMOS, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar préstamos: " + ex.getMessage(), "Error",
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
		if (context.getEvento() == Evento.LISTAR_PRESTAMOS_OK) {
			modeloTabla.setRowCount(0);

			List<TPrestamo> prestamos = (List<TPrestamo>) context.getDatos();

			for (TPrestamo prestamo : prestamos) {
				Object[] fila = { prestamo.getIdEjemplar(), prestamo.getIdSocio(),
						prestamo.getFechaInicial(), prestamo.getFechaMaxima(), prestamo.getFechaDevuelto(),
						prestamo.getPrecioMulta() };
				modeloTabla.addRow(fila);
			}
		}
	}

}
