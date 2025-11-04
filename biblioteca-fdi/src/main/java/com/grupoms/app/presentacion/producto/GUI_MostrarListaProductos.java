package com.grupoms.app.presentacion.producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Set;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarListaProductos extends JFrame implements IGUI{

    private JTextArea textArea;
    private JButton mostrar;

    public GUI_MostrarListaProductos() {
        super("Mostrar Lista de Productos");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //destruye la ventana sin cerrar la app
        pack(); //ajusta
        setLocationRelativeTo(null); //centra
        setVisible(true); //es visible
    }

    private void initGUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Área de texto para mostrar productos
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);

        // Botón para solicitar la lista de productos activos
        mostrar = new JButton("Mostrar productos");
        mostrar.addActionListener(e -> {
            // Crear Context y llamar al controlador
            Context contexto = new Context();
            contexto.setEvento(Evento.MOSTRAR_LISTA_PRODUCTO);
            contexto.setDato(null);
            Controlador.getInstance().handle(contexto);
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(mostrar, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.MOSTRAR_LISTA_PRODUCTO_OK) {
            Set<TProducto> productos = (Set<TProducto>) context.getDatos();
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos activos en la base de datos");
                textArea.setText("");
            } else {
                StringBuilder sb = new StringBuilder();
                for (TProducto p : productos) {
                    sb.append("ID: ").append(p.getId())
                      .append(", Nombre: ").append(p.getNombre())
                      .append(", Precio: ").append(p.getPrecio())
                      .append(", Stock: ").append(p.getStock())
                      .append(", Tipo: ").append(p.getTipo())
                      .append("\n");
                }
                textArea.setText(sb.toString());
            }
        } else if (context.getEvento() == Evento.MOSTRAR_LISTA_PRODUCTO_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos activos");
        }
    }
}