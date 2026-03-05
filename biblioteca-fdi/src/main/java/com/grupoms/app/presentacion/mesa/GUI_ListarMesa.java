package com.grupoms.app.presentacion.mesa;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.negocio.mesa.TMesaTerraza;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarMesa extends JFrame implements IGUI {

	private JButton btnCerrar;
	private JButton btnCargar;
	private List<TMesa> listaMesas;
	private JTable tablaMesas;
	private DefaultTableModel modeloTabla;

	public GUI_ListarMesa() {
		super("Mostrar Lista de Mesas");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}

		SwingUtilities.invokeLater(() -> {
			if (context.getEvento() == Evento.MOSTRAR_LISTA_MESA_OK) {
				listaMesas = (List<TMesa>) context.getDatos();
				actualizarTabla();
			} else if (context.getEvento() == Evento.MOSTRAR_LISTA_MESA_KO) {
				
				if (modeloTabla != null) {
					modeloTabla.setRowCount(0);
				}
				
				
				String mensaje = context.getDatos() != null ? context.getDatos().toString() : "No hay mesas activas.";
				
				
				JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		JPanel panelSuperior = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		
		btnCargar = new JButton("Cargar Mesas");
		btnCargar.addActionListener(e -> {
			Controlador.getInstance().handle(new Context(Evento.MOSTRAR_LISTA_MESA, null));
		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelSuperior.add(btnCargar, gbc);

		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(e -> dispose());
		gbc.gridx = 1;
		gbc.gridy = 0;
		panelSuperior.add(btnCerrar, gbc);

		add(panelSuperior, BorderLayout.NORTH);

		String[] columnas = { "Tipo", "ID", "Ubicación", "Número", "Capacidad", "Reservada", "Privacidad", "Cubierta",
				"Suplemento" };

		modeloTabla = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaMesas = new JTable(modeloTabla);
		JScrollPane scroll = new JScrollPane(tablaMesas);
		add(scroll, BorderLayout.CENTER);
	}

	private void actualizarTabla() {
		modeloTabla.setRowCount(0);

		if (listaMesas == null || listaMesas.isEmpty()) {
			return; 
		}

		for (TMesa mesa : listaMesas) {
			
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
				suplemento = terraza.getSuplemento() != null ? terraza.getSuplemento() + " €" : "N/A";
			}

			Object[] fila = { tipo, id, ubicacion, numero, capacidad, reservada, privacidad, cubierta, suplemento };
			modeloTabla.addRow(fila);
		}
	}
}