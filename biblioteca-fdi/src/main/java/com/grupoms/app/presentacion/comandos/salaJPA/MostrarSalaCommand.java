package com.grupoms.app.presentacion.comandos.salaJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.salaJPA.SalaSA;
import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarSalaCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer id = -1;
		if (data instanceof Integer) {
			id = (Integer) data;
		}

		if (id <= 0) {
			return new Context(Evento.MOSTRAR_SALA_KO, "ID de sala inválido.");
		}

		SalaSA sa = FactoriaSA.getInstance().creaSASala();
		try {
			TSala res = sa.mostrarSala(id);
			return (res != null) ? new Context(Evento.MOSTRAR_SALA_OK, res) : new Context(Evento.MOSTRAR_SALA_KO, null);
		} catch (IllegalArgumentException e) {
			return new Context(Evento.MOSTRAR_SALA_KO, null);
		}
	}
}
