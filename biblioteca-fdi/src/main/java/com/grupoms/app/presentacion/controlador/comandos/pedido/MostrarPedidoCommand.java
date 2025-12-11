package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;

import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarPedidoCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer id = null;

		if (data instanceof Integer) {
			id = (Integer) data;
		} else if (data instanceof TPedido) {
			id = ((TPedido) data).getId();
		} else {
			return new Context(Evento.MOSTRAR_PEDIDO_KO, null);
		}

		SAPedido sa = FactoriaSA.getInstance().creaSAPedido();

		try {
			TPedido emp = sa.mostrarPedido(id);
			return (emp != null) ? new Context(Evento.MOSTRAR_PEDIDO_OK, emp)
					: new Context(Evento.MOSTRAR_PEDIDO_KO, null);
		} catch (IllegalArgumentException e) {
			return new Context(Evento.MOSTRAR_PEDIDO_KO, null);
		}
	}

}
