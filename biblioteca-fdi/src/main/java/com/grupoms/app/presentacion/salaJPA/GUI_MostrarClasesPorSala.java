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

    private JTextField campoIdSala;
    private JTextArea areaResultado;
    private JButton btnBuscar;

    public GUI_MostrarClasesPorSala() {
        setTitle("Mostrar Clases por Sala");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel labelId = new JLabel("ID Sala:");
        campoIdSala = new JTextField(10);
        btnBuscar = new JButton("Buscar Clases");

        btnBuscar.addActionListener(e -> {
            String idText = campoIdSala.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe introducir un ID de Sala.");
                return;
            }
            try {
                int idSala = Integer.parseInt(idText);
                // Enviamos evento específico
                Context contexto = new Context(Evento.MOSTRAR_CLASES_POR_SALA, idSala);
                Controlador.getInstance().handle(contexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panelSuperior.add(labelId, gbc);
        gbc.gridx = 1;
        panelSuperior.add(campoIdSala, gbc);
        gbc.gridx = 2;
        panelSuperior.add(btnBuscar, gbc);

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
            case Evento.MOSTRAR_CLASES_POR_SALA_OK:
                @SuppressWarnings("unchecked")
                List<TClase> lista = (List<TClase>) context.getDatos();
                
                if (lista == null || lista.isEmpty()) {
                    areaResultado.setText("");
                    JOptionPane.showMessageDialog(this, "Esta sala no tiene clases asignadas o no existe.");
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    StringBuilder sb = new StringBuilder();
                    sb.append("Clases asignadas a la Sala ID ").append(campoIdSala.getText()).append(":\n\n");
                    
                    for (TClase c : lista) {
                        sb.append("ID Clase: ").append(c.getId()).append(" | ");
                        sb.append("Tipo: ").append(c.getTipo()).append(" | ");
                        sb.append("Fecha: ").append(c.getFechaInicio() != null ? sdf.format(c.getFechaInicio()) : "N/A").append(" | ");
                        sb.append("Duración: ").append(c.getDuracion()).append(" min");
                        sb.append("\n-------------------------------------------------\n");
                    }
                    areaResultado.setText(sb.toString());
                }
                break;
                
            case Evento.MOSTRAR_CLASES_POR_SALA_KO:
                areaResultado.setText("");
                JOptionPane.showMessageDialog(this, "Error al recuperar las clases de la sala.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                break;
        }
    }
}