package com.grupoms.app.presentacion.comandos.ejemplarJPA;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarEjemplarCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer idEjemplar = (Integer) data;
		TEjemplar ejemplar = FactoriaSA.getInstance().creaSAEjemplar().mostrarEjemplar(idEjemplar);
		if (ejemplar == null)
			return new Context(Evento.MOSTRAR_EJEMPLAR_KO, null);
		return new Context(Evento.MOSTRAR_EJEMPLAR_OK, ejemplar);
	}

}
