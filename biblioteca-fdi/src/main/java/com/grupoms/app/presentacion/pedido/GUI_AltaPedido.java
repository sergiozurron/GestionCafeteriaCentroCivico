package com.grupoms.app.presentacion.pedido;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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



		campoTotal = new JTextField(20);
		campoFecha = new JTextField(20);
		campoMesa = new JTextField(20);
        campoEmpleado = new JTextField(20);
        campoEstado = new JTextField(20);

		JButton botonAlta = new JButton("Alta Proveedor");
		
		botonAlta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TProveedor proveedor = new TProveedor();
				pedido.setTotal(campoTotal.getText());
				pedido.setTarifa(Double.parseDouble(campoTarifa.getText()));
				proveedor.setTiempoEntrega(Integer.parseInt(campoTiempoEntrega.getText()));
				Context contexto = new Context(Evento.ALTA_PROVEEDOR, proveedor);
				Controlador.getInstance().handle(contexto);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(etiquetaNombre, gbc);
		gbc.gridx = 1;
		panel.add(campoNombre, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(etiquetaTarifa, gbc);
		gbc.gridx = 1;
		panel.add(campoTarifa, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(etiquetaTiempoEntrega, gbc);
		gbc.gridx = 1;
		panel.add(campoTiempoEntrega, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
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

    }
}
