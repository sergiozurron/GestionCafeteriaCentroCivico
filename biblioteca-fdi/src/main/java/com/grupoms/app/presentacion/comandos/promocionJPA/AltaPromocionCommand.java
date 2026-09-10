package com.grupoms.app.presentacion.comandos.promocionJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.comandos.Command;
import com.grupoms.app.negocio.PromocionJPA.PromocionSA;
import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class AltaPromocionCommand implements Command {
	@Override
	public Context execute(Object data) {
		int res = -1, event;
		TPromocion promocion = (TPromocion) data;
		PromocionSA sa = FactoriaSA.getInstance().creaSAPromocion();

		res = sa.altaPromocion(promocion);
		if (res < 0) {
			event = Evento.ALTA_PROMOCION_KO;
		} else {
			event = Evento.ALTA_PROMOCION_OK;
		}
		return new Context(event, res);
	}
}
