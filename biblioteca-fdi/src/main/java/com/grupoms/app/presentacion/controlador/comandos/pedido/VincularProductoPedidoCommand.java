package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SALineaPedido;
import com.grupoms.app.negocio.pedido.TLineaPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class VincularProductoPedidoCommand implements Command {

	@Override
	public Context execute(Object data) {
		TLineaPedido lineaPedido = (TLineaPedido) data;
		SALineaPedido sa = FactoriaSA.getInstance().creaSALineaPedido();

		try {
			Integer resultado = sa.altaLineaPedido(lineaPedido);

			if (resultado != null && resultado > 0) {
				return new Context(Evento.VINCULAR_PRODUCTO_PEDIDO_OK, resultado);
			} else {
				return new Context(Evento.VINCULAR_PRODUCTO_PEDIDO_KO, 
						"No se pudo vincular el producto al pedido");
			}

		} catch (Exception e) {
			return new Context(Evento.VINCULAR_PRODUCTO_PEDIDO_KO, e.getMessage());
		}
	}
}