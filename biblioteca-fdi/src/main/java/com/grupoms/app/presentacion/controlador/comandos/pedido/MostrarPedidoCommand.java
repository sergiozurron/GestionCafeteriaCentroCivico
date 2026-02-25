package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;

import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.negocio.pedido.TPedidoLinea;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarPedidoCommand implements Command {

	@Override
	public Context execute(Object data) {
		TPedidoLinea res = FactoriaSA.getInstance().creaSAPedido().mostrarPedidoPorID((Integer) data);
		TPedido pedido = res.gettPedido();
		if(pedido.getId()<=0) {
			return new Context(Evento.MOSTRAR_PEDIDO_KO, res);
		} else  {
			return new Context(Evento.MOSTRAR_PEDIDO_OK, res);
		}
	}

}
