package com.grupoms.app.presentacion.comandos.salaJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaSalaCommand implements Command {
	@Override
	public Context execute(Object data) {
		Integer idSala = (Integer) data;
		try {
			Integer exito = FactoriaSA.getInstance().creaSASala().bajaSala(idSala);
			if (exito < 0)
				return new Context(Evento.BAJA_SALA_KO, null);
			return new Context(Evento.BAJA_SALA_OK, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Context(Evento.BAJA_SALA_KO, null);
		}
	}

}
