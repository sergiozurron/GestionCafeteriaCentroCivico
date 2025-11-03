package com.grupoms.app.presentacion.proveedor;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaProveedorCommand implements Command {

	@Override
	public Context execute(Object data) {
		TProveedor proveedor = (TProveedor) data;
		int idProveedor = FactoriaSA.getInstancia().creaSAProveedor().altaProveedor(proveedor);
		if (idProveedor == -1)
			return new Context(Evento.ALTA_PROVEEDOR_KO, null);
		return new Context(Evento.ALTA_PROVEEDOR_OK, idProveedor);
	}

}
