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

    private static final long serialVersionUID = 1L;

    private JTextField campoIdProveedor;
    private JButton botonBuscar;
    private JTable tabla;
    private JScrollPane scrollTabla;

    public GUI_ProductosPorProveedor() {
        super("Productos por Proveedor");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
    }

    private void initGUI() {

        setLayout(new BorderLayout());

        // Panel superior con el campo y el botón
        JPanel panelSuperior = new JPanel(new FlowLayout());

        JLabel labelId = new JLabel("ID Proveedor:");
        campoIdProveedor = new JTextField(10);

        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(e -> buscarProductos());

        panelSuperior.add(labelId);
        panelSuperior.add(campoIdProveedor);
        panelSuperior.add(botonBuscar);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla vacía inicial
        tabla = new JTable();
        scrollTabla = new JScrollPane(tabla);
        add(scrollTabla, BorderLayout.CENTER);
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

        if (context == null) {
            setVisible(true);
            return;
        }

        if (context.getEvento() == Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR_OK) {

            @SuppressWarnings("unchecked")
            List<TProducto> productos = (List<TProducto>) context.getDatos();

            if (productos == null || productos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay productos para ese proveedor",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Columnas de la tabla
            String[] columnas = {
                    "ID", "Nombre", "Precio", "Stock", "Tipo",
                    "Activo", "Tamaño", "Tiempo Prep.", "Calorías"
            };

            // Datos de la tabla
            Object[][] datos = new Object[productos.size()][columnas.length];

            for (int i = 0; i < productos.size(); i++) {
                TProducto p = productos.get(i);

                datos[i][0] = p.getId();
                datos[i][1] = p.getNombre();
                datos[i][2] = p.getPrecio();
                datos[i][3] = p.getStock();
                datos[i][4] = p.getTipo();
                datos[i][5] = p.getActivo() ? "Sí" : "No";

                if (p.getTipo().equals("Bebida")) {
                    datos[i][6] = p.getTamanho();
                    datos[i][7] = "-";
                    datos[i][8] = "-";
                } else {
                    datos[i][6] = "-";
                    datos[i][7] = p.getTiempoPreparacion();
                    datos[i][8] = p.getCalorias();
                }
            }

            // Actualizar tabla
            tabla.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));

        } else if (context.getEvento() == Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR_KO) {

            JOptionPane.showMessageDialog(this,
                    "No se pudieron obtener los productos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
