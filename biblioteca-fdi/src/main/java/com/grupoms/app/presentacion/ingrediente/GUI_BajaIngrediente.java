package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_BajaIngrediente extends JFrame implements IGUI{
    
    private JTextField campoID;
    private JButton devolver;

    @Override
    public void actualizar(Context context) {
        if(context.getEvento() == Evento.DEVOLVER_PEDIDO){
            JOptionPane.showMessageDialog(this,"Ingrediente dado de baja con exito");
            campoID.setText("");
        }
    }

    public GUI_BajaIngrediente(){
        super("Baja Ingrediente");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initGUI(){
         setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        setLayout(new BorderLayout()); //layout general

        JLabel labelIdPedido = new JLabel("ID Ingrediente:");
        campoID = new JTextField(10);

        devolver = new JButton("Baja Ingrediente");
        devolver.addActionListener(e->{
            try{
                int ing = Integer.parseInt(campoID.getText());
                TIngrediente ingrediente = new TIngrediente();
                ingrediente.setID(ing);

                Context contexto = new Context(Evento.BAJA_INGREDIENTE,ingrediente);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
            }
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelIdPedido, gbc);

        gbc.gridx = 1;
        panel.add(campoID, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(devolver, gbc);

        add(panel, BorderLayout.CENTER);
    }
}
    

