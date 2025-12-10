package com.grupoms.app.presentacion.socioJPA;

import com.grupoms.app.negocio.socioJPA.TAdulto;
import com.grupoms.app.negocio.socioJPA.TInfantil;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUI_ListarSocios extends JFrame implements IGUI {
    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton botonCargar;

    public GUI_ListarSocios(){
        setTitle("Listado de Socios");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }

    private void initGUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // --- Configuración de la tabla ---
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre Y Apellidos");
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Cuota");
        modeloTabla.addColumn("Tipo Socio");
        modeloTabla.addColumn("Miembro Pleno");
        modeloTabla.addColumn("Edad");
        modeloTabla.addColumn("Reducción");

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        // --- Botón para cargar Socios ---
        botonCargar = new JButton("Cargar Socios");
        botonCargar.addActionListener(e -> {
            try {
                Context contexto = new Context(Evento.LISTAR_SOCIOS, null);
                Controlador.getInstance().handle(contexto);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar socios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(botonCargar, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }
        if (context.getEvento() == Evento.LISTAR_SOCIOS_OK) {
            modeloTabla.setRowCount(0); // limpia la tabla
            java.util.List<TSocio> socios = (List<TSocio>) context.getDatos();

            if (socios == null || socios.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay socios en la base de datos.");
                return;
            }

            for (TSocio s : socios) {
                if (s instanceof TAdulto adulto) {
                    if(adulto.getMiembroPleno()){
                        modeloTabla.addRow(new Object[]{
                                adulto.getId(),
                                adulto.getNombreYapellido(),
                                adulto.getDni(),
                                adulto.getCuota(),
                                "Adulto",
                                "Sí",
                                "",
                                ""
                        });
                    }
                    else{
                        modeloTabla.addRow(new Object[]{
                                adulto.getId(),
                                adulto.getNombreYapellido(),
                                adulto.getDni(),
                                adulto.getCuota(),
                                "Adulto",
                                "No",
                                "",
                                ""
                        });
                    }

                } else if (s instanceof TInfantil infantil) {
                    modeloTabla.addRow(new Object[]{
                            infantil.getId(),
                            infantil.getNombreYapellido(),
                            infantil.getDni(),
                            infantil.getCuota(),
                            "Infantil",
                            "",
                            infantil.getEdad(),
                            infantil.getReduccion()
                    });
                }
            }
        } else if (context.getEvento() == Evento.LISTAR_SOCIOS_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar los socios.");
        }
    }

}
