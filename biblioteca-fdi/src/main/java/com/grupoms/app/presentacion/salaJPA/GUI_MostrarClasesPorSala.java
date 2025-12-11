package com.grupoms.app.presentacion.salaJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarClasesPorSala extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField idSalaField;
	private JButton btnMostrar;
	private JTextArea resultadoArea;

	public GUI_MostrarClasesPorSala() {
		super("Mostrar Clases por Sala");
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

		JLabel labelIdSala = new JLabel("ID Sala:");
		idSalaField = new JTextField(20);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labelIdSala, gbc);
		
		gbc.gridx = 1;
		panel.add(idSalaField, gbc);

		btnMostrar = new JButton("Mostrar Clases");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2; 
		panel.add(btnMostrar, gbc);

		resultadoArea = new JTextArea(15, 40); // Un poco más grande para que quepa la info
		resultadoArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(resultadoArea);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(scrollPane, gbc);

		add(panel, BorderLayout.CENTER);

		btnMostrar.addActionListener(e -> {
			try {
				String textoId = idSalaField.getText().trim();
				if (textoId.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Por favor, introduce un ID de sala.", "Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Integer idSala = Integer.parseInt(textoId);
				
				Context context = new Context(Evento.MOSTRAR_CLASES_POR_SALA, idSala);
				Controlador.getInstance().handle(context);
				
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El ID de la sala debe ser un número entero.", "Error de formato", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	@Override
	public void actualizar(Context context) {
		if (context == null) {
			resultadoArea.setText(""); 
			idSalaField.setText("");
			setVisible(true);
			return;
		}

		switch (context.getEvento()) {
		case Evento.MOSTRAR_CLASES_POR_SALA_OK: 
			@SuppressWarnings("unchecked")
			List<TClase> clases = (List<TClase>) context.getDatos();
			
			StringBuilder mensaje = new StringBuilder();
			mensaje.append("Resultados para la Sala ID: ").append(idSalaField.getText()).append("\n");
			mensaje.append("=========================================\n");
			
			// Formateador para la fecha (ej: 11/12/2025 10:30)
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			if (clases.isEmpty()) {
				mensaje.append("No se han encontrado clases asignadas a esta sala.");
			} else {
				for (TClase clase : clases) {
					mensaje.append("ID Clase:     ").append(clase.getId()).append("\n");
					mensaje.append("Tipo:         ").append(clase.getTipo()).append("\n");
					
					String fechaStr = (clase.getFechaInicio() != null) ? sdf.format(clase.getFechaInicio()) : "Sin fecha";
					mensaje.append("Fecha Inicio: ").append(fechaStr).append("\n");
					
					mensaje.append("Duración:     ").append(clase.getDuracion()).append(" min\n");
					mensaje.append("Activo:       ").append(clase.getActivo() ? "Sí" : "No").append("\n");
					
					List<Integer> ejemplares = clase.getEjemplares();
					mensaje.append("Ejemplares:   ");
					if (ejemplares == null || ejemplares.isEmpty()) {
						mensaje.append("Ninguno");
					} else {
						mensaje.append(ejemplares.toString());
					}
					mensaje.append("\n-----------------------------------------\n");
				}
			}

			resultadoArea.setText(mensaje.toString());
			// Posicionar el scroll arriba del todo
			resultadoArea.setCaretPosition(0);
			
			// Opcional: Si quieres mantener el popup también
			// JOptionPane.showMessageDialog(this, mensaje.toString(), "Listado de Clases por Sala", JOptionPane.INFORMATION_MESSAGE);
			break;

		case Evento.MOSTRAR_CLASES_POR_SALA_KO:
			JOptionPane.showMessageDialog(this, "Error al buscar las clases de la sala.\nComprueba que la sala existe.", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}
}