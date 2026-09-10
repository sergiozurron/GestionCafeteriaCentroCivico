package com.grupoms.app.presentacion.promocionJPA;

import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI_VerPromocionesPorSocio extends JFrame implements IGUI {
    private static final long serialVersionUID = 1L;
    private JTextField idSocio;
    private JButton verPromociones;
    private JTextArea resultado;

    public GUI_VerPromocionesPorSocio() {
        super("Ver Promociones por Socio");
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

        JLabel labelIdSocio = new JLabel("ID Socio:");
        idSocio = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelIdSocio, gbc);
        gbc.gridx = 1;
        panel.add(idSocio, gbc);

        verPromociones = new JButton("Ver Promociones");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(verPromociones, gbc);

        // Configuración del JTextArea
        resultado = new JTextArea(10, 40);
        resultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultado);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH; 
        panel.add(scrollPane, gbc);

        add(panel, BorderLayout.CENTER);

        verPromociones.addActionListener(e -> {
            String id = idSocio.getText().trim();
            resultado.setText("");
            Context context = new Context(Evento.VER_PROMOCIONES_POR_SOCIO, id);
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
            case Evento.VER_PROMOCIONES_POR_SOCIO_OK:
                @SuppressWarnings("unchecked")
                List<TPromocion> promociones = (List<TPromocion>) context.getDatos();
                
                StringBuilder mensaje = new StringBuilder();
                if (promociones.isEmpty()) {
                    mensaje.append("Este socio no tiene promociones activas.");
                } else {
                    for (TPromocion p : promociones) {
                        String activo = p.getActivo() ? "Sí" : "No";
                        
                        mensaje.append("ID Promoción: ").append(p.getId())
                               .append(" | Tipo: ").append(p.getTipo())
                               .append(" | Descuento: ").append(p.getDescuento())
                               .append(" | Activo: ").append(activo)
                               .append("\n----------------------------------------------------\n");
                    }
                }
                resultado.setText(mensaje.toString());
                break;
                
            case Evento.VER_PROMOCIONES_POR_SOCIO_KO:
                resultado.setText("Error al buscar promociones para este socio. Verifique el ID.");
                JOptionPane.showMessageDialog(this, "Error al obtener promociones", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}