package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarListaIngredientes extends JFrame implements IGUI {

    private JTextArea textArea;
    private JButton mostrar;

    public GUI_MostrarListaIngredientes() {
        super("Mostrar Lista Ingredientes");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true); // Se hace visible de inmediato, como las demás GUIs
    }

    private void initGUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Área de texto para mostrar ingredientes
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);

        // Botón para solicitar la lista de ingredientes activos
        mostrar = new JButton("Mostrar Ingredientes");
        mostrar.addActionListener(e -> {
            // Crear Context y llamar al controlador
            Context contexto = new Context();
            contexto.setEvento(Evento.MOSTRAR_INGREDIENTES);
            contexto.setDato(null); // No se necesita pasar dato
            Controlador.getInstance().handle(contexto);
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(mostrar, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.MOSTRAR_INGREDIENTES_OK) {
            Set<TIngrediente> ingredientes = (Set<TIngrediente>) context.getDatos();
            if (ingredientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ingredientes activos en la base de datos");
                textArea.setText("");
            } else {
                StringBuilder sb = new StringBuilder();
                for (TIngrediente ing : ingredientes) {
                    sb.append("ID: ").append(ing.getID())
                      .append(", Nombre: ").append(ing.getNombre())
                      .append(", Precio: ").append(ing.getPrecio())
                      .append(", Proveedor: ").append(ing.getIDProveedor())
                      .append("\n");
                }
                textArea.setText(sb.toString());
            }
        } else if (context.getEvento() == Evento.MOSTRAR_INGREDIENTES_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar los ingredientes activos");
        }
    }
}
