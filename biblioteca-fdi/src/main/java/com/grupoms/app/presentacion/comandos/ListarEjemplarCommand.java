package com.grupoms.app.presentacion.comandos;

import java.util.List;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarEjemplarCommand implements Command {

	@Override
	public Context execute(Object data) {
		List<TEjemplar> ejemplares = FactoriaSA.getInstance().creaSAEjemplar().listarEjemplares();
		return new Context(Evento.LISTAR_EJEMPLARES_OK, ejemplares);
	}

}
