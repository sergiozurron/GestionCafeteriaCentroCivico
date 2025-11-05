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

public class GUI_AltaProducto extends JFrame implements IGUI {

    private JTextField campoNombre;
    private JTextField campoPrecio;
    private JTextField campoStock;
    private JRadioButton bebidaButton;
    private JRadioButton comidaButton;
    private JButton crear;
    private JPanel panelBebida;
    private JPanel panelComida;
    private JLabel labelTamanho;
    private JTextField campoTamanho;
    private JLabel labelTiempoPreparacion;
    private JTextField campoTiempoPreparacion;
    private JLabel labelCalorias;
    private JTextField campoCalorias;

    public GUI_AltaProducto(){
       super("Alta Producto");
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

        panelBebida = new JPanel(new GridBagLayout());
        labelTamanho = new JLabel("Tamaño:");
        campoTamanho = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 1;
        panelBebida.add(labelTamanho, gbc);
        gbc.gridx = 1;
        panelBebida.add(campoTamanho, gbc);
        panelBebida.setVisible(false);

        panelComida = new JPanel(new GridBagLayout());
        labelTiempoPreparacion = new JLabel("Suplemento:");
        campoTiempoPreparacion = new JTextField(10);
        labelCalorias = new JLabel("Calorías:");
        campoCalorias = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 1;
        panelComida.add(labelTiempoPreparacion, gbc);
        gbc.gridx = 1;
        panelComida.add(campoTiempoPreparacion, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        panelComida.add(labelCalorias, gbc);
        gbc.gridx = 1;
        panelComida.add(campoCalorias, gbc);
        panelComida.setVisible(false);

        bebidaButton.addActionListener(e -> {
            panelBebida.setVisible(true);
            panelComida.setVisible(false);
            pack();
        });

        comidaButton.addActionListener(e -> {
            panelComida.setVisible(true);
            panelBebida.setVisible(false);
            pack();
        });

        crear = new JButton("Crear Producto");
        crear.addActionListener(e -> crearProducto());

        gbc.gridx = 0; gbc.gridy = 0; panel.add(labelNombre, gbc);
        gbc.gridx = 1; panel.add(campoNombre, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(labelPrecio, gbc);
        gbc.gridx = 1; panel.add(campoPrecio, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(labelStock, gbc);
        gbc.gridx = 1; panel.add(campoStock, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(bebidaButton, gbc);
        gbc.gridx = 1; panel.add(comidaButton, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; panel.add(panelBebida, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; panel.add(panelComida, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(crear, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void crearProducto() {
        try {
            String nombre = campoNombre.getText();
            Double precio = Double.parseDouble(campoPrecio.getText());
            int stock = Integer.parseInt(campoStock.getText());

            TProducto producto;
            if (bebidaButton.isSelected()) {
                producto = new TBebida();
                producto.setTamanho(Integer.parseInt(campoTamanho.getText()));
            } else /*if (comidaButton.isSelected())*/ {
                producto = new TComida();
                producto.setCalorias(Integer.parseInt(campoCalorias.getText()));
                producto.setTiempoPreparacion(Integer.parseInt(campoTiempoPreparacion.getText()));
            }
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);

            Context contexto = new Context(Evento.ALTA_PRODUCTO, producto);
            Controlador.getInstance().handle(contexto);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
        }
    }

    @Override
    public void actualizar(Context context) {
    	if (context == null)
    		setVisible(true);
        else if (context.getEvento() == Evento.ALTA_PRODUCTO_OK) {
            JOptionPane.showMessageDialog(this, "Producto creado con éxito");
            campoNombre.setText("");
            campoPrecio.setText("");
            campoStock.setText("");
        } else if (context.getEvento() == Evento.ALTA_PRODUCTO_KO) {
            JOptionPane.showMessageDialog(this, "Error al crear el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}