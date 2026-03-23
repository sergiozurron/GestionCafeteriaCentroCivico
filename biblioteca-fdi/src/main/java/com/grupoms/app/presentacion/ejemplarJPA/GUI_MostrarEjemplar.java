package com.grupoms.app.presentacion.ejemplarJPA;

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
import javax.swing.JTextField;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarEjemplar extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoId;
	private JButton btnMostrar;

	private JTextField campoValorId;
	private JTextField campoValorEstado;
	private JTextField campoValorIdMaterial;

	public GUI_MostrarEjemplar() {
		super("Mostrar Ejemplar");
		setSize(300, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelId = new JLabel("ID:");
		campoId = new JTextField(10);

		btnMostrar = new JButton("Mostrar Ejemplar");
		btnMostrar.addActionListener(e -> {
			try {
				Integer id = Integer.parseInt(campoId.getText().trim());

				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0");
					return;
				}

				Context contexto = new Context(Evento.MOSTRAR_EJEMPLAR, id);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: El ID debe ser un número válido");
			}
		});

		campoValorId = new JTextField(15);
		campoValorId.setEditable(false);
		JLabel labelIdValor = new JLabel("ID:");
		labelIdValor.setPreferredSize(campoValorId.getPreferredSize());

		campoValorEstado = new JTextField(15);
		campoValorEstado.setEditable(false);
		JLabel labelEstadoValor = new JLabel("Estado:");
		labelEstadoValor.setPreferredSize(campoValorEstado.getPreferredSize());

		campoValorIdMaterial = new JTextField(15);
		campoValorIdMaterial.setEditable(false);
		JLabel labelIdMaterialValor = new JLabel("ID Material:");
		labelIdMaterialValor.setPreferredSize(campoValorIdMaterial.getPreferredSize());

		int y = 0;

		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelId, gbc);
		gbc.gridx = 1;
		panel.add(campoId, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(btnMostrar, gbc);

		y++;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelIdValor, gbc);
		gbc.gridx = 1;
		panel.add(campoValorId, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelEstadoValor, gbc);
		gbc.gridx = 1;
		panel.add(campoValorEstado, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelIdMaterialValor, gbc);
		gbc.gridx = 1;
		panel.add(campoValorIdMaterial, gbc);

		add(panel, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				campoId.setText("");
				campoValorId.setText("");
				campoValorEstado.setText("");
				campoValorIdMaterial.setText("");
			}
		});
		
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		} else if (context.getEvento() == Evento.MOSTRAR_EJEMPLAR_OK) {
			TEjemplar ejemplar = (TEjemplar) context.getDatos();

			campoValorId.setText(ejemplar.getId() != null ? ejemplar.getId().toString() : "N/A");
			campoValorEstado.setText(ejemplar.getEstado() != null ? ejemplar.getEstado() : "N/A");
			campoValorIdMaterial
					.setText(ejemplar.getIdMaterial() != null ? ejemplar.getIdMaterial().toString() : "N/A");
		} else if (context.getEvento() == Evento.MOSTRAR_EJEMPLAR_KO) {
			JOptionPane.showMessageDialog(this, "Ejemplar no encontrado en la base de datos");
		}
	}

}
