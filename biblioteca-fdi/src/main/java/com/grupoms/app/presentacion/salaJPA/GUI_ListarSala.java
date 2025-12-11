package com.grupoms.app.presentacion.salaJPA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.util.List;

public class GUI_ListarSala extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton botonCargar;

	public GUI_ListarSala() {
		setTitle("Listado de Salas");

		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Nombre");
		modeloTabla.addColumn("Capacidad");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		botonCargar = new JButton("Cargar Salas");
		botonCargar.addActionListener(e -> {
			try {

				Context contexto = new Context(Evento.LISTAR_SALA, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar salas: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		panelPrincipal.add(scrollPane, BorderLayout.CENTER);
		panelPrincipal.add(botonCargar, BorderLayout.SOUTH);
		add(panelPrincipal);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}

		if (context.getEvento() == Evento.LISTAR_SALAS_OK) {
			modeloTabla.setRowCount(0);
			List<TSala> salas = (List<TSala>) context.getDatos();

			if (salas == null || salas.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay salas en la base de datos.");
				return;
			}

			for (TSala s : salas) {
				modeloTabla.addRow(new Object[] { s.getId(), s.getNombre(), s.getCapacidad()

				});
			}
		} else if (context.getEvento() == Evento.LISTAR_SALAS_KO) {
			JOptionPane.showMessageDialog(this, "Error al cargar las salas.");
		}
	}
}