package com.grupoms.app.presentacion.comandos.materialJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.materialJPA.MaterialSA;
import com.grupoms.app.negocio.materialJPA.TMaterial;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarMaterialCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof TMaterial)) {
			return new Context(Evento.MODIFICAR_MATERIAL_KO, null);
		}

		TMaterial material = (TMaterial) data;
		MaterialSA sa = FactoriaSA.getInstance().creaSAMaterial();
		Integer ok = sa.modificarMaterial(material);
		if (ok > -1) {
			return new Context(Evento.MODIFICAR_MATERIAL_OK, ok);
		} else {
			return new Context(Evento.MODIFICAR_MATERIAL_KO, null);
		}
	}

}
