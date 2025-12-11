package com.grupoms.app.presentacion.comandos.materialJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaMaterialCommand implements Command {
	@Override
	public Context execute(Object data) {
		Integer idMaterial = (Integer) data;
		try {
			Integer exito = FactoriaSA.getInstance().creaSAMaterial().bajaMaterial(idMaterial);
			if (exito < 0)
				return new Context(Evento.BAJA_MATERIAL_KO, null);
			return new Context(Evento.BAJA_MATERIAL_OK, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Context(Evento.BAJA_MATERIAL_KO, null);
		}
	}

}
