package com.grupoms.app.presentacion.pedido;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ConfirmarPedido extends JFrame implements IGUI{
    private JButton confirmar;
    private JTextField campoIdPedido;

    public GUI_ConfirmarPedido(){
       super("Confirmar Pedido");
       initGUI(); //iniciamos el front por asi ddecirlo
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //destruye la ventana sin cerrar la app
       pack(); //ajusta
       setLocationRelativeTo(null); //centra
       setVisible(true); //es visible
    }

    @Override
    public void actualizar(Context context) {
        if(context.getEvento() == Evento.CONFIRMAR_PEDIDO){
            JOptionPane.showMessageDialog(this, "Pedido confirmado y en preparación");
            campoIdPedido.setText("");
         } // Limpiar campo        
    }

    public void initGUI(){
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        setLayout(new BorderLayout()); //layout general

        JLabel labelIdPedido = new JLabel("ID Pedido:");
        campoIdPedido = new JTextField(10);

        confirmar = new JButton("Confirmar Pedido");
        confirmar.addActionListener(e->{
            try {
                int idPedido = Integer.parseInt(campoIdPedido.getText());
                TPedido pedido = new TPedido();
                pedido.setId(idPedido);

                // Creamos el contexto y llamamos al controlador
                Context contexto = new Context(Evento.CONFIRMAR_PEDIDO, pedido);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID del pedido no es válido");
            }
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelIdPedido, gbc);

        gbc.gridx = 1;
        panel.add(campoIdPedido, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(confirmar, gbc);

        add(panel, BorderLayout.CENTER);
    }
    
}
