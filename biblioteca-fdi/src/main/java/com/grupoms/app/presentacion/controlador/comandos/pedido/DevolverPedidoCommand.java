package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class DevolverPedidoCommand implements Command{

	@Override
	public Context execute(Object data) {
		Integer pedido = (Integer) data;
		SAPedido sa = FactoriaSA.getInstance().creaSAPedido();

		try {

			sa.devolverPedido(pedido);
			return new Context(Evento.ALTA_PEDIDO_OK, pedido);
		} catch (Exception e) {
			return new Context(Evento.ALTA_PEDIDO_KO, null);
		}
	}

}
