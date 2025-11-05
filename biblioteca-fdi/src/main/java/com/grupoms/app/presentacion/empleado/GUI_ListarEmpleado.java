package com.grupoms.app.presentacion.empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarEmpleado extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton botonCargar;

    public GUI_ListarEmpleado() {
        setTitle("Listado de Empleados");
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
        modeloTabla.addColumn("Donde Atiende");
        modeloTabla.addColumn("Sueldo");
        modeloTabla.addColumn("Activo");

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        // --- Botón para cargar empleados para no saturar con cargas automaticas
        botonCargar = new JButton("Cargar Empleados");
        botonCargar.addActionListener(e -> {
          try {
                Context contexto = new Context(Evento.MOSTRAR_EMPLEADOS, null);
                Controlador.getInstance().handle(contexto);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(botonCargar, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.MOSTRAR_EMPLEADOS_OK) {
            modeloTabla.setRowCount(0); // limpia la tabla
            Set<TEmpleado> empleados = (Set<TEmpleado>) context.getDatos();

            if (empleados == null || empleados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay empleados activos en la base de datos.");
                return;
            }

            for (TEmpleado emp : empleados) {
                Object[] fila = {
                    emp.getID(),
                    emp.getNombre(),
                    emp.getDondeAtiende(),
                    emp.getSueldo(),
                    (emp.getActivo() != null && emp.getActivo()) ? "Sí" : "No"
                };
                modeloTabla.addRow(fila);
            }
        } 
        else if (context.getEvento() == Evento.MOSTRAR_EMPLEADOS_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar los empleados.");
        }
    }
}
