package com.grupoms.app.presentacion.controlador.comandos.proveedor;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.proveedor.TProveedor;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarListaProveedoresCommand implements Command {

	@Override
	public Context execute(Object data) {
		try {
			List<TProveedor> listaProveedores = FactoriaSA.getInstance().creaSAProveedor().mostrarListaProveedores();
			if (listaProveedores == null || listaProveedores.isEmpty())
				return new Context(Evento.MOSTRAR_LISTA_PROVEEDOR_KO, null);
			return new Context(Evento.MOSTRAR_LISTA_PROVEEDOR_OK, listaProveedores);
		} catch (Exception e) {
			return new Context(Evento.MOSTRAR_LISTA_PROVEEDOR_KO, null);
		}
	}

}
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

