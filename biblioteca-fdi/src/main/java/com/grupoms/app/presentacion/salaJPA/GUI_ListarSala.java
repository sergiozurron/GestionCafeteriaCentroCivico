package com.grupoms.app.presentacion.salaJPA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.util.List;

public class GUI_ListarSala extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton botonCargar;

    public GUI_ListarSala() {
        setTitle("Listado de Salas");
        // He reducido un poco el ancho ya que ahora hay una columna menos
        setSize(500, 500); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }

    private void initGUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // --- Configuración de la tabla ---
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Capacidad");
        // Eliminada: modeloTabla.addColumn("Activo");

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        // --- Botón para cargar salas ---
        botonCargar = new JButton("Cargar Salas");
        botonCargar.addActionListener(e -> {
            try {
                // Asegúrate de que el evento aquí coincida con el que espera tu controlador
                Context contexto = new Context(Evento.LISTAR_SALA, null);
                Controlador.getInstance().handle(contexto);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar salas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(botonCargar, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }

        // IMPORTANTE: Asegúrate de que Evento.LISTAR_SALAS_OK es el nombre correcto del evento de éxito
        if (context.getEvento() == Evento.LISTAR_SALAS_OK) { 
            modeloTabla.setRowCount(0); // Limpia la tabla
            List<TSala> salas = (List<TSala>) context.getDatos();

            if (salas == null || salas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay salas en la base de datos.");
                return;
            }

            for (TSala s : salas) {
                modeloTabla.addRow(new Object[]{
                    s.getId(),
                    s.getNombre(),
                    s.getCapacidad()
                    // Eliminado el dato de activo del array de la fila
                });
            }
        } else if (context.getEvento() == Evento.LISTAR_SALAS_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar las salas.");
        }
    }
}