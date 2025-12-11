package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAOrden;
import com.grupoms.app.negocio.pedido.TOrden;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaOrdenCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof TOrden)) {
			return new Context(Evento.ALTA_ORDEN_KO, null);
		}

		TOrden orden = (TOrden) data;
		SAOrden saOrden = FactoriaSA.getInstance().creaSAOrden();

		try {
			Integer resultado = saOrden.altaOrden(orden);
			if (resultado == null || resultado <= 0) {
				return new Context(Evento.ALTA_ORDEN_KO, null);
			}
			return new Context(Evento.ALTA_ORDEN_OK, orden);
		} catch (Exception e) {
			return new Context(Evento.ALTA_ORDEN_KO, null);
		}
	}
}
