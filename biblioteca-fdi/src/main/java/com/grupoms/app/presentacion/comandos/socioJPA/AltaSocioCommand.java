package com.grupoms.app.presentacion.comandos.socioJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.socioJPA.SocioSA;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaSocioCommand implements Command {
	@Override
	public Context execute(Object data) {
		int res = -1, event;
		TSocio s = (TSocio) data;
		SocioSA sa = FactoriaSA.getInstance().creaSASocio();

		res = sa.altaSocio(s);
		if (res < 0) {
			event = Evento.ALTA_SOCIO_KO;
		} else {
			event = Evento.ALTA_SOCIO_OK;
		}
		return new Context(event, res);
	}
}
