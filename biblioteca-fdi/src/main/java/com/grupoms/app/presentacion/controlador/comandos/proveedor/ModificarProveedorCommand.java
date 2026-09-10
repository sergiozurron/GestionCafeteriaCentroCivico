package com.grupoms.app.presentacion.controlador.comandos.proveedor;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarProveedorCommand implements Command {

	@Override
	public Context execute(Object data) {

		try {
			TProveedor proveedor = (TProveedor) data;

			Boolean resultado = FactoriaSA.getInstance()
					.creaSAProveedor()
					.modificarProveedor(proveedor);

			if (resultado == null || !resultado) {
				return new Context(
						Evento.MODIFICAR_PROVEEDOR_KO,
						"No se pudo modificar el proveedor"
				);
			}

			return new Context(
					Evento.MODIFICAR_PROVEEDOR_OK,
					proveedor.getId()
			);

		} catch (Exception e) {
			return new Context(
					Evento.MODIFICAR_PROVEEDOR_KO,
					e.getMessage()
			);
		}
	}
}
