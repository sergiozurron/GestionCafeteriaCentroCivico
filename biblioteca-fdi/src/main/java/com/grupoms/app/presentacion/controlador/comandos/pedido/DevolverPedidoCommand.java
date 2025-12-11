package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class DevolverPedidoCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof TPedido)) {
			return new Context(Evento.DEVOLVER_PEDIDO_KO, "El objeto recibido no es un TPedido válido.");
		}

		TPedido pedido = (TPedido) data;
		SAPedido sa = FactoriaSA.getInstance().creaSAPedido();

		try {
			sa.devolverPedido(pedido);
			return new Context(Evento.DEVOLVER_PEDIDO_OK, pedido);
		} catch (Exception e) {
			e.printStackTrace();
			return new Context(Evento.DEVOLVER_PEDIDO_KO, e.getMessage());
		}
	}

}
