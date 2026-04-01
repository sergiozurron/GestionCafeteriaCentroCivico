package com.grupoms.app.presentacion.mesa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.negocio.mesa.TMesaTerraza;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarMesa extends JFrame implements IGUI {

	private JTextField numero, ubicacion, capacidad, id;
	private JRadioButton rbtnSala, rbtnTerraza;
	private JPanel panelSala, panelTerraza;
	private JCheckBox salaReservada, terrazaCubierta;
	private JLabel lblPrivacidad, lblSuplemento;
	private JTextField salaPrivacidad, terrazaSuplemento;
	private JButton modificar;
	private ButtonGroup grupoTipo;

	public GUI_ModificarMesa() {
		super("Modificar Mesa");
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
			if (context.getEvento() == Evento.MODIFICAR_MESA_OK) {
				JOptionPane.showMessageDialog(this, "Mesa modificada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				
				dispose();
			} else if (context.getEvento() == Evento.MODIFICAR_MESA_KO) {
				
				String mensaje = context.getDatos() != null ? context.getDatos().toString() : "Error al modificar la mesa";
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

		JLabel labelid = new JLabel("ID Mesa:");
		id = new JTextField(10);

		JLabel labelNumero = new JLabel("Número:");
		numero = new JTextField(10);
		JLabel labelUbicacion = new JLabel("Ubicación:");
		ubicacion = new JTextField(10);
		JLabel labelCapacidad = new JLabel("Capacidad:");
		capacidad = new JTextField(10);

		rbtnSala = new JRadioButton("Sala");
		rbtnTerraza = new JRadioButton("Terraza");
		grupoTipo = new ButtonGroup();
		grupoTipo.add(rbtnSala);
		grupoTipo.add(rbtnTerraza);

		panelSala = new JPanel(new GridBagLayout());
		salaReservada = new JCheckBox("Reservada");
		lblPrivacidad = new JLabel("Nivel privacidad:");
		salaPrivacidad = new JTextField(10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelSala.add(salaReservada, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelSala.add(lblPrivacidad, gbc);
		gbc.gridx = 1;
		panelSala.add(salaPrivacidad, gbc);
		panelSala.setVisible(false);

		panelTerraza = new JPanel(new GridBagLayout());
		terrazaCubierta = new JCheckBox("Cubierta");
		lblSuplemento = new JLabel("Suplemento:");
		terrazaSuplemento = new JTextField(10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTerraza.add(terrazaCubierta, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelTerraza.add(lblSuplemento, gbc);
		gbc.gridx = 1;
		panelTerraza.add(terrazaSuplemento, gbc);
		panelTerraza.setVisible(false);

		rbtnSala.addItemListener(e -> {
			panelSala.setVisible(e.getStateChange() == ItemEvent.SELECTED);
			panelTerraza.setVisible(false);
			pack();
		});

		rbtnTerraza.addItemListener(e -> {
			panelTerraza.setVisible(e.getStateChange() == ItemEvent.SELECTED);
			panelSala.setVisible(false);
			pack();
		});

		modificar = new JButton("Modificar Mesa");
		modificar.addActionListener(e -> {
			try {
				TMesa mesa;
				if (rbtnTerraza.isSelected()) {
					mesa = new TMesaTerraza();
				} else if (rbtnSala.isSelected()) {
					mesa = new TMesaSala();
				} else {
					JOptionPane.showMessageDialog(this, "Debes seleccionar un tipo de mesa (Sala o Terraza)", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				mesa.setId(Integer.parseInt(id.getText().trim()));
				mesa.setNumero(Integer.parseInt(numero.getText().trim()));
				mesa.setUbicacion(ubicacion.getText().trim());
				mesa.setCapacidad(Integer.parseInt(capacidad.getText().trim()));
				mesa.setActivo(true);

				Context contexto = null;
				if (rbtnSala.isSelected()) {
					TMesaSala mesaS = (TMesaSala) mesa;
					mesaS.setReservada(salaReservada.isSelected());
					mesaS.setPrivacidad(salaPrivacidad.getText().trim());
					contexto = new Context(Evento.MODIFICAR_MESA, mesaS);
				} else if (rbtnTerraza.isSelected()) {
					TMesaTerraza mesaT = (TMesaTerraza) mesa;
					mesaT.setCubierta(terrazaCubierta.isSelected());
					mesaT.setSuplemento(Double.parseDouble(terrazaSuplemento.getText().trim()));
					contexto = new Context(Evento.MODIFICAR_MESA, mesaT);
				}

				try {
					
					Controlador.getInstance().handle(contexto);
				} catch (Exception ex) {
					
					
					actualizar(new Context(Evento.MODIFICAR_MESA_KO, ex.getMessage()));
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		int y = 0;

		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelid, gbc);
		gbc.gridx = 1;
		panel.add(id, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelNumero, gbc);
		gbc.gridx = 1;
		panel.add(numero, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelUbicacion, gbc);
		gbc.gridx = 1;
		panel.add(ubicacion, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(labelCapacidad, gbc);
		gbc.gridx = 1;
		panel.add(capacidad, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(rbtnSala, gbc);
		gbc.gridx = 1;
		panel.add(rbtnTerraza, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(panelSala, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(panelTerraza, gbc);

		y++;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panel.add(modificar, gbc);

		add(panel, BorderLayout.CENTER);
	}

	private void limpiarCampos() {
		id.setText("");
		numero.setText("");
		ubicacion.setText("");
		capacidad.setText("");
		salaReservada.setSelected(false);
		salaPrivacidad.setText("");
		terrazaCubierta.setSelected(false);
		terrazaSuplemento.setText("");

		if (grupoTipo != null) {
			grupoTipo.clearSelection();
		}

		panelSala.setVisible(false);
		panelTerraza.setVisible(false);
		pack();
	}
}