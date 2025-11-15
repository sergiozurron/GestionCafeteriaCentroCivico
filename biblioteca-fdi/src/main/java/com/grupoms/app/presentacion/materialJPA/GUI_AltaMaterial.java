package com.grupoms.app.presentacion.materialJPA;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;

public class GUI_AltaMaterial extends JFrame implements IGUI{
	
	private JTextField campoAutor, campoEditorial, isbn, numero;
	private 
	private JButton aceptar;
	
	private JButton cancelar;
	
	public GUI_AltaMaterial() {
		super("Alta Material");
		initGUI();
		pack();
        setLocationRelativeTo(null);
	}
	private void initGUI() {
		setLayout(new BorderLayout());
	    JPanel panel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5,5,5,5);
	    gbc.fill = GridBagConstraints.HORIZONTAL;
		
	    
	    //Campos comunes
	    
	    JLabel labelAutor = new JLabel("Autor:");
	    campoAutor = new JTextField(20);
	    panel.add(labelAutor);
	    
	    panel.add(Box.createRigidArea(new Dimension(0,10)));
	    JLabel labelTipoProducto = new JLabel("Tipo del Producto: ");
	    panel.add(labelTipoProducto);
	    
	    JComboBox<String> tipoMaterial = new JComboBox<String>();
	    tipoMaterial.addItem("Pintura");
	    tipoMaterial.addItem("Libro");
	    tipoMaterial.setMaximumSize(tipoMaterial.getPreferredSize());
	    panel.add(tipoMaterial);
	}
	@Override
	public void actualizar(Context context) {
		// TODO Auto-generated method stub
		
	}

}
