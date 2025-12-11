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
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.negocio.mesa.TMesaTerraza;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarMesa extends JFrame implements IGUI {

	private JButton baja;
	private List<TMesa> listaMesas;
	private JTable tablaMesas;
	private DefaultTableModel modeloTabla;

	public GUI_ListarMesa() {
		super("Mostrar Lista de Mesas");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);

		Context contexto = new Context(Evento.MOSTRAR_LISTA_MESA, null);
		Controlador.getInstance().handle(contexto);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if (context == null)
			setVisible(true);
		else if (context.getEvento() == Evento.MOSTRAR_LISTA_MESA_OK) {
			listaMesas = (List<TMesa>) context.getDatos();
			actualizarTabla();
		} else if (context.getEvento() == Evento.MOSTRAR_LISTA_MESA_KO) {
			JOptionPane.showMessageDialog(this, "Error al mostrar lista de mesas", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		JPanel panelSuperior = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		baja = new JButton("Cerrar");
		baja.addActionListener(e -> dispose());
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelSuperior.add(baja, gbc);
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

		setVisible(true);
	}

	private void actualizarTabla() {
		modeloTabla.setRowCount(0);

		if (listaMesas != null) {
			for (TMesa mesa : listaMesas) {

				String tipo = mesa.getTipo();
				String id = String.valueOf(mesa.getId());
				String ubicacion = mesa.getUbicacion();
				String numero = String.valueOf(mesa.getNumero());
				String capacidad = String.valueOf(mesa.getCapacidad());

				String reservada = "N/A";
				String cubierta = "N/A";
				String privacidad = "N/A";
				String suplemento = "N/A";

				if (mesa instanceof TMesaSala) {
					TMesaSala sala = (TMesaSala) mesa;
					reservada = sala.getReservada() ? "Reservada" : "Sin reservar";
					privacidad = sala.getPrivacidad();
				} else if (mesa instanceof TMesaTerraza) {
					TMesaTerraza terraza = (TMesaTerraza) mesa;
					cubierta = terraza.getCubierta() ? "Cubierta" : "No cubierta";
					suplemento = String.valueOf(terraza.getSuplemento());
				}

				Object[] fila = { tipo, id, ubicacion, numero, capacidad, reservada, privacidad, cubierta, suplemento };

				modeloTabla.addRow(fila);
			}
		}
	}
}
