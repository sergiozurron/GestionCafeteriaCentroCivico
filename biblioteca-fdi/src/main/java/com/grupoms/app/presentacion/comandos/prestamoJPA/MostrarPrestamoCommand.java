package com.grupoms.app.presentacion.comandos.prestamoJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.prestamoJPA.PrestamoId;
import com.grupoms.app.negocio.prestamoJPA.TPrestamo;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarPrestamoCommand implements Command {

	@Override
	public Context execute(Object data) {
		PrestamoId idPrestamo = (PrestamoId) data;
		TPrestamo prestamo = FactoriaSA.getInstance().creaSAPrestamo().mostrarPrestamo(idPrestamo);
		if (prestamo == null) {
			return new Context(Evento.MOSTRAR_PRESTAMO_KO, null);
		}
		return new Context(Evento.MOSTRAR_PRESTAMO_OK, prestamo);
	}

}
