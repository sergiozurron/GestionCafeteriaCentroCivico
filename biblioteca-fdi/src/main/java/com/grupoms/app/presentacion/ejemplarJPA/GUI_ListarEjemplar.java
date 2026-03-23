package com.grupoms.app.presentacion.ejemplarJPA;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarEjemplar extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton btnCargar;

	public GUI_ListarEjemplar() {
		setTitle("Listar Ejemplares");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panelPrincipal = new JPanel(new BorderLayout());

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Estado");
		modeloTabla.addColumn("Id Material");

		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		btnCargar = new JButton("Cargar Ejemplares");
		btnCargar.addActionListener(e -> {
			try {
				Context contexto = new Context(Evento.LISTAR_EJEMPLARES, null);
				Controlador.getInstance().handle(contexto);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar ejemplares: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		panelPrincipal.add(scrollPane, BorderLayout.CENTER);
		panelPrincipal.add(btnCargar, BorderLayout.SOUTH);

		add(panelPrincipal);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
				modeloTabla.setRowCount(0);
			}
		});
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		if (context.getEvento() == Evento.LISTAR_EJEMPLARES_OK) {
			modeloTabla.setRowCount(0);

			List<TEjemplar> ejemplares = (List<TEjemplar>) context.getDatos();

			for (TEjemplar ejemplar : ejemplares) {
				Object[] fila = { ejemplar.getId(), ejemplar.getEstado(), ejemplar.getIdMaterial() };
				modeloTabla.addRow(fila);
			}
		}
	}

}
