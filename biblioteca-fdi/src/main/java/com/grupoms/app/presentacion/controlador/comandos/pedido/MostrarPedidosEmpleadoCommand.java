package com.grupoms.app.presentacion.controlador.comandos.pedido;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarPedidosEmpleadoCommand implements Command{
	@Override
	public Context execute(Object data) {
		Integer idEmpleado = (Integer) data;
		SAPedido sa = FactoriaSA.getInstance().creaSAPedido();
		try {
			List<TPedido> lista = sa.mostrarPedidosPorEmpleado(idEmpleado);
			if(lista!=null) {
				return new Context(Evento.MOSTRAR_PEDIDOS_EMPLEADO_OK,lista);
			}
			else {
				return new Context(Evento.MOSTRAR_PEDIDOS_EMPLEADO_KO, "No se pudo mostrar los pedidos del empleado");
			}
		}catch (Exception e) {
			return new Context(Evento.MOSTRAR_PEDIDOS_EMPLEADO_KO, e.getMessage());
		}
	}
}
