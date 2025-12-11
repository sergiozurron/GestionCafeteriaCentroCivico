package com.grupoms.app.presentacion.claseJPA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class GUI_ListarClase extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton botonCargar;

	public GUI_ListarClase() {
		setTitle("Listado de Clases");
		setSize(750, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Tipo");
		modeloTabla.addColumn("Fecha Inicio");
		modeloTabla.addColumn("Duración (min)");
		modeloTabla.addColumn("ID Sala");
		modeloTabla.addColumn("IDs Ejemplares");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		botonCargar = new JButton("Cargar Clases");
		botonCargar.addActionListener(e -> {
			try {
				Context contexto = new Context(Evento.LISTAR_CLASES, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar clases: " + ex.getMessage(), "Error",
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

		if (context.getEvento() == Evento.LISTAR_CLASES_OK) {
			modeloTabla.setRowCount(0);
			List<TClase> clases = (List<TClase>) context.getDatos();

			if (clases == null || clases.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay clases registradas.");
				return;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			for (TClase c : clases) {
				String fechaStr = (c.getFechaInicio() != null) ? sdf.format(c.getFechaInicio()) : "N/A";

				String ejemplaresStr = "";

				if (c.getEjemplares() != null && !c.getEjemplares().isEmpty()) {

					ejemplaresStr = c.getEjemplares().toString().replace("[", "").replace("]", "");
				}

				modeloTabla.addRow(new Object[] { c.getId(), c.getTipo(), fechaStr, c.getDuracion(), c.getIdSala(),
						ejemplaresStr });
			}
		} else if (context.getEvento() == Evento.LISTAR_CLASES_KO) {
			JOptionPane.showMessageDialog(this, "Error al cargar las clases.");
		}
	}
}