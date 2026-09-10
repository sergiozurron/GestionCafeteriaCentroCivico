package com.grupoms.app.presentacion.promocionJPA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.PromocionJPA.*;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.util.List;

public class GUI_ListarPromocion extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton botonCargar;

	public GUI_ListarPromocion() {
		setTitle("Listado de Promociones");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Tipo");
		modeloTabla.addColumn("Descuento");
		modeloTabla.addColumn("Activo");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		botonCargar = new JButton("Cargar Promociones");
		botonCargar.addActionListener(e -> {

			try {
				Context contexto = new Context(Evento.LISTAR_PROMOCION, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al listar promociones: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		panelPrincipal.add(botonCargar, BorderLayout.NORTH);

		panelPrincipal.add(scrollPane, BorderLayout.CENTER);
		add(panelPrincipal);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}

		if (context.getEvento() == Evento.LISTAR_PROMOCION_OK) {
			modeloTabla.setRowCount(0);
			List<TPromocion> promociones = (List<TPromocion>) context.getDatos();

			if (promociones == null || promociones.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay promociones en la base de datos.");
				return;
			}

			for (TPromocion p : promociones) {
				modeloTabla
						.addRow(new Object[] { p.getId(), p.getTipo(), p.getDescuento(), p.getActivo() ? "Sí" : "No" });
			}
		} else if (context.getEvento() == Evento.LISTAR_PROMOCION_KO) {
			JOptionPane.showMessageDialog(this, "Error al listar las promociones.");
		}
	}
}
