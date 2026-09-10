package com.grupoms.app.presentacion.comandos.prestamoJPA;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.prestamoJPA.TPrestamo;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarPrestamosCommand implements Command {

	@Override
	public Context execute(Object data) {
		List<TPrestamo> prestamos = FactoriaSA.getInstance().creaSAPrestamo().listarPrestamo();
		return new Context(Evento.LISTAR_PRESTAMOS_OK, prestamos);
	}

}
