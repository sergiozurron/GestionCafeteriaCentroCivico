package com.grupoms.app.presentacion.comandos.claseJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class VincularEjemplarAClaseCommand implements Command {

	@Override
	public Context execute(Object data) {

		Integer[] params = (Integer[]) data;
		Integer idClase = params[0];
		Integer idEjemplar = params[1];

		int res = FactoriaSA.getInstance().creaSAClase().vincularEjemplarAClase(idClase, idEjemplar);

		if (res <= 0)
			return new Context(Evento.VINCULAR_EJEMPLAR_CLASE_KO, null);

		return new Context(Evento.VINCULAR_EJEMPLAR_CLASE_OK, params);
	}
}
