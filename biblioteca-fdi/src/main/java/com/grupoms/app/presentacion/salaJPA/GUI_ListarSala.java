package com.grupoms.app.presentacion.salaJPA;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarSala extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextArea areaResultado;
	private JButton btnListar;

	public GUI_ListarSala() {
		setTitle("Listar Salas");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		JPanel panelSuperior = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);

		btnListar = new JButton("Listar Salas");
		btnListar.addActionListener(e -> {
			Context contexto = new Context(Evento.LISTAR_SALAS, null);
			Controlador.getInstance().handle(contexto);
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelSuperior.add(btnListar, gbc);

		areaResultado = new JTextArea();
		areaResultado.setEditable(false);

		add(panelSuperior, BorderLayout.NORTH);
		add(new JScrollPane(areaResultado), BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.LISTAR_SALAS_OK:
			@SuppressWarnings("unchecked")
			List<TSala> lista = (List<TSala>) context.getDatos();
			if (lista == null || lista.isEmpty()) {
				areaResultado.setText("");
				JOptionPane.showMessageDialog(this, "No hay salas registradas");
			} else {
				StringBuilder sb = new StringBuilder();
				for (TSala s : lista) {
					sb.append("ID: ").append(s.getId()).append(" | ");
					sb.append("Nombre: ").append(s.getNombre()).append(" | ");
					sb.append("Capacidad: ").append(s.getCapacidad()).append(" | ");
					sb.append("Activa: ").append(s.getActivo() != null && s.getActivo() ? "Sí" : "No");
					sb.append("\n");
				}
				areaResultado.setText(sb.toString());
			}
			break;
		case Evento.LISTAR_SALAS_KO:
			areaResultado.setText("");
			JOptionPane.showMessageDialog(this, "Error al listar las salas", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}