package com.grupoms.app.negocio.factoria;

import com.grupoms.app.negocio.mesa.SAMesa;
import com.grupoms.app.negocio.mesa.SAMesaImp;
import com.grupoms.app.negocio.proveedor.SAProveedor;
import com.grupoms.app.negocio.proveedor.SAProveedorImpl;

public class FactoriaSAImp extends FactoriaSA {

	public SAProveedor creaSAProveedor() {
		return new SAProveedorImpl();
	}
	
	public SAMesa creaSAMesa() {
		return new SAMesaImp();
	}

	@Override
	public SAPedido creaSAPedido() {
		return new SAPedidoImp();
	}

	@Override
	public SAIngrediente creaSAIngrediente() {
		return new SAIngredienteImp();
	}

}
