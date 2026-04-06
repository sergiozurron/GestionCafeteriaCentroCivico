package com.grupoms.app.presentacion.controlador.comandos;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class BajaPrestamoCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer id = (Integer) data;
		boolean resultado = FactoriaSA.getInstance().creaSAPrestamo().bajaPrestamo(id);
		if (resultado)
			return new Context(Evento.BAJA_PRESTAMO_OK, true);
		return new Context(Evento.BAJA_PRESTAMO_KO, null);
	}

}
