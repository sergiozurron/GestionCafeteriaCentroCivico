package com.grupoms.app.presentacion.producto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ProductosPorProveedor extends JFrame implements IGUI {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField campoIdProveedor;
    private JButton buscar;

    public GUI_ProductosPorProveedor() {
        super("Mostrar Productos por Proveedor");
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

        JLabel labelId = new JLabel("ID Proveedor:");
        campoIdProveedor = new JTextField(10);

        buscar = new JButton("Buscar");
        buscar.addActionListener(e -> buscarProductos());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelId, gbc);

        gbc.gridx = 1;
        panel.add(campoIdProveedor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(buscar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void buscarProductos() {

        try {
            Integer idProveedor = Integer.parseInt(campoIdProveedor.getText());

            Context contexto = new Context(
                    Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR,
                    idProveedor
            );

            Controlador.getInstance().handle(contexto);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe ser numérico",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizar(Context context) {

        if (context == null)
            setVisible(true);

        else if (context.getEvento() == Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR_OK) {

            @SuppressWarnings("unchecked")
            List<TProducto> productos = (List<TProducto>) context.getDatos();

            if (productos == null || productos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay productos para ese proveedor",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {

                for (TProducto producto : productos) {

                    String info = "ID: " + producto.getId() + "\n"
                            + "Nombre: " + producto.getNombre() + "\n"
                            + "Precio: " + producto.getPrecio() + "\n"
                            + "Stock: " + producto.getStock() + "\n"
                            + "Tipo: " + producto.getTipo() + "\n"
                            + "Activo: " + (producto.getActivo() ? "Sí" : "No");

                    if (producto.getTipo().equals("Bebida")) {
                        info += "\nTamaño: " + producto.getTamanho();
                    } 
                    else if (producto.getTipo().equals("Comida")) {
                        info += "\nTiempo de Preparación: "
                                + producto.getTiempoPreparacion()
                                + "\nCalorías: " + producto.getCalorias();
                    }

                    JOptionPane.showMessageDialog(this,
                            info,
                            "Información del Producto",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        else if (context.getEvento() == Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR_KO) {

            JOptionPane.showMessageDialog(this,
                    "No se pudieron obtener los productos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}