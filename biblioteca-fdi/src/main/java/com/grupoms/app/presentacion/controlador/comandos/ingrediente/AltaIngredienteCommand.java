package com.grupoms.app.presentacion.controlador.comandos.ingrediente;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;
import com.grupoms.app.negocio.ingrediente.TIngrediente;

import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaIngredienteCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof TIngrediente)) {
			return new Context(Evento.ALTA_INGREDIENTE_KO, "Datos inválidos");
		}

		TIngrediente ingr = (TIngrediente) data;
		SAIngrediente sa = FactoriaSA.getInstance().creaSAIngrediente();

		try {
			Integer id = sa.crearIngrediente(ingr);
			return (id != null) ? new Context(Evento.ALTA_INGREDIENTE_OK, ingr)
					: new Context(Evento.ALTA_INGREDIENTE_KO, "No se pudo crear el ingrediente");
		} catch (Exception e) {
			return new Context(Evento.ALTA_INGREDIENTE_KO, e.getMessage());
		}
	}

}
