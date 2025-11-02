package com.grupoms.app.presentacion.mesa;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Command;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class AltaMesaCommand implements Command{

	@Override
	public Context execute(Object data) {
		TMesa mesa = (TMesa) data;
		int idMesa = FactoriaSA.getInstancia().creaSAMesa().altaMesa(mesa);
		if (idMesa == -1)
			return new Context(Evento.ALTA_MESA_KO, null);
		return new Context(Evento.ALTA_MESA_OK, idMesa);
	}

}