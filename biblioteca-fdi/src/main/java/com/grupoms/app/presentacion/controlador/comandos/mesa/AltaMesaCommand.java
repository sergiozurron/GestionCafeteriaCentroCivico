package com.grupoms.app.presentacion.controlador.comandos.mesa;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaMesaCommand implements Command{

	@Override
	public Context execute(Object data) {
		TMesa mesa = (TMesa) data;
		int idMesa = FactoriaSA.getInstance().creaSAMesa().altaMesa(mesa);
		if (idMesa == -1)
			return new Context(Evento.ALTA_MESA_KO, null);
		return new Context(Evento.ALTA_MESA_OK, idMesa);
	}
}