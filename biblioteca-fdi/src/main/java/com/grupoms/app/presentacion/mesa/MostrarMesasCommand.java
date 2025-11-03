package com.grupoms.app.presentacion.mesa;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarMesasCommand implements Command{

	@Override
	public Context execute(Object data) {
		List<TMesa> mesas = FactoriaSA.getInstancia().creaSAMesa().mostrarMesas();
		if(mesas.isEmpty()) {
			return new Context(Evento.MOSTRAR_LISTA_MESA_KO, null);
		}
		return new Context(Evento.MOSTRAR_LISTA_MESA_OK, mesas);
	}
}