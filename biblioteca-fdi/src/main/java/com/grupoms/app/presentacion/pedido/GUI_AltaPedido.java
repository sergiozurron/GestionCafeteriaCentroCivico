package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaPedido extends JFrame implements IGUI{

	private JTextField campoMesa;
    private JTextField campoEmpleado;
    
    private JButton crear;

    public GUI_AltaPedido(){
       super("Alta Pedido");
       initGUI(); //iniciamos el front por asi ddecirlo
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //destruye la ventana sin cerrar la app
       pack(); //ajusta
       setLocationRelativeTo(null); //centra
       setVisible(true); //es visible
    }
    @Override
    public void actualizar(Context context) {
          if (context.getEvento() == Evento.ALTA_PEDIDO) {
            // Muestra mensaje de éxito
            JOptionPane.showMessageDialog(this, "Pedido creado con éxito");
            // Limpia los campos para la siguiente entrada
            campoMesa.setText("");
            campoEmpleado.setText("");
        }
    }
    
    public void initGUI(){
        setLayout(new BorderLayout()); //layout general

        JPanel panel = new JPanel(new GridBagLayout()); //panel principal

        JLabel labelMesa = new JLabel("ID Mesa:");
        campoMesa = new JTextField(10);

        JLabel labelEmpleado = new JLabel("ID Empleado:");
        campoEmpleado = new JTextField(10);

        crear = new JButton("Crear Pedido");
        crear.addActionListener(e -> {
            try {
                int idMesa = Integer.parseInt(campoMesa.getText());
                int idEmpleado = Integer.parseInt(campoEmpleado.getText());

                // Crear el TPedido directamente aquí
                TPedido pedido = new TPedido();
                pedido.setIdMesa(idMesa);
                pedido.setIdEmpleado(idEmpleado);
                pedido.setTotal(0.0);
                pedido.setActivo(true);
                pedido.setEstado("ABIERTO");
                pedido.setFecha(new java.sql.Date(System.currentTimeMillis()));

                // Enviar al controlador
                Context contexto = new Context(Evento.ALTA_PEDIDO, pedido);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);        // Espaciado alrededor de los componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Cada componente se expande horizontalmente

        // Colocación de los componentes en la cuadrícula
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelMesa, gbc);

        gbc.gridx = 1;
        panel.add(campoMesa, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(labelEmpleado, gbc);

        gbc.gridx = 1;
        panel.add(campoEmpleado, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(crear, gbc);

        // Añade el panel principal al centro del JFrame
        add(panel, BorderLayout.CENTER);
    }
}
