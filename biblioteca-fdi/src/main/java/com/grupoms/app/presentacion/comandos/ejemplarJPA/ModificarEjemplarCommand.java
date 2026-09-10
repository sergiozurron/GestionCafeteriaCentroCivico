package com.grupoms.app.presentacion.comandos.ejemplarJPA;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarEjemplarCommand implements Command {

	@Override
	public Context execute(Object data) {
		TEjemplar ejemplar = (TEjemplar) data;
		Boolean exito = FactoriaSA.getInstance().creaSAEjemplar().modificarEjemplar(ejemplar);
		if (!exito)
			return new Context(Evento.MODIFICAR_EJEMPLAR_KO, null);
		return new Context(Evento.MODIFICAR_EJEMPLAR_OK, null);
	}

}
