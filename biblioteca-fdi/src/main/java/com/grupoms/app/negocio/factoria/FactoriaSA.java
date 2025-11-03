package com.grupoms.app.negocio.factoria;

import com.grupoms.app.negocio.mesa.SAMesa;
import com.grupoms.app.negocio.proveedor.SAProveedor;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;

public abstract class FactoriaSA {
	
	private static FactoriaSA instancia;
	
	public static FactoriaSA getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaSAImp();
		}
		return instancia;
	}
	
	public abstract SAProveedor creaSAProveedor();
	public abstract SAMesa creaSAMesa();
	public abstract SAPedido creaSAPedido();
	public abstract SAIngrediente creaSAIngrediente();

}
