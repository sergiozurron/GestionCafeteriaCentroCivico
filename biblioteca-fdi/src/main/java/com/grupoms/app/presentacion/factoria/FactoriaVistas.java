package com.grupoms.app.presentacion.factoria;

import java.util.HashMap;
import java.util.Map;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.pedido.GUI_AltaPedido;
import com.grupoms.app.presentacion.proveedor.GUI_AltaProveedor;

public class FactoriaVistas {
	
	public static final String GUI_ALTA_PROVEEDOR = "gui-alta-proveedor";
	public static final String GUI_ALTA_PEDIDO = "gui-alta-pedido";
	//añadir todos los GUI
	private static FactoriaVistas instance;

	private Map<String, IGUI> vistas = new HashMap<>();
	
	private FactoriaVistas(){
		//cada GUI que añadamos hayq ue inicializarla aqui
		vistas.put(GUI_ALTA_PEDIDO, new GUI_AltaPedido());
		vistas.put(GUI_ALTA_PROVEEDOR,new GUI_AltaProveedor());
		//vistas.put(GUI_ALTA_MESA, new GUI_AltaMesa());
	}
	
	public static FactoriaVistas getInstance() {
		if (instance == null)
			instance = new FactoriaVistas();
		return instance;
	}
	
	public IGUI creaVista(String nombreVista) {
		return vistas.get(nombreVista);
	}

}
