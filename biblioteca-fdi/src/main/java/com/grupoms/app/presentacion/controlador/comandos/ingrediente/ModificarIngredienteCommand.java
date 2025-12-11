package com.grupoms.app.presentacion.controlador.comandos.ingrediente;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;
import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarIngredienteCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof TIngrediente)) {
			return new Context(Evento.MODIFICAR_INGREDIENTE_KO, null);
		}

		TIngrediente ingr = (TIngrediente) data;
		SAIngrediente sa = FactoriaSA.getInstance().creaSAIngrediente();

		try {
			Boolean ok = sa.modificarIngrediente(ingr);
			return ok ? new Context(Evento.MODIFICAR_INGREDIENTE_OK, ingr)
					: new Context(Evento.MODIFICAR_INGREDIENTE_KO, null);
		} catch (IllegalArgumentException e) {
			return new Context(Evento.MODIFICAR_INGREDIENTE_KO, null);
		}
	}

}
