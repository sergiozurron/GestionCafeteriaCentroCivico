package com.grupoms.app.presentacion.materialJPA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.materialJPA.*;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.util.List;

public class GUI_ListarMaterial extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton botonCargar;

	public GUI_ListarMaterial() {
		setTitle("Listado de Materiales");
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
		modeloTabla.addColumn("Autor");
		modeloTabla.addColumn("Tipo Material");
		modeloTabla.addColumn("Editorial");
		modeloTabla.addColumn("ISBN");
		modeloTabla.addColumn("Número");
		modeloTabla.addColumn("Fecha");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		botonCargar = new JButton("Cargar Material");
		botonCargar.addActionListener(e -> {
			try {
				Context contexto = new Context(Evento.LISTAR_MATERIAL, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar materiales: " + ex.getMessage(), "Error",
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
		if (context.getEvento() == Evento.LISTAR_MATERIAL_OK) {
			modeloTabla.setRowCount(0);
			List<TMaterial> materiales = (List<TMaterial>) context.getDatos();

			if (materiales == null || materiales.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay materiales en la base de datos.");
				return;
			}

			for (TMaterial m : materiales) {
				if (m instanceof TLibro libro) {
					modeloTabla.addRow(new Object[] { libro.getID(), libro.getNombre(), libro.getAutor(), "Libro",
							libro.getEditorial(), libro.getISBN(), "", "" });
				} else if (m instanceof TPintura pintura) {
					modeloTabla.addRow(new Object[] { pintura.getID(), pintura.getNombre(), pintura.getAutor(),
							"Pintura", "", "", pintura.getNumero(), pintura.getFecha() });
				}
			}
		} else if (context.getEvento() == Evento.LISTAR_MATERIAL_KO) {
			JOptionPane.showMessageDialog(this, "Error al cargar los materiales.");
		}
	}
}
