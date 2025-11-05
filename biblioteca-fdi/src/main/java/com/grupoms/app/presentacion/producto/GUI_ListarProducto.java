package com.grupoms.app.presentacion.producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarProducto extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton botonCargar;

    public GUI_ListarProducto() {
        setTitle("Listado de Productos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
        setVisible(true);

    }

    private void initGUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // --- Configuración de la tabla ---
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Activo");

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        // --- Botón para cargar productos ---
        botonCargar = new JButton("Cargar Productos");
        botonCargar.addActionListener(e -> {
            try {
                Context contexto = new Context(Evento.MOSTRAR_LISTA_PRODUCTO, null);
                Controlador.getInstance().handle(contexto);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelPrincipal.add(botonCargar, BorderLayout.SOUTH);


        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(botonCargar, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.MOSTRAR_LISTA_PRODUCTO_OK) {
            modeloTabla.setRowCount(0); // limpia la tabla
            Set<TProducto> productos = (Set<TProducto>) context.getDatos();

            if (productos == null || productos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos activos en la base de datos.", "Sin datos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (TProducto p : productos) {
                Object[] fila = {
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock(),
                    p.getTipo(),
                    p.getActivo() ? "Sí" : "No"
                };
                modeloTabla.addRow(fila);
            }
        } 
        else if (context.getEvento() == Evento.MOSTRAR_LISTA_PRODUCTO_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos " + context.getDatos(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
