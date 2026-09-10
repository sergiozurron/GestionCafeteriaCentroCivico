package com.grupoms.app.presentacion.comandos.socioJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class DesvincularPromocionASocioCommand implements Command {
	@Override
	public Context execute(Object data) {
		Integer[] params = (Integer[]) data;
		Integer idSocio = params[0];
		Integer idPromocion = params[1];

		int res = FactoriaSA.getInstance().creaSASocio().desvincularPromocionASocio(idSocio, idPromocion);

		if (res <= 0)
			return new Context(Evento.DESVINCULAR_PROMOCION_KO, null);

		return new Context(Evento.DESVINCULAR_PROMOCION_OK, params);
	}
}
