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

public class GUI_MostrarProducto extends JFrame implements IGUI{

    private JTextField idProducto;
    private JComboBox<String> tipoProducto;
    private JButton mostrar;

    public GUI_MostrarProducto() {
        super("Mostrar Producto");
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

        JLabel labeltipoProducto = new JLabel("Tipo de Producto: ");
        tipoProducto = new JComboBox<>(new String[] {"Bebida", "Comida"});

        mostrar = new JButton("Mostrar Producto");
        mostrar.addActionListener(e -> mostrarProducto());

        gbc.gridx = 0; gbc.gridy = 0; panel.add(labeltipoProducto, gbc);
        gbc.gridx = 1; panel.add(tipoProducto, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(mostrar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void mostrarProducto() {
        String tipo = (String) tipoProducto.getSelectedItem();

        TProducto producto;
        if (tipo.equals("Bebida")) {
            producto = new TBebida();
        } else /*if (tipo.equals("Comida"))*/ {
            producto = new TComida();
        }

        producto.setId(Integer.parseInt(idProducto.getText()));

        Context contexto = new Context(Evento.MOSTRAR_PRODUCTO, producto);
        Controlador.getInstance().handle(contexto);
    }

    @Override
    public void actualizar(Context context) {
    	if (context == null)
    		setVisible(true);
        else if (context.getEvento() == Evento.MOSTRAR_PRODUCTO_OK) {
            TProducto producto = (TProducto) context.getDatos();
            String info = "ID: " + producto.getId() + "\n" +
                          "Nombre: " + producto.getNombre() + "\n" +
                          "Precio: " + producto.getPrecio() + "\n" +
                          "Stock: " + producto.getStock() + "\n" +
                          "Tipo: " + producto.getTipo() + "\n" +
                          "Activo: " + (producto.getActivo() ? "Sí" : "No");
                          
            if (producto.getTipo().equals("Bebida")) {
                info += "\nTamaño: " + producto.getTamanho();
            } else if (producto.getTipo().equals("Comida")) {
                info += "\nTiempo de Preparación: " + ((TComida) producto).getTiempoPreparacion() + 
                        "\nCalorías: " + producto.getCalorias();
            }
            JOptionPane.showMessageDialog(this, info, "Información del Producto", JOptionPane.INFORMATION_MESSAGE);
        } else if (context.getEvento() == Evento.MOSTRAR_PRODUCTO_KO) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
