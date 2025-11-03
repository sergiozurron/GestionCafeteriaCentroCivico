package com.grupoms.app.presentacion.mesa;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarMesaCommand implements Command{

	@Override
	public Context execute(Object data) {
		TMesa mesa = (TMesa) data;
		try {
		FactoriaSA.getInstancia().creaSAMesa().modificarMesa(mesa);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return new Context(Evento.MODIFICAR_MESA_KO, null);
		}
		return new Context(Evento.MODIFICAR_MESA_OK, mesa.getId());
	}
}