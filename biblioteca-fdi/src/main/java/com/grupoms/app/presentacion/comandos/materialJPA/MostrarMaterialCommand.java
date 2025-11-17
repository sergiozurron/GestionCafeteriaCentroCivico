package com.grupoms.app.presentacion.comandos.materialJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.materialJPA.MaterialSA;
import com.grupoms.app.negocio.materialJPA.TMaterial;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarMaterialCommand implements Command{

	@Override
	public Context execute(Object data) {
		Integer id = -1;
		if (data instanceof Integer) {
	         id = (Integer) data;
	    }
		
		MaterialSA sa = FactoriaSA.getInstance().creaSAMaterial();
		try {
			TMaterial res = sa.mostrarMaterial(id);
			return (res != null)
		             ? new Context(Evento.MOSTRAR_MATERIAL_OK, res)
		             : new Context(Evento.MOSTRAR_MATERIAL_KO, null);
		     } catch (IllegalArgumentException e) {
		         return new Context(Evento.MOSTRAR_MATERIAL_KO, null);
		}
	}
}
