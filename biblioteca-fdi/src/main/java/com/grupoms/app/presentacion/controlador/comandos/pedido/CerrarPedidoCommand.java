package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TCarrito;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class CerrarPedidoCommand implements Command {

	@Override
	public Context execute(Object data) {
		TCarrito carrito = (TCarrito) data;
		int res = FactoriaSA.getInstance().creaSAPedido().cerrarPedido(carrito);
		if(res>-1) {
			return new Context(Evento.CERRAR_PEDIDO_OK,res);
		}else {
			return new Context(Evento.CERRAR_PEDIDO_OK,res);

		}
	}
}
