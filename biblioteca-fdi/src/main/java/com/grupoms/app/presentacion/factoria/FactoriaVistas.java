package com.grupoms.app.presentacion.factoria;

import java.util.Map;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.proveedor.GUIAltaProveedor;

public class FactoriaVistas {
	
	public static final String GUI_ALTA_PROVEEDOR = "gui-alta-proveedor";
	
	public Map<String, IGUI> vistas = Map.of(
			GUI_ALTA_PROVEEDOR, new GUIAltaProveedor()
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
