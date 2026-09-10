package com.grupoms.app.presentacion.comandos.ejemplarJPA;

import java.util.List;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarEjemplaresPorMaterialCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer idMaterial = (Integer) data;
		List<TEjemplar> ejemplares = FactoriaSA.getInstance().creaSAEjemplar().listarEjemplaresPorMaterial(idMaterial);
		if (ejemplares == null)
			return new Context(Evento.MOSTRAR_EJEMPLARMATERIAL_KO, null);
		return new Context(Evento.MOSTRAR_EJEMPLARMATERIAL_OK, ejemplares);
	}

}
