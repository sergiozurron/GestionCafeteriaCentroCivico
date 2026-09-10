package com.grupoms.app.presentacion.controlador.comandos.mesa;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaMesaCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (!(data instanceof TMesa)) {
			return new Context(Evento.BAJA_MESA_KO, "Datos inválidos");
		}

		TMesa mesa = (TMesa) data;

		try {
			Boolean resultado = FactoriaSA.getInstance().creaSAMesa().bajaMesa(mesa);

			if (resultado == null || !resultado) {
				return new Context(Evento.BAJA_MESA_KO, "No se pudo dar de baja la mesa");
			}

			return new Context(Evento.BAJA_MESA_OK, mesa);

		} catch (RuntimeException e) {
			return new Context(Evento.BAJA_MESA_KO, e.getMessage());
		}
	}
}