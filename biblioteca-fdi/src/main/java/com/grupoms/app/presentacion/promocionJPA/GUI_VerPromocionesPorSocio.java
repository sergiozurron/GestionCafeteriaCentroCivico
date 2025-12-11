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

    private static final long serialVersionUID = 1L;

    private JTextField idSocioField;
    private JButton verPromocionesBtn;

    private JTextArea promocionesArea;

    public GUI_VerPromocionesPorSocio() {
        super("Ver Promociones por Socio");
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

        // Campo ID Socio
        JLabel labelIdSocio = new JLabel("ID Socio:");
        idSocioField = new JTextField(10);

        verPromocionesBtn = new JButton("Ver Promociones");
        verPromocionesBtn.addActionListener(e -> {
            String idText = idSocioField.getText().trim();
            Context context = new Context(Evento.VER_PROMOCIONES_POR_SOCIO, idText);
            Controlador.getInstance().handle(context);
        });

        promocionesArea = new JTextArea(10, 30);
        promocionesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(promocionesArea);

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelIdSocio, gbc);
        gbc.gridx = 1;
        panel.add(idSocioField, gbc);

        y++;
        gbc.gridx = 0; 
        gbc.gridy = y; 
        gbc.gridwidth = 2;
        panel.add(verPromocionesBtn, gbc);

        y++;
        gbc.gridx = 0; 
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        add(panel, BorderLayout.CENTER);
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
            List<TPromocion> lista = (List<TPromocion>) context.getDatos();

            StringBuilder sb = new StringBuilder();

            if (lista.isEmpty()) {
                sb.append("Este socio no tiene promociones asociadas.");
            } else {
                for (TPromocion p : lista) {
                    sb.append("ID: ").append(p.getId()).append("\n");
                    sb.append("Tipo: ").append(p.getTipo()).append("\n");
                    sb.append("Descuento: ").append(p.getDescuento()).append("\n");
                    sb.append("Activo: ").append(p.getActivo() ? "Sí" : "No").append("\n");
                    sb.append("---------------------------\n");
                }
            }

            promocionesArea.setText(sb.toString());
            break;

        case Evento.VER_PROMOCIONES_POR_SOCIO_KO:
            promocionesArea.setText("");
            JOptionPane.showMessageDialog(this,
                "Error: No se pudieron obtener las promociones del socio.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            break;
        }
    }
}
