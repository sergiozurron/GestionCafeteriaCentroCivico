package com.grupoms.app.presentacion.comandos.prestamoJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.prestamoJPA.TPrestamo;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaPrestamoCommand implements Command {

	@Override
	public Context execute(Object data) {
		TPrestamo prestamo = (TPrestamo) data;
		Boolean idPrestamo = FactoriaSA.getInstance().creaSAPrestamo().altaPrestamo(prestamo);
		if (!idPrestamo)
			return new Context(Evento.ALTA_PRESTAMO_KO, null);
		return new Context(Evento.ALTA_PRESTAMO_OK, idPrestamo);
	}

}
