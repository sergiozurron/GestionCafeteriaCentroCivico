package com.grupoms.app.negocio.factoria;

import com.grupoms.app.negocio.proveedor.SAProveedor;

public abstract class FactoriaSA {
	
	private static FactoriaSA instancia;
	
	public static FactoriaSA getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaSAImpl();
		}
		return instancia;
	}
	
	public abstract SAProveedor creaSAProveedor();

}
