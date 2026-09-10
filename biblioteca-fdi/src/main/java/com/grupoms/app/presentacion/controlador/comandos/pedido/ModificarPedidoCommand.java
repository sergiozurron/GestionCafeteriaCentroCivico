package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarPedidoCommand implements Command{

	@Override
	public Context execute(Object data) {
		TPedido pedido = (TPedido) data;
		SAPedido sa = FactoriaSA.getInstance().creaSAPedido();
		try {
			Boolean ok = sa.modificarPedido(pedido);
			if(ok) {
				return new Context(Evento.MODIFICAR_PEDIDO_OK,pedido);
			}
			else {
				return new Context(Evento.MODIFICAR_PEDIDO_KO, "No se pudo modificar el pedido");
			}
		}catch (Exception e) {
			return new Context(Evento.MODIFICAR_PEDIDO_KO, e.getMessage());
			}
	}

}
