package com.grupoms.app.presentacion.comandos.materialJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.materialJPA.MaterialSA;
import com.grupoms.app.negocio.materialJPA.TLibro;
import com.grupoms.app.negocio.materialJPA.TMaterial;
import com.grupoms.app.negocio.materialJPA.TPintura;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaMaterialCommand implements Command{

	@Override
	public Context execute(Object data) {
		int res = -1, event;
        TMaterial material = (TMaterial) data;
        MaterialSA sa = FactoriaSA.getInstance().creaSAMaterial();
        
        res = sa.altaMaterial(material);
        if(res<0) {
        	event = Evento.ALTA_MATERIAL_KO;
        }else {
        	event=Evento.ALTA_MATERIAL_OK;
        }
        return new Context(event,res);
	}

}
