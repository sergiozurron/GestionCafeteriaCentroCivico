package com.grupoms.app.presentacion.pedido;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoms.app.negocio.pedido.TCarrito;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_CerrarPedido extends JFrame implements IGUI{

	private JTextField campoIdProducto;
	private JTextField campoCantidad;
	private JButton botonAceptar;
	private static final long serialVersionUID = 1L;
	private TCarrito carrito;

	public GUI_CerrarPedido(TCarrito carrito) {
		setTitle("Cerrar Pedido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);
		initGUI();
	}
	private void initGUI() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		TPedido pedido = new TPedido();
		
		//ETIQUETA PRODUCTO
		JLabel idProducto =  new JLabel("ID Producto: ");
		campoIdProducto = new JTextField(10);
		
		//ETIQUETA CANTIDAD
		JLabel cantidad = new JLabel("Cantidad: ");
		campoCantidad = new JTextField(10);
		
		//BOTON AÑADIR
	}
	@Override
	public void actualizar(Context context) {
		int resultado = (int)context.getDatos();
		if(context.getEvento() == Evento.CERRAR_PEDIDO_OK) {
			JOptionPane.showMessageDialog(this,"Pedido creado con exito");
		}else if(context.getEvento()==Evento.CERRAR_PEDIDO_KO) {
			switch(resultado) {
				case -1:
	                JOptionPane.showMessageDialog(this, "Se ha producido un error");
	                break;
	            case -2:
	                JOptionPane.showMessageDialog(this, "Datos incorrectos");
	                break;
	            default:
	                JOptionPane.showMessageDialog(this, "Error desconocido al cerrar el pedido.");
	                break;
            }
		}
	}
}


