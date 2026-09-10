package com.grupoms.app.presentacion.comandos.prestamoJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.prestamoJPA.TPrestamo;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarPrestamoCommand implements Command {

	@Override
	public Context execute(Object data) {
		TPrestamo prestamo = (TPrestamo) data;
		Boolean exito = FactoriaSA.getInstance().creaSAPrestamo().modificarPrestamo(prestamo);
		if (!exito)
			return new Context(Evento.MODIFICAR_PRESTAMO_KO, null);
		return new Context(Evento.MODIFICAR_PRESTAMO_OK, null);
	}

}
