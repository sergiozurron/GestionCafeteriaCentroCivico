package com.grupoms.app.presentacion.controlador.comandos.mesa;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarListaMesasCommand implements Command {

	@Override
	public Context execute(Object data) {

		try {
			List<TMesa> mesas = FactoriaSA.getInstance().creaSAMesa().mostrarListaMesa();

			if (mesas == null || mesas.isEmpty()) {
				return new Context(Evento.MOSTRAR_LISTA_MESA_KO, "No hay mesas disponibles");
			}

			return new Context(Evento.MOSTRAR_LISTA_MESA_OK, mesas);

		} catch (RuntimeException e) {
			return new Context(Evento.MOSTRAR_LISTA_MESA_KO, e.getMessage());
		}
	}
}