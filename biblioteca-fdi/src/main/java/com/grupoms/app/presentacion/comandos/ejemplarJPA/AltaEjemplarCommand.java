package com.grupoms.app.presentacion.comandos.ejemplarJPA;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaEjemplarCommand implements Command {

	@Override
	public Context execute(Object data) {
		TEjemplar ejemplar = (TEjemplar) data;
		int idEjemplar = FactoriaSA.getInstance().creaSAEjemplar().altaEjemplar(ejemplar);
		if (idEjemplar == -1)
			return new Context(Evento.ALTA_EJEMPLAR_KO, null);
		return new Context(Evento.ALTA_EJEMPLAR_OK, idEjemplar);
	}

}
