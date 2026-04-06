package com.grupoms.app.presentacion.claseJPA;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI_ListarClasesPorSala extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField idSala;
    private JButton verClases;
    private JTextArea resultado;

    public GUI_ListarClasesPorSala() {
        super("Listar Clases por Sala");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ---- 1) Input ID Sala ----
        JLabel labelIdSala = new JLabel("ID Sala:");
        idSala = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelIdSala, gbc);

        gbc.gridx = 1;
        panel.add(idSala, gbc);

        // ---- 2) Button ----
        verClases = new JButton("Ver Clases");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(verClases, gbc);

        // ---- 3) Output area ----
        resultado = new JTextArea(10, 40);
        resultado.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultado);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        add(panel, BorderLayout.CENTER);

        // ---- 4) Action Listener ----
        verClases.addActionListener(e -> {
            String id = idSala.getText().trim();
            resultado.setText("");

            Context context = new Context(Evento.LISTAR_CLASES_POR_SALA, id);
            Controlador.getInstance().handle(context);
        });
    }

    @Override
    public void actualizar(Context context) {

        if (context == null) {
            setVisible(true);
            return;
        }

        switch (context.getEvento()) {

            case Evento.LISTAR_CLASES_POR_SALA_OK:

                @SuppressWarnings("unchecked")
                List<TClase> clases = (List<TClase>) context.getDatos();

                StringBuilder mensaje = new StringBuilder();

                if (clases.isEmpty()) {
                    mensaje.append("No hay clases activas para esta sala.");
                } else {
                    for (TClase c : clases) {

                        String activo = c.getActivo() ? "Sí" : "No";

                        mensaje.append("ID Clase: ").append(c.getId())
                               .append(" | Tipo: ").append(c.getTipo())
                               .append(" | Fecha Inicio: ").append(c.getFechaInicio())
                               .append(" | Duración: ").append(c.getDuracion())
                               .append(" | Activo: ").append(activo)
                               .append("\n----------------------------------------------------\n");
                    }
                }

                resultado.setText(mensaje.toString());
                break;

            case Evento.LISTAR_CLASES_POR_SALA_KO:

                resultado.setText("Error al buscar clases para esta sala.");
                JOptionPane.showMessageDialog(
                        this,
                        "Error al obtener clases",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                break;
        }
    }
}