package com.grupoms.app.presentacion.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Command;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class AltaPedidoCommand implements Command{

    @Override
    public Context execute(Object data) {
        TPedido pedido = (TPedido) pedido;
		int idPedido = FactoriaSA.getInstancia().creaSAPedido().altaPedido(pedido);
		if (idPedido == -1)
			return new Context(Evento.ALTA_PEDIDO_KO, null);
		return new Context(Evento.ALTA_PEDIDO_OK, idPedido);
    }
    
}
