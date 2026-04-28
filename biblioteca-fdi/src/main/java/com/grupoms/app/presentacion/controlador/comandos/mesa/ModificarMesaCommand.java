package com.grupoms.app.presentacion.controlador.comandos.mesa;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarMesaCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (!(data instanceof TMesa)) {
			return new Context(Evento.MODIFICAR_MESA_KO, "Datos inválidos");
		}

		TMesa mesa = (TMesa) data;

		try {
			Boolean resultado = FactoriaSA.getInstance().creaSAMesa().modificarMesa(mesa);

			if (resultado == null || !resultado) {
				return new Context(Evento.MODIFICAR_MESA_KO, "No se pudo modificar la mesa");
			}

			return new Context(Evento.MODIFICAR_MESA_OK, mesa.getId());

		} catch (RuntimeException e) {
			return new Context(Evento.MODIFICAR_MESA_KO, e.getMessage());
		}
	}
}