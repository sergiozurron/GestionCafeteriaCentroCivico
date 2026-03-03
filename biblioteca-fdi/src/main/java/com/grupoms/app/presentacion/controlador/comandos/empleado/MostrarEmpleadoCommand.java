package com.grupoms.app.presentacion.controlador.comandos.empleado;

import com.grupoms.app.negocio.empleado.SAEmpleado;
import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarEmpleadoCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer id = (Integer) data;

		SAEmpleado sa = FactoriaSA.getInstance().creaSAEmpleado();

		try {
			TEmpleado emp = sa.mostrarEmpleado(id);

			if (emp != null) {
				return new Context(Evento.MOSTRAR_EMPLEADO_OK, emp);
			} else {
				return new Context(Evento.MOSTRAR_EMPLEADO_KO, null);
			}

		} catch (IllegalArgumentException e) {
			return new Context(Evento.MOSTRAR_EMPLEADO_KO, null);
		}
	}
}
