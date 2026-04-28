package com.grupoms.app.presentacion.controlador.comandos.ingrediente;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;
import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarListaIngredientes implements Command {
	public Context execute(Object data) {
		SAIngrediente sa = FactoriaSA.getInstance().creaSAIngrediente();

		try {
			List<TIngrediente> ingredientes = sa.mostrarListaIngredientes();
			return new Context(Evento.MOSTRAR_INGREDIENTES_OK, ingredientes);
		} catch (Exception e) {
			return new Context(Evento.MOSTRAR_INGREDIENTES_KO, e.getMessage());
		}
	}
}
