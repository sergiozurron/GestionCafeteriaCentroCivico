package com.grupoms.app.presentacion.factoria;

import java.util.Map;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.pedido.GUI_AltaPedido;
import com.grupoms.app.presentacion.proveedor.GUI_AltaProveedor;

public class FactoriaVistas {
	
	public static final String GUI_ALTA_PROVEEDOR = "gui-alta-proveedor";
	public static final String GUI_ALTA_PEDIDO = "gui-alta-pedido";
	//añadir todos los GUI
	public Map<String, IGUI> vistas = Map.of(
			GUI_ALTA_PROVEEDOR, new GUI_AltaProveedor(),
			GUI_ALTA_PEDIDO, new GUI_AltaPedido()
			);
	
	private static FactoriaVistas instance;
	
	public static FactoriaVistas getInstance() {
		if (instance == null)
			instance = new FactoriaVistas();
		return instance;
	}
	
	public IGUI creaVista(String nombreVista) {
		return vistas.get(nombreVista);
	}

}
