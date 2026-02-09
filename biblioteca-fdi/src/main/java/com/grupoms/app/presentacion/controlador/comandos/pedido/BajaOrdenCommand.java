package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SALineaVenta;
import com.grupoms.app.negocio.pedido.TLineaVenta;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaOrdenCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof TLineaVenta)) {
			return new Context(Evento.BAJA_ORDEN_KO, null);
		}

		TLineaVenta orden = (TLineaVenta) data;
		SALineaVenta saOrden = FactoriaSA.getInstance().creaSAOrden();

		try {
			saOrden.bajaOrden(orden);
			return new Context(Evento.BAJA_ORDEN_OK, orden);
		} catch (Exception e) {
			return new Context(Evento.BAJA_ORDEN_KO, null);
		}
	}

}
