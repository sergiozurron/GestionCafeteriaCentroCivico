package com.grupoms.app.presentacion.comandos.salaJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.salaJPA.SalaSA;
import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarSalaCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof TSala)) {
			return new Context(Evento.MODIFICAR_SALA_KO, null);
		}

		TSala sala = (TSala) data;
		SalaSA sa = FactoriaSA.getInstance().creaSASala();
		Integer ok = sa.modificarSala(sala);
		if (ok > -1) {
			return new Context(Evento.MODIFICAR_SALA_OK, ok);
		} else {
			return new Context(Evento.MODIFICAR_SALA_KO, null);
		}
	}
}
