package com.grupoms.app.presentacion.producto;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.producto.TBebida;
import com.grupoms.app.negocio.producto.TComida;
import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarProducto extends JFrame implements IGUI {


    private JTextField campoNombre;
    private JTextField campoPrecio;
    private JTextField campoStock;
    private JRadioButton bebidaButton;
    private JRadioButton comidaButton;
    private JButton modificar;

    public GUI_ModificarProducto() {
        super("Modificar Producto");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //destruye la ventana sin cerrar la app
        pack(); //ajusta
        setLocationRelativeTo(null); //centra
         //es visible
    }

    private void initGUI() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelNombre = new JLabel("Nombre:");
        campoNombre = new JTextField(10);

        JLabel labelPrecio = new JLabel("Precio:");
        campoPrecio = new JTextField(10);

        JLabel labelStock = new JLabel("Stock:");
        campoStock = new JTextField(10);

        bebidaButton = new JRadioButton("Bebida");
        comidaButton = new JRadioButton("Comida");
        ButtonGroup tipoGroup = new ButtonGroup();
        tipoGroup.add(bebidaButton);
        tipoGroup.add(comidaButton);

        modificar = new JButton("Modificar Producto");
        modificar.addActionListener(e -> modificarProducto());

        gbc.gridx = 0; gbc.gridy = 0; panel.add(labelNombre, gbc);
        gbc.gridx = 1; panel.add(campoNombre, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(labelPrecio, gbc);
        gbc.gridx = 1; panel.add(campoPrecio, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(labelStock, gbc);
        gbc.gridx = 1; panel.add(campoStock, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(modificar, gbc);

        add(panel, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
    }

    private void modificarProducto() {
        try {
            String nombre = campoNombre.getText();
            Double precio = Double.parseDouble(campoPrecio.getText());
            int stock = Integer.parseInt(campoStock.getText());

            TProducto producto;
            if (bebidaButton.isSelected()) {
                producto = new TBebida();
            } else /*if (comidaButton.isSelected())*/ {
                producto = new TComida();
            }

            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);

            Context contexto = new Context(Evento.MODIFICAR_PRODUCTO, producto);
            Controlador.getInstance().handle(contexto);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizar(Context context) {
    	if (context == null)
    		setVisible(true);
    }
    
}
