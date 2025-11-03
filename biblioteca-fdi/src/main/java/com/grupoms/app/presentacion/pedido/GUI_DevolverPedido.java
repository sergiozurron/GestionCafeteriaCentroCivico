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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    public GUI_DevolverPedido(){
        super("Devolver Pedido");
        setTitle("[DEVOLVER PEDIDO]");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas
        JLabel labelId = new JLabel("ID del pedido:");
        campoID = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelId, gbc);
        gbc.gridx = 1;
        panel.add(campoID, gbc);
        JButton botonDevolver = new JButton("Devolver");
        botonDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idPedido = Integer.parseInt(campoIdPedido.getText());

                    Context contexto = new Context(Evento.DEVOLVER_PEDIDO, idPedido);
                    Controlador.getInstance().handle(contexto);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(GUI_DevolverPedido.this,
                            "Error: introduce un ID numérico válido",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
}
