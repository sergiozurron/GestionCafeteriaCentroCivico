package com.grupoms.app.presentacion.ejemplarJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarEjemplaresMaterial extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoID;
	private JButton mostrar;

	private JTable tabla;
	private DefaultTableModel modeloTabla;

	public GUI_ListarEjemplaresMaterial() {
		super("Mostrar Ejemplares de Material");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	private void initGUI() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelID = new JLabel("ID Material:");
		campoID = new JTextField(10);

		mostrar = new JButton("Mostrar Ejemplares");
		mostrar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(campoID.getText());
				Context contexto = new Context(Evento.MOSTRAR_EJEMPLARMATERIAL, id);
				Controlador.getInstance().handle(contexto);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID debe ser numérico", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		modeloTabla = new DefaultTableModel(new String[] { "ID Ejemplar", "Estado" }, 0);
		tabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tabla);

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelID, gbc);
		gbc.gridx = 1;
		panel.add(campoID, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		panel.add(mostrar, gbc);

		add(panel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		if (context == null) {
			modeloTabla.setRowCount(0);
			setVisible(true);
			return;
		}

		switch (context.getEvento()) {
		case Evento.MOSTRAR_EJEMPLARMATERIAL_OK:
			modeloTabla.setRowCount(0);
			List<TEjemplar> ejemplares = (List<TEjemplar>) context.getDatos();
			if (ejemplares != null && !ejemplares.isEmpty()) {
				for (TEjemplar ej : ejemplares) {
					modeloTabla.addRow(new Object[] { ej.getId(), ej.getEstado() });
				}
			} else {
				JOptionPane.showMessageDialog(this, "No hay ejemplares asociados a ese material", "Información",
						JOptionPane.INFORMATION_MESSAGE);
			}
			break;
		case Evento.MOSTRAR_EJEMPLARMATERIAL_KO:
			modeloTabla.setRowCount(0);
			JOptionPane.showMessageDialog(this, "Material no encontrado o sin ejemplares", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			break;
		}
		campoID.setText("");

	}
}
