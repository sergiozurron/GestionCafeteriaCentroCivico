package com.grupoms.app.presentacion.controlador.comandos.producto;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.producto.SAProducto;
import com.grupoms.app.negocio.producto.TProducto;

import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaProductoCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof TProducto)) {
			return new Context(Evento.ALTA_PRODUCTO_KO, "Los datos no son válidos");
		}

		TProducto producto = (TProducto) data;
		SAProducto saProducto = FactoriaSA.getInstance().creaSAProducto();
		try {
			Integer idGenerado = saProducto.altaProducto(producto);
			if (idGenerado != null && idGenerado > 0) {
				producto.setId(idGenerado);
				return new Context(Evento.ALTA_PRODUCTO_OK, producto);
			} else
				return new Context(Evento.ALTA_PRODUCTO_KO, "No se pudo crear el producto");
		} catch (Exception e) {
			return new Context(Evento.ALTA_PRODUCTO_KO, e.getMessage());
		}
	}
}
