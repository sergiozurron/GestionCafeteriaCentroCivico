package com.grupoms.app.presentacion.controlador.comandos;

import java.util.List;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class ListarEjemplaresPorClaseCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer idClase = (Integer) data;
		List<TEjemplar> ejemplares = FactoriaSA.getInstance().creaSAEjemplar().listarEjemplaresPorMaterial(idClase);
		if (ejemplares == null)
			return new Context(Evento.LISTAR_EJEMPLARES_CLASE_KO, null);
		return new Context(Evento.LISTAR_EJEMPLARES_CLASE_OK, ejemplares);
	}

}
