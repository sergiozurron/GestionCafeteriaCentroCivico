package com.grupoms.app.negocio.factoria;

import com.grupoms.app.negocio.mesa.*;
import com.grupoms.app.negocio.proveedor.*;
import com.grupoms.app.negocio.pedido.*;
import com.grupoms.app.negocio.empleado.*;
import com.grupoms.app.negocio.ingrediente.*;
import com.grupoms.app.negocio.materialJPA.*;
import com.grupoms.app.negocio.producto.*;
import com.grupoms.app.negocio.PromocionJPA.*;


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

	@Override
	public SAProducto creaSAProducto() {
		return new SAProductoImp();
	}

	@Override
	public SAOrden creaSAOrden() {
		return new SAOrdenImp();
	}

	@Override
	public SAEmpleado creaSAEmpleado() {
		return new SAEmpleadoImp();
	}

	// JPA

	@Override
	public MaterialSA creaSAMaterial() {
		return new MaterialSAImp();
	}

	public PromocionSA creaSAPromocion() {
		return new PromocionSAImp();
	}
}
