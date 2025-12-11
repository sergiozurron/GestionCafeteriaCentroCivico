package com.grupoms.app.presentacion.claseJPA;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarClase extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoID;
    private JButton mostrar;

    private JLabel tipoLabel;
    private JLabel fechaLabel;
    private JLabel duracionLabel;
    private JLabel salaLabel;
    private JLabel ejemplaresLabel; // Nuevo campo para mostrar los ejemplares

    public GUI_MostrarClase() {
        super("Mostrar Clase");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo ID
        JLabel labelID = new JLabel("ID Clase:");
        campoID = new JTextField(10);

        mostrar = new JButton("Mostrar Clase");
        mostrar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoID.getText().trim());
                // Pasamos directamente el ID como en MostrarSala (o encapsulado si tu controlador lo requiere)
                Context contexto = new Context(Evento.MOSTRAR_CLASE, id);
                Controlador.getInstance().handle(contexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
            }
        });

        // Inicializamos las etiquetas donde se mostrará la info
        tipoLabel = new JLabel();
        fechaLabel = new JLabel();
        duracionLabel = new JLabel();
        salaLabel = new JLabel();
        ejemplaresLabel = new JLabel();

        int y = 0;

        // Fila 0: Input ID
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelID, gbc);
        gbc.gridx = 1;
        panel.add(campoID, gbc);

        y++;
        // Fila 1: Botón
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(mostrar, gbc);

        y++;
        // Fila 2: Tipo
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        panel.add(tipoLabel, gbc);

        y++;
        // Fila 3: Fecha
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Fecha Inicio:"), gbc);
        gbc.gridx = 1;
        panel.add(fechaLabel, gbc);

        y++;
        // Fila 4: Duración
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 1;
        panel.add(duracionLabel, gbc);

        y++;
        // Fila 5: ID Sala
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("ID Sala:"), gbc);
        gbc.gridx = 1;
        panel.add(salaLabel, gbc);
        
        y++;
        // Fila 6: Ejemplares
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("IDs Ejemplares:"), gbc);
        gbc.gridx = 1;
        panel.add(ejemplaresLabel, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }

        switch (context.getEvento()) {
        case Evento.MOSTRAR_CLASE_OK:
            TClase c = (TClase) context.getDatos();
            if (c != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                
                tipoLabel.setText(c.getTipo());
                fechaLabel.setText(c.getFechaInicio() != null ? sdf.format(c.getFechaInicio()) : "N/A");
                duracionLabel.setText(String.valueOf(c.getDuracion()));
                salaLabel.setText(c.getIdSala() != null ? String.valueOf(c.getIdSala()) : "N/A");
                
                // Mostrar IDs de ejemplares si existen
                if (c.getEjemplares() != null && !c.getEjemplares().isEmpty()) {
                    ejemplaresLabel.setText(c.getEjemplares().toString().replace("[", "").replace("]", ""));
                } else {
                    ejemplaresLabel.setText("");
                }
            }
            break;
        case Evento.MOSTRAR_CLASE_KO:
            JOptionPane.showMessageDialog(this, "Clase no encontrada en la base de datos");
            tipoLabel.setText("");
            fechaLabel.setText("");
            duracionLabel.setText("");
            salaLabel.setText("");
            ejemplaresLabel.setText("");
            break;
        }
    }
}