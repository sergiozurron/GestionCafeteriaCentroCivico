package com.grupoms.app.presentacion.proveedor;

import com.grupoms.app.negocio.TProveedor;
import com.grupoms.app.presentacion.Command;
import com.grupoms.app.presentacion.Context;

public class AltaProveedorCommand implements Command {

	@Override
	public Context execute(Object data) {
		TProveedor proveedor = (TProveedor) data;
		// FactoriaSA ...
		return null;
	}

}
