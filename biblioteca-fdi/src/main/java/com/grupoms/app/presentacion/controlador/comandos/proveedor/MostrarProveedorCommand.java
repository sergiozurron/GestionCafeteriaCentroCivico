package com.grupoms.app.presentacion.controlador.comandos.proveedor;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarProveedorCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer idProveedor = (Integer) data;
		try {
			TProveedor proveedor = FactoriaSA.getInstance().creaSAProveedor().mostrarProveedor(idProveedor);
			if (proveedor == null)
				return new Context(Evento.MOSTRAR_PROVEEDOR_KO, null);
			return new Context(Evento.MOSTRAR_PROVEEDOR_OK, proveedor);
		} catch (Exception e) {
			return new Context(Evento.MOSTRAR_PROVEEDOR_KO, null);
		}
	}

}

