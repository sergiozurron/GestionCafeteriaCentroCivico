package com.grupoms.app.presentacion.controlador.comandos.mesa;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaMesaCommand implements Command{

	@Override
	public Context execute(Object data) {
		TMesa mesa = (TMesa) data;
		try {
		FactoriaSA.getInstance().creaSAMesa().bajaMesa(mesa);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return new Context(Evento.BAJA_MESA_KO, null);
		}
		return new Context(Evento.BAJA_MESA_OK, mesa);
	}
}