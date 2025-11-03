package com.grupoms.app.negocio.factoria;

import com.grupoms.app.negocio.mesa.*;
import com.grupoms.app.negocio.proveedor.*;
import com.grupoms.app.negocio.pedido.*;
import com.grupoms.app.negocio.ingrediente.*;


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
