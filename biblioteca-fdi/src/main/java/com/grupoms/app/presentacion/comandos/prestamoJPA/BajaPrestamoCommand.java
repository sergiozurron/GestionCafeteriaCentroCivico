package com.grupoms.app.presentacion.comandos.prestamoJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaPrestamoCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer idPrestamo = (Integer) data;
		Integer exito = FactoriaSA.getInstance().creaSAPrestamo().bajaPrestamo(idPrestamo);
		if (exito == -1)
			return new Context(Evento.BAJA_PRESTAMO_KO, null);
		return new Context(Evento.BAJA_PRESTAMO_OK, null);
	}

}
