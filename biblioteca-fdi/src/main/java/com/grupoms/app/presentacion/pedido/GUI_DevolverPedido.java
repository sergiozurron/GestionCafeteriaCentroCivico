package com.grupoms.app.presentacion.pedido;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_DevolverPedido extends JFrame implements IGUI{

    private JTextField campoID;

    @Override
    public void actualizar(Context context) {
        if(context.getEvento() == Evento.DEVOLVER_PEDIDO){
            JOptionPane.showMessageDialog(this,"Pedido devuelto con exito");
        }
    }

    public GUI_DevolverPedido(){
        super("Devolver Pedido");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initGUI(){
        
    }
    
}
