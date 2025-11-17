package com.grupoms.app.presentacion.comandos.materialJPA;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.materialJPA.MaterialSA;
import com.grupoms.app.negocio.materialJPA.TMaterial;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarMaterialCommand implements Command{
	@Override
	public Context execute(Object data) {

		MaterialSA sa = FactoriaSA.getInstance().creaSAMaterial();
		try {
			List<TMaterial> lista = sa.listarMateriales();
            return new Context(Evento.LISTAR_MATERIAL_OK, lista);
		}catch (Exception e) {
            // Cualquier excepción se traduce a KO
            return new Context(Evento.LISTAR_MATERIAL_KO, null);
        }
	}

}
