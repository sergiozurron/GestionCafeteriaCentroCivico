package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SALineaPedido;
import com.grupoms.app.negocio.pedido.TLineaPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class DesvincularProductoPedidoCommand implements Command {

	@Override
	public Context execute(Object data) {
		TLineaPedido lineaPedido = (TLineaPedido)data;
		SALineaPedido sa = FactoriaSA.getInstance().creaSALineaPedido();
		try{
			Integer idGenerado = sa.bajaLineaPedido(lineaPedido.getPedidoId(),lineaPedido.getProductoId());
			if (idGenerado != null && idGenerado > 0) {
				lineaPedido.setId(idGenerado);
				return new Context(Evento.DESVINCULAR_PRODUCTO_PEDIDO_OK, lineaPedido);
			} else {
				return new Context(Evento.DESVINCULAR_PRODUCTO_PEDIDO_KO, null);
			}
		}catch (IllegalArgumentException e) {
			return new Context(Evento.DESVINCULAR_PRODUCTO_PEDIDO_KO, null);
		}
	}

}
