package com.grupoms.app.presentacion.controlador.comandos.pedido;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarPedidosMesaCommand implements Command{
	@Override
	public Context execute(Object data) {
		Integer idMesa = (Integer) data;
		SAPedido sa = FactoriaSA.getInstance().creaSAPedido();
		try {
			List<TPedido> lista = sa.mostrarPedidosPorMesa(idMesa);
			if(lista!=null) {
				return new Context(Evento.MOSTRAR_PEDIDOS_MESA_OK,lista);
			}
			else {
				return new Context(Evento.MOSTRAR_PEDIDOS_MESA_KO,null);
			}
		}catch (IllegalArgumentException e) {
			return new Context(Evento.MOSTRAR_PEDIDOS_MESA_KO,null);
		}
	}
}
