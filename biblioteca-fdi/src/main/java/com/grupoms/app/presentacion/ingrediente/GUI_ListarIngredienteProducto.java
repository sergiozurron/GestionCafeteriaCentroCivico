package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarIngredienteProducto extends JFrame implements IGUI {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField campoIDProducto;
    private JButton botonBuscar;

    public GUI_ListarIngredienteProducto() {
        setTitle("Listar Ingredientes por Producto");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
        setVisible(true);
    }

    private void initGUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        // Parte superior: campo para ID de producto
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        panelBusqueda.add(new JLabel("ID del Producto:"));
        campoIDProducto = new JTextField(10);
        panelBusqueda.add(campoIDProducto);

        botonBuscar = new JButton("Buscar Ingredientes");
        botonBuscar.addActionListener(e -> {
            try {
                int idProducto = Integer.parseInt(campoIDProducto.getText());
                Context contexto = new Context(Evento.LISTAR_INGREDIENTES_POR_PRODUCTO, idProducto);
                Controlador.getInstance().handle(contexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduce un ID de producto válido (número entero).");
            }
        });
        panelBusqueda.add(botonBuscar);

        // Tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Activo");
        modeloTabla.addColumn("ID Proveedor");

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.LISTAR_INGREDIENTES_POR_PRODUCTO_OK) {
            modeloTabla.setRowCount(0);
            Set<TIngrediente> ingredientes = (Set<TIngrediente>) context.getDatos();
            if (ingredientes == null || ingredientes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No hay ingredientes asociados a este producto.",
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
            for (TIngrediente ing : ingredientes) {
                Object[] fila = {
                    ing.getID(),
                    ing.getNombre(),
                    ing.getPrecio(),
                    ing.getActivo() ? "Sí" : "No",
                    ing.getIDProveedor()
                };
                modeloTabla.addRow(fila);
            }

        } else if (context.getEvento() == Evento.LISTAR_INGREDIENTES_POR_PRODUCTO_KO) {
            JOptionPane.showMessageDialog(this, "Error: " + context.getDatos());
        }
    }
}
