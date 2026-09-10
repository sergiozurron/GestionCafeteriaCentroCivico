package com.grupoms.app.presentacion.comandos.ejemplarJPA;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarEjemplarPrestSocioCommand implements Command {

	@Override
	public Context execute(Object data) {
		try {
			Object[] fechas = (Object[]) data;

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaInicio = formatter.parse((String) fechas[0]);
			Date fechaFin = formatter.parse((String) fechas[1]);	

			List<TEjemplar> ejemplares = FactoriaSA.getInstance()
					.creaSAEjemplar()
					.listarEjemplaresPrestadosPorAdultosPlenos(fechaInicio, fechaFin);

			return new Context(Evento.LISTAR_EJEMPLARES_PREST_SOCIO_OK, ejemplares);

		} catch (Exception e) {
			e.printStackTrace();
			return new Context(Evento.LISTAR_EJEMPLARES_PREST_SOCIO_KO, null);
		}
	}
}