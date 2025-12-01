package com.grupoms.app.presentacion.claseJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_VincularEjemplarClase extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoIdClase;
	private JTextField campoIdEjemplar;
	private JButton btnVincular;

	public GUI_VincularEjemplarClase() {
		setTitle("Vincular Ejemplar a Clase");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelIdClase = new JLabel("ID Clase:");
		campoIdClase = new JTextField(10);

		JLabel labelIdEjemplar = new JLabel("ID Ejemplar:");
		campoIdEjemplar = new JTextField(10);

		btnVincular = new JButton("Vincular");

		btnVincular.addActionListener(e -> {
			String idClaseTexto = campoIdClase.getText().trim();
			String idEjemplarTexto = campoIdEjemplar.getText().trim();

			if (idClaseTexto.isEmpty() || idEjemplarTexto.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Ambos IDs son obligatorios");
				return;
			}

			Integer idClase;
			Integer idEjemplar;
			try {
				idClase = Integer.parseInt(idClaseTexto);
				idEjemplar = Integer.parseInt(idEjemplarTexto);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Los IDs deben ser números válidos");
				return;
			}

			Integer[] params = new Integer[] { idClase, idEjemplar };

			Context contexto = new Context(Evento.VINCULAR_EJEMPLAR_CLASE, params);
			Controlador.getInstance().handle(contexto);
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelIdClase, gbc);
		gbc.gridx = 1;
		panel.add(campoIdClase, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelIdEjemplar, gbc);
		gbc.gridx = 1;
		panel.add(campoIdEjemplar, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(btnVincular, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.VINCULAR_EJEMPLAR_CLASE_OK:
			JOptionPane.showMessageDialog(this, "Ejemplar vinculado correctamente a la clase");
			break;
		case Evento.VINCULAR_EJEMPLAR_CLASE_KO:
			JOptionPane.showMessageDialog(this, "Error al vincular el ejemplar a la clase", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}

}
