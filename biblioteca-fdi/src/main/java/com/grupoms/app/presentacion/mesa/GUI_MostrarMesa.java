package com.grupoms.app.presentacion.mesa;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.negocio.mesa.TMesaTerraza;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarMesa extends JFrame implements IGUI {

	private JTextField idMesa;
	private JButton mostrar;
	private JTable tablaMesa;
	private DefaultTableModel modeloTabla;

	public GUI_MostrarMesa() {
		super("Mostrar Mesa");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				limpiarVista();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				limpiarVista();
			}
		});
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			limpiarVista();
			setVisible(true);
			return;
		}
		
		SwingUtilities.invokeLater(() -> {
			if (context.getEvento() == Evento.MOSTRAR_MESA_OK) {
				TMesa mesa = (TMesa) context.getDatos();
				if (mesa != null) {
					actualizarTabla(mesa);
				} else {
					modeloTabla.setRowCount(0);
					JOptionPane.showMessageDialog(this, "No hay mesas activas con ese ID.", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			} else if (context.getEvento() == Evento.MOSTRAR_MESA_KO) {
				modeloTabla.setRowCount(0);

				String mensajeError = (context.getDatos() instanceof String) 
										? (String) context.getDatos() 
										: "No hay mesas activas con ese ID.";
				
				JOptionPane.showMessageDialog(this, mensajeError, "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		});
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		JPanel panelSuperior = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelMesa = new JLabel("ID Mesa:");
		idMesa = new JTextField(10);

		mostrar = new JButton("Mostrar Mesa");
		mostrar.addActionListener(e -> {
			try {
				Integer id = Integer.parseInt(idMesa.getText().trim());
				
				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0");
					return;
				}

				Context contexto = new Context(Evento.MOSTRAR_MESA, id);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: el ID debe ser un número válido");
			}
		});

		int y = 0;
		gbc.gridx = 0;
		gbc.gridy = y;
		panelSuperior.add(labelMesa, gbc);
		gbc.gridx = 1;
		panelSuperior.add(idMesa, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panelSuperior.add(mostrar, gbc);

		add(panelSuperior, BorderLayout.NORTH);

		String[] columnas = { "Tipo", "ID", "Ubicación", "Número", "Capacidad", "Reservada", "Privacidad", "Cubierta",
				"Suplemento" };

		modeloTabla = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaMesa = new JTable(modeloTabla);
		JScrollPane scroll = new JScrollPane(tablaMesa);
		add(scroll, BorderLayout.CENTER);
	}

	private void actualizarTabla(TMesa mesa) {
		modeloTabla.setRowCount(0);

		if (mesa != null) {
			
			String tipo = "Desconocido";
			if (mesa instanceof TMesaSala) {
				tipo = "Sala";
			} else if (mesa instanceof TMesaTerraza) {
				tipo = "Terraza";
			}
			
			String id = String.valueOf(mesa.getId());
			String ubicacion = mesa.getUbicacion() != null ? mesa.getUbicacion() : "N/A";
			String numero = String.valueOf(mesa.getNumero());
			String capacidad = String.valueOf(mesa.getCapacidad());

			String reservada = "N/A";
			String cubierta = "N/A";
			String privacidad = "N/A";
			String suplemento = "N/A";

			if (mesa instanceof TMesaSala) {
				TMesaSala sala = (TMesaSala) mesa;
				reservada = (sala.getReservada() != null && sala.getReservada()) ? "Sí" : "No";
				privacidad = sala.getPrivacidad() != null ? sala.getPrivacidad() : "N/A";
			} else if (mesa instanceof TMesaTerraza) {
				TMesaTerraza terraza = (TMesaTerraza) mesa;
				cubierta = (terraza.getCubierta() != null && terraza.getCubierta()) ? "Sí" : "No";
				suplemento = terraza.getSuplemento() != null ? String.valueOf(terraza.getSuplemento()) + " €" : "N/A";
			}

			Object[] fila = { tipo, id, ubicacion, numero, capacidad, reservada, privacidad, cubierta, suplemento };

			modeloTabla.addRow(fila);
		}
	}

	private void limpiarVista() {
		if (idMesa != null) {
			idMesa.setText("");
		}
		if (modeloTabla != null) {
			modeloTabla.setRowCount(0);
		}
	}
}