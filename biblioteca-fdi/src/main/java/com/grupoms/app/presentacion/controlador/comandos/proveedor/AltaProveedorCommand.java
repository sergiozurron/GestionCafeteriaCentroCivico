package com.grupoms.app.presentacion.controlador.comandos.proveedor;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaProveedorCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (!(data instanceof TProveedor)) {
			return new Context(Evento.ALTA_PROVEEDOR_KO, "Datos inválidos");
		}

		TProveedor proveedor = (TProveedor) data;

		try {
			int idProveedor = FactoriaSA.getInstance()
					.creaSAProveedor()
					.altaProveedor(proveedor);

			if (idProveedor > 0) {
				return new Context(Evento.ALTA_PROVEEDOR_OK, idProveedor);
			} else {
				return new Context(Evento.ALTA_PROVEEDOR_KO,
						"No se pudo dar de alta el proveedor");
			}

		} catch (Exception e) {
			return new Context(Evento.ALTA_PROVEEDOR_KO, e.getMessage());
		}
	}
}