package com.grupoms.app.presentacion.promocionJPA;

import javax.swing.*;
import java.util.List;

import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;

public class GUI_VerPromocionesPorSocio extends JFrame implements IGUI {
    /**
     * 
     */
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
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelIdSocio = new JLabel("ID Socio:");
        idSocio = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelIdSocio, gbc);
        gbc.gridx = 1;
        panel.add(idSocio, gbc);

        verPromociones = new JButton("Ver Promociones");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(verPromociones, gbc);

        resultado = new JTextArea(10, 30);
        resultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultado);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        add(panel, BorderLayout.CENTER);

        verPromociones.addActionListener(e -> {
            String id = idSocio.getText().trim();
            Context context = new Context(Evento.VER_PROMOCIONES_POR_SOCIO, id);
            Controlador.getInstance().handle(context);
        });
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            return;
        }
        switch(context.getEvento()) {
            case Evento.VER_PROMOCIONES_POR_SOCIO_OK:
                @SuppressWarnings("unchecked")
                List<TPromocion> promociones = (List<TPromocion>) context.getDatos();
                StringBuilder mensaje = new StringBuilder("Promociones por socio:\n");
                for (TPromocion promo : promociones) {
                    mensaje.append("ID: ").append(promo.getId())
                           .append(", Tipo: ").append(promo.getTipo())
                           .append(", Descuento: ").append(promo.getDescuento())
                           .append("\n");
                }
                JOptionPane.showMessageDialog(this, mensaje.toString(), "Listado de Promociones por socio", JOptionPane.INFORMATION_MESSAGE);
                break;
            case Evento.VER_PROMOCIONES_POR_SOCIO_KO:
                JOptionPane.showMessageDialog(this, "Error al listar las promociones por socio", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
