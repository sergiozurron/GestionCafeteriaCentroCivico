package com.grupoms.app.presentacion.mesa;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.negocio.mesa.TMesaTerraza;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarMesa extends JFrame implements IGUI {

    private JTextField idMesa;
    private JComboBox<String> tipoMesa;
    private JButton mostrar;
    private JTable tablaMesa;
    private DefaultTableModel modeloTabla;

    public GUI_MostrarMesa() {
        super("Mostrar Mesa");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
    	if (context.getEvento() == Evento.MOSTRAR_MESA_OK) {
            TMesa mesa = (TMesa) context.getDatos();
            JOptionPane.showMessageDialog(this, "Mesa mostrada con éxito");
            actualizarTabla(mesa);
        } else if (context.getEvento() == Evento.MOSTRAR_MESA_KO) {
            JOptionPane.showMessageDialog(this, "Error al mostrar la mesa");
        }
    }

    private void initGUI() {
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelMesa = new JLabel("ID Mesa:");
        idMesa = new JTextField(10);

        JLabel labelTipoMesa = new JLabel("Tipo de Mesa:");
        tipoMesa = new JComboBox<>(new String[] { "Sala", "Terraza" });

        mostrar = new JButton("Mostrar Mesa");
        mostrar.addActionListener(e -> {
            try {
                String tipo = (String) tipoMesa.getSelectedItem();
                TMesa mesa;
                if ("Terraza".equalsIgnoreCase(tipo))
                    mesa = new TMesaTerraza();
                else
                    mesa = new TMesaSala();

                mesa.setId(Integer.parseInt(idMesa.getText()));

                Context contexto = new Context(Evento.MOSTRAR_MESA, mesa);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser un número válido");
            }
        });

        int y = 0;
        gbc.gridx = 0;
        gbc.gridy = y;
        panelSuperior.add(labelMesa, gbc);
        gbc.gridx = 1;
        panelSuperior.add(idMesa, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panelSuperior.add(labelTipoMesa, gbc);
        gbc.gridx = 1;
        panelSuperior.add(tipoMesa, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        panelSuperior.add(mostrar, gbc);

        add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {
            "Tipo", "ID", "Ubicación", "Número", "Capacidad",
            "Reservada", "Privacidad", "Cubierta", "Suplemento"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tablaMesa = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaMesa);
        add(scroll, BorderLayout.CENTER);
    }

    private void actualizarTabla(TMesa mesa) {
        modeloTabla.setRowCount(0); 

        if (mesa != null) {
            String tipo = mesa.getTipo();
            String id = String.valueOf(mesa.getId());
            String ubicacion = mesa.getUbicacion();
            String numero = String.valueOf(mesa.getNumero());
            String capacidad = String.valueOf(mesa.getCapacidad());

            String reservada = "N/A";
            String cubierta = "N/A";
            String privacidad = "N/A";
            String suplemento = "N/A";

            if (mesa instanceof TMesaSala) {
                TMesaSala sala = (TMesaSala) mesa;
                reservada = sala.getReservada() ? "Reservada" : "Sin reservar";
                privacidad = sala.getPrivacidad();
            } else if (mesa instanceof TMesaTerraza) {
                TMesaTerraza terraza = (TMesaTerraza) mesa;
                cubierta = terraza.getCubierta() ? "Cubierta" : "No cubierta";
                suplemento = String.valueOf(terraza.getSuplemento());
            }

            Object[] fila = {
                tipo,
                id,
                ubicacion,
                numero,
                capacidad,
                reservada,
                privacidad,
                cubierta,
                suplemento
            };

            modeloTabla.addRow(fila);
        }
    }
}
