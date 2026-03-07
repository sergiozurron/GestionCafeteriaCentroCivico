package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.grupoms.app.negocio.producto.TEntradaReceta;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarIngredienteProducto extends JFrame implements IGUI {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
    }

    private void initGUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

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

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Producto");
        modeloTabla.addColumn("ID Ingrediente");
        modeloTabla.addColumn("Activo");

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }
        else if (context.getEvento() == Evento.LISTAR_INGREDIENTES_POR_PRODUCTO_OK) {

            modeloTabla.setRowCount(0);

            List<TEntradaReceta> lista = (List<TEntradaReceta>) context.getDatos();

            if (lista == null || lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ingredientes asociados a este producto.", "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (TEntradaReceta er : lista) {
                Object[] fila = {

                    er.getProductoID(),
                    er.getIngredienteID(),
                    er.getActivo() ? "Sí" : "No"
                };
                modeloTabla.addRow(fila);
            }

        } else if (context.getEvento() == Evento.LISTAR_INGREDIENTES_POR_PRODUCTO_KO) {
            JOptionPane.showMessageDialog(this, "Error: " + context.getDatos());
        }
    }
}
