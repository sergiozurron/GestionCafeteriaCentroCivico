package com.grupoms.app.presentacion.socioJPA;

import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI_MostrarSociosPorPromocion extends JFrame implements IGUI {
    private static final long serialVersionUID = 1L;
    private JTextField idPromocion;
    private JButton verSocios;
    private JTextArea resultado;

    public GUI_MostrarSociosPorPromocion() {
        super("Ver Socios por Promoción");
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

        JLabel labelIdPromocion = new JLabel("ID Promoción:");
        idPromocion = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelIdPromocion, gbc);
        gbc.gridx = 1;
        panel.add(idPromocion, gbc);

        verSocios = new JButton("Ver Socios");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(verSocios, gbc);

        resultado = new JTextArea(10, 40);
        resultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultado);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        add(panel, BorderLayout.CENTER);

        verSocios.addActionListener(e -> {
            String id = idPromocion.getText().trim();
            resultado.setText(""); 
            Context context = new Context(Evento.MOSTRAR_SOCIOS_POR_PROMOCION, id);
            Controlador.getInstance().handle(context);
        });
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }
        switch (context.getEvento()) {
            case Evento.MOSTRAR_SOCIOS_POR_PROMOCION_OK:
                @SuppressWarnings("unchecked")
                List<TSocio> socios = (List<TSocio>) context.getDatos();
                
                StringBuilder mensaje = new StringBuilder();
                if (socios.isEmpty()) {
                    mensaje.append("No hay socios asociados a esta promoción.");
                } else {
                    for (TSocio s : socios) {
                        String tipo = (s.getTipoSocio() == 0) ? "Adulto" : "Infantil";
                        
                        mensaje.append("ID Promoción: ").append(s.getId())
                               .append(" | Nombre: ").append(s.getNombreYapellido())
                               .append(" | DNI: ").append(s.getDni())
                               .append(" | Cuota: ").append(s.getCuota())
                               .append(" | Tipo: ").append(tipo)
                               .append("\n----------------------------------------------------\n");
                    }
                }
                resultado.setText(mensaje.toString());
                break;
                
            case Evento.MOSTRAR_SOCIOS_POR_PROMOCION_KO:
                resultado.setText("Error al listar los socios por promoción. Verifique el ID.");
                JOptionPane.showMessageDialog(this, "Error al listar los socios", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}