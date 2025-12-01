package com.grupoms.app.presentacion.comandos.ejemplarJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaEjemplarCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer idEjemplar = (Integer) data;
		Boolean exito = FactoriaSA.getInstance().creaSAEjemplar().bajaEjemplar(idEjemplar);
		if (!exito)
			return new Context(Evento.BAJA_EJEMPLAR_KO, null);
		return new Context(Evento.BAJA_EJEMPLAR_OK, null);
	}

}
