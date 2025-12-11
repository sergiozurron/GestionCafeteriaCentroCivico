package com.grupoms.app.presentacion.controlador.comandos.empleado;

import com.grupoms.app.negocio.empleado.SAEmpleado;
import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaEmpleadoCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (!(data instanceof TEmpleado)) {
			return new Context(Evento.BAJA_EMPLEADO_KO, null);
		}

		TEmpleado emp = (TEmpleado) data;
		SAEmpleado sa = FactoriaSA.getInstance().creaSAEmpleado();

		try {
			Boolean ok = sa.bajaEmpleado(emp);
			return ok ? new Context(Evento.BAJA_EMPLEADO_OK, emp) : new Context(Evento.BAJA_EMPLEADO_KO, null);
		} catch (IllegalArgumentException e) {
			return new Context(Evento.BAJA_EMPLEADO_KO, null);
		}
	}
}
