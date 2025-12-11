package com.grupoms.app.presentacion.materialJPA;

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

import com.grupoms.app.negocio.materialJPA.*;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarMaterial extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoID;
	private JButton mostrar;

	private JLabel nombreLabel;
	private JLabel ISBNLabel;
	private JLabel editorialLabel;
	private JLabel autorLabel;

	public GUI_MostrarMaterial() {
		super("Mostrar Material");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void initGUI() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelID = new JLabel("ID Material:");
		campoID = new JTextField(10);

		mostrar = new JButton("Mostrar Material");
		mostrar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(campoID.getText());
				Context contexto = new Context(Evento.MOSTRAR_MATERIAL, id);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
			}
		});
		nombreLabel = new JLabel();
		ISBNLabel = new JLabel();
		editorialLabel = new JLabel();
		autorLabel = new JLabel();

		int y = 0;

		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelID, gbc);
		gbc.gridx = 1;
		panel.add(campoID, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(mostrar, gbc);

		y++;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Autor:"), gbc);
		gbc.gridx = 1;
		panel.add(autorLabel, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Nombre:"), gbc);
		gbc.gridx = 1;
		panel.add(nombreLabel, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("ISBN/Numero:"), gbc);
		gbc.gridx = 1;
		panel.add(ISBNLabel, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(new JLabel("Editorial/Fecha:"), gbc);
		gbc.gridx = 1;
		panel.add(editorialLabel, gbc);
		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}

		switch (context.getEvento()) {
		case Evento.MOSTRAR_MATERIAL_OK:
			TMaterial m = (TMaterial) context.getDatos();
			if (m != null) {
				nombreLabel.setText(m.getNombre());
				autorLabel.setText(m.getAutor());
				;
				if (m.getTipoMaterial() == 0) {
					TPintura p = (TPintura) m;
					ISBNLabel.setText(String.valueOf(p.getNumero()));
					editorialLabel.setText(p.getFecha());
				} else {
					TLibro l = (TLibro) m;
					ISBNLabel.setText(String.valueOf(l.getISBN()));
					editorialLabel.setText(l.getEditorial());
					;
				}
			}
			break;
		case Evento.MOSTRAR_MATERIAL_KO:
			JOptionPane.showMessageDialog(this, "Material no encontrado en la base de datos");
			ISBNLabel.setText("");
			nombreLabel.setText("");
			editorialLabel.setText("");
			autorLabel.setText("");
			break;
		}

	}

}
