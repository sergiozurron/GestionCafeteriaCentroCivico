package com.grupoms.app.presentacion.controlador.comandos.pedido;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarPedidoCommand implements Command {
	public Context execute(Object data) {
		SAPedido sa = FactoriaSA.getInstance().creaSAPedido();
		try {
			List<TPedido> pedidos = sa.mostrarPedidos();
			return new Context(Evento.MOSTRAR_PEDIDOS_OK, pedidos);
		} catch (Exception e) {
			return new Context(Evento.MOSTRAR_PEDIDOS_KO, null);
		}
	}
}
