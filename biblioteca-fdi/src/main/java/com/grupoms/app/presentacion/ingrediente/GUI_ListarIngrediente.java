package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.util.Set;

public class GUI_ListarIngrediente extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton botonCargar;

    public GUI_ListarIngrediente() {
        setTitle("Listado de Ingredientes");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
        setVisible(true);

        Context contexto = new Context(Evento.MOSTRAR_INGREDIENTES, null);
        Controlador.getInstance().handle(contexto);
    }

    private void initGUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // --- Configuración de la tabla ---
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Activo");
        modeloTabla.addColumn("ID Proveedor");

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        // --- Botón para cargar ingredientes ---
//        botonCargar = new JButton("Cargar Ingredientes");
//        botonCargar.addActionListener(e -> {
//            Context contexto = new Context(Evento.MOSTRAR_INGREDIENTES, null);
//            Controlador.getInstance().handle(contexto);
//        });

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(botonCargar, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.MOSTRAR_INGREDIENTES_OK) {
            modeloTabla.setRowCount(0); // limpia la tabla
            @SuppressWarnings("unchecked")
            Set<TIngrediente> ingredientes = (Set<TIngrediente>) context.getDatos();

            if (ingredientes == null || ingredientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ingredientes activos en la base de datos.");
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
        } 
        else if (context.getEvento() == Evento.MOSTRAR_INGREDIENTES_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar los ingredientes.");
        }
    }
}
