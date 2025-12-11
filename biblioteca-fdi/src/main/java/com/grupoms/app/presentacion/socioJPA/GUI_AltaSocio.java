package com.grupoms.app.presentacion.socioJPA;

import com.grupoms.app.negocio.socioJPA.TAdulto;
import com.grupoms.app.negocio.socioJPA.TInfantil;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class GUI_AltaSocio extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField campoNombreYApellido, campoDni, campoCuota, campoEdad;
	private JLabel campoReduccion;
	private JRadioButton adultoButton, infantilButton, si, no;
	private JButton aceptar;
	private JPanel panelAdulto, panelInfantil;

	public GUI_AltaSocio() {
		super("Alta socio");
		initGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelNombre = new JLabel("Nombre y Apellido: ");
		campoNombreYApellido = new JTextField(20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelNombre, gbc);
		gbc.gridx = 1;
		panel.add(campoNombreYApellido, gbc);

		JLabel labelDNI = new JLabel("DNI: ");
		campoDni = new JTextField(20);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(labelDNI, gbc);
		gbc.gridx = 1;
		panel.add(campoDni, gbc);

		JLabel labelCuota = new JLabel("Cuota: ");
		campoCuota = new JTextField(20);
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(labelCuota, gbc);
		gbc.gridx = 1;
		panel.add(campoCuota, gbc);

		adultoButton = new JRadioButton("Adulto");
		infantilButton = new JRadioButton("Infantil");
		ButtonGroup grupoTipo = new ButtonGroup();
		grupoTipo.add(adultoButton);
		grupoTipo.add(infantilButton);
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(adultoButton, gbc);
		gbc.gridx = 1;
		panel.add(infantilButton, gbc);

		panelAdulto = new JPanel(new GridBagLayout());
		JLabel labelMiembroPleno = new JLabel("Miembro Pleno");
		si = new JRadioButton("Sí");
		no = new JRadioButton("No");
		ButtonGroup miembroPleno = new ButtonGroup();
		miembroPleno.add(si);
		miembroPleno.add(no);
		GridBagConstraints gbcAdulto = new GridBagConstraints();
		gbcAdulto.insets = new Insets(5, 5, 5, 5);
		gbcAdulto.gridx = 0;
		gbcAdulto.gridy = 0;
		panelAdulto.add(labelMiembroPleno, gbcAdulto);
		gbcAdulto.gridx = 0;
		gbcAdulto.gridy = 1;
		panelAdulto.add(si, gbc);
		gbc.gridx = 1;
		panelAdulto.add(no, gbc);
		gbcAdulto.gridx = 0;
		gbcAdulto.gridy = 2;
		panelAdulto.setVisible(false);

		panelInfantil = new JPanel(new GridBagLayout());
		JLabel labelEdad = new JLabel("Edad: ");
		JLabel labelReduccion = new JLabel("Reducción: ");
		campoEdad = new JTextField(15);
		campoReduccion = new JLabel("0");
		GridBagConstraints gbcInf = new GridBagConstraints();
		gbcInf.insets = new Insets(5, 5, 5, 5);
		gbcInf.gridx = 0;
		gbcInf.gridy = 0;
		panelInfantil.add(labelEdad, gbcInf);
		gbcInf.gridx = 1;
		panelInfantil.add(campoEdad, gbcInf);
		gbcInf.gridx = 0;
		gbcInf.gridy = 1;
		panelInfantil.add(labelReduccion, gbcInf);
		gbcInf.gridx = 1;
		panelInfantil.add(campoReduccion, gbcInf);
		panelInfantil.setVisible(false);

		adultoButton.addItemListener(e -> {
			panelAdulto.setVisible(e.getStateChange() == ItemEvent.SELECTED);
			panelInfantil.setVisible(false);
			pack();
		});

		infantilButton.addItemListener(e -> {
			panelAdulto.setVisible(false);
			panelInfantil.setVisible(e.getStateChange() == ItemEvent.SELECTED);
			pack();
		});

		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(e -> crearSocio());

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		panel.add(panelAdulto, gbc);

		gbc.gridy = 5;
		panel.add(panelInfantil, gbc);

		gbc.gridy = 6;
		panel.add(aceptar, gbc);
		add(panel, BorderLayout.CENTER);
	}

	private void crearSocio() {
		try {
			String n = campoNombreYApellido.getText();
			String d = campoDni.getText();
			Integer c = Integer.parseInt(campoCuota.getText());
			TSocio socio = null;

			if (adultoButton.isSelected()) {
				Boolean mP;
				if (si.isSelected()) {
					mP = true;
					socio = new TAdulto(n, d, 0, c, mP);
				} else if (no.isSelected()) {
					mP = false;
					socio = new TAdulto(n, d, 0, c, mP);
				} else {
					JOptionPane.showMessageDialog(this, "Debe seleccionar si es miembro pleno o no.");
					return;
				}
			} else if (infantilButton.isSelected()) {
				int e = Integer.parseInt(campoEdad.getText());
				Double r = 0.0;
				if (e <= 3) {
					r = 0.50;
				} else if (e <= 14) {
					r = 0.20;
				} else if (e <= 18) {
					r = 0.10;
				}
				Double re = r * 100;
				campoReduccion.setText(re.toString() + "%");
				socio = new TInfantil(n, d, 1, c, r, e);
			}

			Context contexto = new Context(Evento.ALTA_SOCIO, socio);
			Controlador.getInstance().handle(contexto);
			setVisible(false);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Error en el formato de los datos", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			setVisible(true);
			return;
		}
		switch (context.getEvento()) {
		case Evento.ALTA_SOCIO_OK:
			JOptionPane.showMessageDialog(this, "Socio creado con éxito");
			campoNombreYApellido.setText("");
			campoDni.setText("");
			campoCuota.setText("");
			campoEdad.setText("");
			campoReduccion.setText("0%");
			adultoButton.setSelected(false);
			infantilButton.setSelected(false);
			panelAdulto.setVisible(false);
			panelInfantil.setVisible(false);
			break;
		case Evento.ALTA_SOCIO_KO:
			JOptionPane.showMessageDialog(this, "Error al añadir el socio", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;
		}
	}
}
