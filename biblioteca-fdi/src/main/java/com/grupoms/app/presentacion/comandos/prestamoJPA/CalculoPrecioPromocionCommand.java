package com.grupoms.app.presentacion.comandos.prestamoJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.prestamoJPA.TCalculoPrecioPromocion;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class CalculoPrecioPromocionCommand implements Command {

	@Override
	public Context execute(Object data) {
		Double resultado = FactoriaSA.getInstance().creaSAPrestamo().calcularPrecioPromocion((TCalculoPrecioPromocion) data);
		if (resultado == null)
			return new Context(Evento.CALCULO_PRECIO_PROMOCION_KO, null);
		return new Context(Evento.CALCULO_PRECIO_PROMOCION_OK, resultado);
	}

}
