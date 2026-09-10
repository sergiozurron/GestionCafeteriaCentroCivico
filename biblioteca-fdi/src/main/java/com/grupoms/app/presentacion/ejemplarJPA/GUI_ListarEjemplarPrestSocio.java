package com.grupoms.app.presentacion.ejemplarJPA;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarEjemplarPrestSocio extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnBuscar;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;

    public GUI_ListarEjemplarPrestSocio() {
        setTitle("Ejemplares prestados por socios adultos plenos");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelFiltros = new JPanel(new GridLayout(2, 2));

        panelFiltros.add(new JLabel("Fecha inicio (YYYY-MM-DD):"));
        txtFechaInicio = new JTextField();
        panelFiltros.add(txtFechaInicio);

        panelFiltros.add(new JLabel("Fecha fin (YYYY-MM-DD):"));
        txtFechaFin = new JTextField();
        panelFiltros.add(txtFechaFin);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Id Material");

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> {
            String fechaInicio = txtFechaInicio.getText().trim();
            String fechaFin = txtFechaFin.getText().trim();

            if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce ambas fechas", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Object[] datos = { fechaInicio, fechaFin };

                Context contexto = new Context(Evento.LISTAR_EJEMPLARES_PREST_SOCIO, datos);
                Controlador.getInstance().handle(contexto);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error al buscar ejemplares: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelPrincipal.add(panelFiltros, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(btnBuscar, BorderLayout.SOUTH);

        add(panelPrincipal);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                modeloTabla.setRowCount(0);
            }
        });
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }

        if (context.getEvento() == Evento.LISTAR_EJEMPLARES_PREST_SOCIO_OK) {
            modeloTabla.setRowCount(0);

            List<TEjemplar> ejemplares = (List<TEjemplar>) context.getDatos();
            for (TEjemplar ejemplar : ejemplares) {
                Object[] fila = {
                        ejemplar.getId(),
                        ejemplar.getEstado(),
                        ejemplar.getIdMaterial()
                };
                modeloTabla.addRow(fila);
            }
        }
    }
}