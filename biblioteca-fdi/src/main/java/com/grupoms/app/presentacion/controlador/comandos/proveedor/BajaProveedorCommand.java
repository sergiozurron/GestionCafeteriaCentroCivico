package com.grupoms.app.presentacion.controlador.comandos.proveedor;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaProveedorCommand implements Command {

	@Override
	public Context execute(Object data) {
		TProveedor proveedor = (TProveedor) data;
		Boolean resultado = FactoriaSA.getInstance().creaSAProveedor().bajaProveedor(proveedor);
		if (resultado == null || !resultado)
			return new Context(Evento.BAJA_PROVEEDOR_KO, null);
		return new Context(Evento.BAJA_PROVEEDOR_OK, proveedor.getId());
	}

}