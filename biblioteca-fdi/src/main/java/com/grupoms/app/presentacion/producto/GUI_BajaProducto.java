package com.grupoms.app.presentacion.producto;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.producto.TBebida;
import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_BajaProducto extends JFrame implements IGUI {

    private JTextField idProd;
    private JButton baja;

    public GUI_BajaProducto() {
        super("Baja Producto");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    void initGUI() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelIdProd = new JLabel("ID Producto:");
        idProd = new JTextField(10);

        baja = new JButton("Dar de Baja");
        baja.addActionListener(e -> bajaProducto());

        gbc.gridx = 0; gbc.gridy = 0; panel.add(labelIdProd, gbc);
        gbc.gridx = 1; panel.add(idProd, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(baja, gbc);

        add(panel, BorderLayout.CENTER);
    }

    void bajaProducto() {
        try {
            int id = Integer.parseInt(idProd.getText().trim());
            TProducto tProducto = new TBebida(); // antes habia un combobox, pero no use usaba (?)
            tProducto.setId(id);
            tProducto.setActivo(false);

            Context contexto = new Context(Evento.BAJA_PRODUCTO, tProducto);
            Controlador.getInstance().handle(contexto);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de producto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizar(Context context) {
    	if (context == null)
    		setVisible(true);
        else if (context.getEvento() == Evento.BAJA_PRODUCTO_OK) {
            JOptionPane.showMessageDialog(this, "Producto dado de baja con éxito");
            idProd.setText("");
        } else if (context.getEvento() == Evento.BAJA_PRODUCTO_KO) {
            JOptionPane.showMessageDialog(this, "Error al dar de baja el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
