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


