package com.grupoms.app.presentacion.controlador.comandos.mesa;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.SAMesa;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarMesaCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer id = null;

		if (data instanceof Integer) {
			id = (Integer) data;
		} else if (data instanceof TMesa) {
			id = ((TMesa) data).getId();
		} else {
			return new Context(Evento.MOSTRAR_MESA_OK, null);
		}

		SAMesa sa = FactoriaSA.getInstance().creaSAMesa();

		try {
			TMesa emp = sa.mostrarMesa(id);
			return (emp != null) ? new Context(Evento.MOSTRAR_MESA_OK, emp) : new Context(Evento.MOSTRAR_MESA_KO, null);
		} catch (IllegalArgumentException e) {
			return new Context(Evento.MOSTRAR_MESA_KO, null);
		}
	}
}