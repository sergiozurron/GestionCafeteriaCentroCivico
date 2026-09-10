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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_BajaMesa extends JFrame implements IGUI {

	private JTextField idMesa;
	private JButton baja;

	public GUI_BajaMesa() {
		super("Baja Mesa");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				limpiarCampos();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				limpiarCampos();
			}
		});
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			limpiarCampos();
			setVisible(true);
			return;
		}
		
		SwingUtilities.invokeLater(() -> {
			if (context.getEvento() == Evento.BAJA_MESA_OK) {
				JOptionPane.showMessageDialog(this, "Mesa dada de baja con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				
				dispose();
			} else if (context.getEvento() == Evento.BAJA_MESA_KO) {
				String mensaje = context.getDatos() != null ? context.getDatos().toString() : "Error al dar de baja la mesa";
				JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelmesa = new JLabel("ID Mesa:");
		idMesa = new JTextField(10);

		baja = new JButton("Baja Mesa");
		baja.addActionListener(e -> {
			try {
				Integer id = Integer.parseInt(idMesa.getText().trim());
				
				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0");
					return;
				}
				
				TMesa mesaTransporte = new TMesaSala();
				mesaTransporte.setId(id);

				Context contexto = new Context(Evento.BAJA_MESA, mesaTransporte);
				Controlador.getInstance().handle(contexto);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: el ID debe ser un número válido");
			}
		});

		int y = 0;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelmesa, gbc);
		gbc.gridx = 1;
		panel.add(idMesa, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(baja, gbc);

		add(panel, BorderLayout.CENTER);
	}

	private void limpiarCampos() {
		idMesa.setText("");
	}
}