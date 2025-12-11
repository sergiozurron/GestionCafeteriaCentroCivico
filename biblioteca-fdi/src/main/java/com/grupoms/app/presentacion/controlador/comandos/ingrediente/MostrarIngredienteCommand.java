package com.grupoms.app.presentacion.controlador.comandos.ingrediente;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;
import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarIngredienteCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer id = null;

		if (data instanceof Integer) {
			id = (Integer) data;
		} else if (data instanceof TIngrediente) {
			id = ((TIngrediente) data).getID();
		} else {
			return new Context(Evento.MOSTRAR_INGREDIENTE_KO, null);
		}

		SAIngrediente sa = FactoriaSA.getInstance().creaSAIngrediente();

		try {
			TIngrediente emp = sa.mostrarIngrediente(id);
			return (emp != null) ? new Context(Evento.MOSTRAR_INGREDIENTE_OK, emp)
					: new Context(Evento.MOSTRAR_INGREDIENTE_KO, null);
		} catch (IllegalArgumentException e) {
			return new Context(Evento.MOSTRAR_INGREDIENTE_KO, null);
		}
	}

}
