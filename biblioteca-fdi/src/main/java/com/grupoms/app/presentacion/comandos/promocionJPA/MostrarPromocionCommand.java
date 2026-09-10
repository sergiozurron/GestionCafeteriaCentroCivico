package com.grupoms.app.presentacion.comandos.promocionJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.comandos.Command;
import com.grupoms.app.negocio.PromocionJPA.PromocionSA;
import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class MostrarPromocionCommand implements Command {
	@Override
	public Context execute(Object data) {
		int event;
		Integer id = (Integer) data;
		PromocionSA sa = FactoriaSA.getInstance().creaSAPromocion();

		TPromocion promocion = sa.mostrarPromocion(id);
		if (promocion != null) {
			event = Evento.MOSTRAR_PROMOCION_OK;
		} else {
			event = Evento.MOSTRAR_PROMOCION_KO;
		}
		return new Context(event, promocion);
	}

}
