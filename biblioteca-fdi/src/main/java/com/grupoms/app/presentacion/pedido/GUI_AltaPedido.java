package com.grupoms.app.presentacion.pedido;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaPedido extends JFrame implements IGUI{

    private JTextField campoTotal;
	private JTextField campoMesa;
    private JTextField campoEmpleado;
    private JTextField campoEstado;



    public GUI_AltaPedido(){
        setTitle("[ALTA PEDIDO]");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas
        JLabel labelTotal = new JLabel("Total:");
        JLabel labelMesa = new JLabel("ID Mesa:");
        JLabel labelEmpleado = new JLabel("ID Empleado:");
        JLabel labelEstado = new JLabel("Estado:");



		campoTotal = new JTextField("0.0");
        campoTotal.setEditable(false); //no lo puede editar manualmente
		campoMesa = new JTextField(20);
        campoEmpleado = new JTextField(20);
        campoEstado = new JTextField(20);

		JButton botonAlta = new JButton("Alta Pedido");
		
		botonAlta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                try{
				TPedido pedido = new TPedido();
				pedido.setTotal(0.0);
				pedido.setMesa(Integer.parseInt(campoMesa.getText()));
				pedido.setEmplead(Integer.parseInt(campoEmpleado.getText()));
				pedido.setEstado(campoEstado.getText());
                pedido.setActivo(true);
                pedido.setFecha(LocalDate.now());
                
                
                Context contexto = new Context(Evento.ALTA_PROVEEDOR, proveedor);
				Controlador.getInstance().handle(contexto);

                JOptionPane.showConfirmDialog(null, "Pedido creado con exito")

                } catch (NumberFormatException ex){
                    JOptionPane.showConfirmDialog(null, "Error: los campos numericos no son validos")
                }

			}
		});

		// Layout con GridBag
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelTotal, gbc);
        gbc.gridx = 1;
        panel.add(campoTotal, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(labelMesa, gbc);
        gbc.gridx = 1;
        panel.add(campoMesa, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(labelEmpleado, gbc);
        gbc.gridx = 1;
        panel.add(campoEmpleado, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(labelEstado, gbc);
        gbc.gridx = 1;
        panel.add(campoEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(botonAlta, gbc);


		add(panel);
    }
    @Override
    public void actualizar(Context context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }
    
    public void initGUI(){
        setVisible(true);
    }
}
