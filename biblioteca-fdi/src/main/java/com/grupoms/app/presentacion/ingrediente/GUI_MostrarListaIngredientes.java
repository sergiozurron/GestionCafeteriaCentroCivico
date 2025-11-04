package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import java.awt.*;
import java.util.Set;

public class GUI_MostrarListaIngredientes extends JFrame implements IGUI{
    
    private JTextArea textArea;
    private JButton mostrar;

    public GUI_MostrarListaIngredientes() {
        super("Ingredientes ");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initGUI() {
        JPanel panel = new JPanel(new BorderLayout());
        
        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);

        mostrar = new JButton("Mostrar Ingredientes ");
        mostrar.addActionListener(e -> {
            // Creamos el Context con el evento
            Context contexto = new Context();
            contexto.setEvento(Evento.MOSTRAR_INGREDIENTES);
            contexto.setDato(null); // no necesitamos pasar dato
            
            Controlador.getInstance().handle(contexto);
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(mostrar, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if(context.getEvento() == Evento.MOSTRAR_INGREDIENTES_OK) {
            Set<TIngrediente> ingredientes = (Set<TIngrediente>) context.getDatos();
            if(ingredientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ingredientes activos en la base de datos");
                textArea.setText("");
            } else {
                StringBuilder sb = new StringBuilder();
                for(TIngrediente ing : ingredientes) {
                    sb.append("ID: ").append(ing.getID())
                      .append(", Nombre: ").append(ing.getNombre())
                      .append(", Precio: ").append(ing.getPrecio())
                      .append(", Proveedor: ").append(ing.getIDProveedor())
                      .append("\n");
                }
                textArea.setText(sb.toString());
            }
        } else if(context.getEvento() == Evento.MOSTRAR_INGREDIENTES_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar los ingredientes activos");
        }
    }
}
