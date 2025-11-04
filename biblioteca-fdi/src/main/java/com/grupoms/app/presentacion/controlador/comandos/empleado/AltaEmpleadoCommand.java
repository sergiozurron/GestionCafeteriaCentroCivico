package com.grupoms.app.presentacion.controlador.comandos.empleado;

import com.grupoms.app.negocio.empleado.SAEmpleado;
import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaEmpleadoCommand implements Command {

    @Override
    public Context execute(Object data) {
        if (!(data instanceof TEmpleado)) {
            return new Context(Evento.ALTA_EMPLEADO_KO, null);
        }

        TEmpleado emp = (TEmpleado) data;
        SAEmpleado sa = FactoriaSA.getInstance().creaSAEmpleado();

        try {
            Integer id = sa.crearEmpleado(emp);
            return (id != null)
                ? new Context(Evento.ALTA_EMPLEADO_OK, emp)
                : new Context(Evento.ALTA_EMPLEADO_KO, null);
        } catch (IllegalArgumentException e) {
            return new Context(Evento.ALTA_EMPLEADO_KO, null);
        }
    }
}
