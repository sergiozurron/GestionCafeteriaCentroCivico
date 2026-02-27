package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class CerrarPedidoCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer idPedido = (Integer) data;
		SAPedido sa = FactoriaSA.getInstance().creaSAPedido();

		try {

			TPedido pedido = sa.cerrarPedido(idPedido);
			
			if(pedido != null) {
				return new Context(Evento.CERRAR_PEDIDO_OK,pedido);
			}else {
				return new Context(Evento.CERRAR_PEDIDO_KO,null);
			}
			

		} catch (IllegalArgumentException e) {
			return new Context(Evento.CERRAR_PEDIDO_KO, null);
		}
	}

}
