package com.grupoms.app.presentacion.comandos.prestamoJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.prestamoJPA.PrestamoId;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class DevolverPrestamoCommand implements Command {

	@Override
	public Context execute(Object data) {
		PrestamoId idPrestamo = (PrestamoId) data;
		Boolean exito = FactoriaSA.getInstance().creaSAPrestamo().devolverPrestamo(idPrestamo);
		if (!exito)
			return new Context(Evento.DEVOLUCION_PRESTAMO_KO, null);
		return new Context(Evento.DEVOLUCION_PRESTAMO_OK, null);
	}

}
