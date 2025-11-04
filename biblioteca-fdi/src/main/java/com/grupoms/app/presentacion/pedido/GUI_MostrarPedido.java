package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarPedido extends JFrame implements IGUI{
    private JTextField campoPedido;
    private JTextArea areaRes;
    
    private JButton mostrar;

    public GUI_MostrarPedido(){
       super("MostrarPedido");
       initGUI(); //iniciamos el front por asi ddecirlo
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //destruye la ventana sin cerrar la app
       pack(); //ajusta
       setLocationRelativeTo(null); //centra
       setVisible(true); //es visible
    }
    @Override
    public void actualizar(Context context) {
          switch (context.getEvento()) {
            case Evento.MOSTRAR_PEDIDO_OK:
                TPedido pedido = (TPedido) context.getDatos();
                areaRes.setText(
                        "ID Pedido: " + pedido.getId() + "\n" +
                        "ID Mesa: " + pedido.getIdMesa() + "\n" +
                        "ID Empleado: " + pedido.getIdEmpleado() + "\n" +
                        "Estado: " + pedido.getEstado() + "\n" +
                        "Fecha: " + pedido.getFecha() + "\n" +
                        "Total: " + pedido.getTotal() + "\n" +
                        "Activo: " + pedido.getActivo()
                );
                break;
            case Evento.MOSTRAR_PEDIDO_KO:
                String msg = (String) context.getDatos();
                JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
    
    public void initGUI(){
        setLayout(new BorderLayout()); //layout general

        JPanel panel = new JPanel(new GridBagLayout()); //panel principal
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelId = new JLabel("ID Pedido:");
        campoPedido = new JTextField(10);

        mostrar = new JButton("Mostrar Pedido");
        mostrar.addActionListener(e -> {
            try {
                int idPedido = Integer.parseInt(campoPedido.getText());
                // Enviar al controlador
                Context contexto = new Context(Evento.MOSTRAR_PEDIDO, idPedido);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser un número entero");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelId, gbc);
        gbc.gridx = 1;
        panel.add(campoPedido, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(mostrar, gbc);

        add(panel, BorderLayout.NORTH);

        areaRes = new JTextArea(10, 30);
        areaRes.setEditable(false);
        add(new JScrollPane(areaRes), BorderLayout.CENTER);
    }
}
