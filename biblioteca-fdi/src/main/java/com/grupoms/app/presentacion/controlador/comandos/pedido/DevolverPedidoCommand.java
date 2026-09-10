package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class DevolverPedidoCommand implements Command{

	@Override
	public Context execute(Object data) {
	    Integer idPedido = (Integer) data;
	    SAPedido sa = FactoriaSA.getInstance().creaSAPedido();

	    try {
	        Boolean ok = sa.devolverPedido(idPedido);

	        if (ok) {
	            return new Context(Evento.DEVOLVER_PEDIDO_OK, idPedido);
	        } else {
	            return new Context(Evento.DEVOLVER_PEDIDO_KO, idPedido);
	        }

	    } catch (Exception e) {
	        return new Context(Evento.DEVOLVER_PEDIDO_KO, e.getMessage());
	    }
	}


}
