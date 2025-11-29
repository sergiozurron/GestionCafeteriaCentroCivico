package com.grupoms.app.presentacion.claseJPA;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarClase extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextArea areaResultado;
	private JButton btnListar;

	public GUI_ListarClase() {
		setTitle("Listar Clases");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		JPanel panelSuperior = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);

		btnListar = new JButton("Listar Clases");
		btnListar.addActionListener(e -> {
			Context contexto = new Context(Evento.LISTAR_CLASES, null);
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
		case Evento.LISTAR_CLASES_OK:
			@SuppressWarnings("unchecked")
			List<TClase> lista = (List<TClase>) context.getDatos();
			if (lista == null || lista.isEmpty()) {
				areaResultado.setText("");
				JOptionPane.showMessageDialog(this, "No hay clases registradas");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				StringBuilder sb = new StringBuilder();
				for (TClase c : lista) {
					sb.append("ID: ").append(c.getId()).append(" | ");
					sb.append("Tipo: ").append(c.getTipo()).append(" | ");
					sb.append("Fecha: ")
							.append(c.getFechaInicio() != null ? sdf.format(c.getFechaInicio()) : "N/A")
							.append(" | ");
					sb.append("Duración: ").append(c.getDuracion()).append(" min | ");
					sb.append("Activa: ").append(c.getActivo() != null && c.getActivo() ? "Sí" : "No");
					sb.append("\n");
				}
				areaResultado.setText(sb.toString());
			}
			break;
		case Evento.LISTAR_CLASES_KO:
			areaResultado.setText("");
			JOptionPane.showMessageDialog(this, "Error al listar las clases", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}

}
