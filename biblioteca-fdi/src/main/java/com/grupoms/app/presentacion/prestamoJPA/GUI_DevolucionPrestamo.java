package com.grupoms.app.presentacion.prestamoJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.prestamoJPA.PrestamoId;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_DevolucionPrestamo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField campoIdSocio;
	private JTextField campoIdEjemplar;
	private JTextField campoFechaInicio;
	private JButton btnDevolucion;

	public GUI_DevolucionPrestamo() {
		super("Devolución Prestamo");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelIdSocio = new JLabel("ID Socio:");
		campoIdSocio = new JTextField(20);
		
		JLabel labelIdEjemplar = new JLabel("ID Ejemplar:");
		campoIdEjemplar = new JTextField(20);
		
		JLabel labelFechaInicio = new JLabel("Fecha Inicio (YYYY-MM-DD):");
		campoFechaInicio = new JTextField(20);

		btnDevolucion = new JButton("Devolver");
		btnDevolucion.addActionListener(e -> {
			
			int idSocio;
			try {
				idSocio = Integer.parseInt(campoIdSocio.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID Socio debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int idEjemplar;
			try {
				idEjemplar = Integer.parseInt(campoIdEjemplar.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID Ejemplar debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Date fechaInicio;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				fechaInicio = sdf.parse(campoFechaInicio.getText());
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this, "Fecha Inicio debe tener formato YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Context contexto = new Context(Evento.DEVOLUCION_PRESTAMO, new PrestamoId(idSocio, idEjemplar, fechaInicio));
			Controlador.getInstance().handle(contexto);

		});

		int y = 0;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelIdSocio, gbc);
		gbc.gridx = 1;
		panel.add(campoIdSocio, gbc);
		
		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelIdEjemplar, gbc);
		gbc.gridx = 1;
		panel.add(campoIdEjemplar, gbc);
		
		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelFechaInicio, gbc);
		gbc.gridx = 1;
		panel.add(campoFechaInicio, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(btnDevolucion, gbc);

		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.DEVOLUCION_PRESTAMO_OK:
			dispose();
			JOptionPane.showMessageDialog(this, "Préstamo devuelto");
			clearFields();
			break;
		case Evento.DEVOLUCION_PRESTAMO_KO:
			JOptionPane.showMessageDialog(this, "Error al devolver el préstamo", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}

	private void clearFields() {
		campoIdSocio.setText("");
		campoIdEjemplar.setText("");
		campoFechaInicio.setText("");
		
	}

}
