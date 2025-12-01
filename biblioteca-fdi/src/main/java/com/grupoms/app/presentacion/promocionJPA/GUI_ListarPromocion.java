package com.grupoms.app.presentacion.promocionJPA;

import javax.swing.*;
import java.util.List;

import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;

public class GUI_ListarPromocion extends JFrame implements IGUI {
    public GUI_ListarPromocion() {
        super("Listar Promociones");
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

        // Botón Listar
        JButton listar = new JButton("Listar Promociones");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        listar.addActionListener(e -> listarPromociones());

        panel.add(listar, gbc);
        add(panel, BorderLayout.CENTER);
    }

    private void listarPromociones() {
        Context contexto = new Context(Evento.LISTAR_PROMOCION, null);
        Controlador.getInstance().handle(contexto);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            return;
        }
        switch(context.getEvento()) {
            case Evento.LISTAR_PROMOCION_OK:
                @SuppressWarnings("unchecked")
                List<TPromocion> promociones = (List<TPromocion>) context.getDatos();
                StringBuilder mensaje = new StringBuilder("Promociones:\n");
                for (TPromocion promo : promociones) {
                    mensaje.append("ID: ").append(promo.getId())
                           .append(", Tipo: ").append(promo.getTipo())
                           .append(", Descuento: ").append(promo.getDescuento())
                           .append("\n");
                }
                JOptionPane.showMessageDialog(this, mensaje.toString(), "Listado de Promociones", JOptionPane.INFORMATION_MESSAGE);
                break;
            case Evento.LISTAR_PROMOCION_KO:
                JOptionPane.showMessageDialog(this, "Error al listar las promociones", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}