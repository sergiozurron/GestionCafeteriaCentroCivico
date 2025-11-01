package com.grupoms.app.negocio.factoria;

import com.grupoms.app.negocio.proveedor.SAProveedor;
import com.grupoms.app.negocio.proveedor.SAProveedorImpl;

public class FactoriaSAImpl extends FactoriaSA {

	public SAProveedor creaSAProveedor() {
		return new SAProveedorImpl();
	}

}
