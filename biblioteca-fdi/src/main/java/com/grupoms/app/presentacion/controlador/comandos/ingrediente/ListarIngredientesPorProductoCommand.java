package com.grupoms.app.presentacion.controlador.comandos.ingrediente;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;
import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.negocio.producto.TEntradaReceta;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarIngredientesPorProductoCommand implements Command {

	@Override
	public Context execute(Object data) {
		SAIngrediente saIngrediente = FactoriaSA.getInstance().creaSAIngrediente();
		try {
			List<TEntradaReceta> productos = saIngrediente.mostrarIngredientesPorProducto((Integer) data);
			return new Context(Evento.LISTAR_INGREDIENTES_POR_PRODUCTO_OK, productos);
		} catch (Exception e) {
			return new Context(Evento.LISTAR_INGREDIENTES_POR_PRODUCTO_KO, e.getMessage());
		}
	}

}
