package com.grupoms.app.presentacion.controlador.comandos.mesa;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.SAMesa;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaMesaCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (!(data instanceof TMesa)) {
			return new Context(Evento.ALTA_MESA_KO, "Datos inválidos");
		}

		TMesa mesa = (TMesa) data;
		SAMesa sa = FactoriaSA.getInstance().creaSAMesa();

		try {
			Integer idGenerado = sa.altaMesa(mesa);

			if (idGenerado == null || idGenerado <= 0) {
				return new Context(Evento.ALTA_MESA_KO, "No se pudo crear la mesa");
			}

			mesa.setId(idGenerado);
			return new Context(Evento.ALTA_MESA_OK, mesa);

		} catch (RuntimeException e) {
			return new Context(Evento.ALTA_MESA_KO, e.getMessage());
		}
	}
}