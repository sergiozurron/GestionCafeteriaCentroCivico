package com.grupoms.app.presentacion.comandos.salaJPA;

import java.util.List;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarClasesPorSalaCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof Integer)) {
			return new Context(Evento.MOSTRAR_CLASES_POR_SALA_KO, "ID de sala inválido.");
		}

		Integer idSala = (Integer) data;
		List<TClase> clases = FactoriaSA.getInstance().creaSASala().mostrarClasesPorSala(idSala);
		if (clases == null)
			return new Context(Evento.MOSTRAR_CLASES_POR_SALA_KO, "La sala no existe o está inactiva.");
		return new Context(Evento.MOSTRAR_CLASES_POR_SALA_OK, clases);
	}

}
