package com.grupoms.app.presentacion.comandos.promocionJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.comandos.Command;
import com.grupoms.app.negocio.PromocionJPA.PromocionSA;
import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class VerPromocionesPorSocioCommand implements Command {
	@Override
	public Context execute(Object data) {
		int event;
		String idSocio = (String) data;
		PromocionSA sa = FactoriaSA.getInstance().creaSAPromocion();

		java.util.List<TPromocion> promociones = sa.VerPromocionesPorSocio(Integer.parseInt(idSocio));
		if (promociones != null) {
			event = Evento.VER_PROMOCIONES_POR_SOCIO_OK;
		} else {
			event = Evento.VER_PROMOCIONES_POR_SOCIO_KO;
		}
		return new Context(event, promociones);
	}
}
