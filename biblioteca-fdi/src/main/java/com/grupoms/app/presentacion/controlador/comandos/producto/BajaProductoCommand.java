package com.grupoms.app.presentacion.controlador.comandos.producto;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaProductoCommand implements Command {

	@Override
	public Context execute(Object data) {
		TProducto p = (TProducto) data;
		try {
			Boolean resultado = FactoriaSA.getInstance().creaSAProducto().bajaProducto(p);
			if (resultado == null || !resultado) {
				return new Context(Evento.BAJA_PRODUCTO_KO, "No se pudo dar de baja el producto");
			}
			return new Context(Evento.BAJA_PRODUCTO_OK, p);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Context(Evento.BAJA_PRODUCTO_KO, e.getMessage());
		}
	}

}
