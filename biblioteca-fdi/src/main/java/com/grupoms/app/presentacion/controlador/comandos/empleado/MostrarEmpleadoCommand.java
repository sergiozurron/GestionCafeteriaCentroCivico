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
        Integer id = null;

        if (data instanceof Integer) {
            id = (Integer) data;
        } else if (data instanceof TEmpleado) {
            id = ((TEmpleado) data).getID();
        } else {
            return new Context(Evento.MOSTRAR_EMPLEADO_KO, null);
        }

        SAEmpleado sa = FactoriaSA.getInstance().creaSAEmpleado();

        try {
            TEmpleado emp = sa.mostrarEmpleado(id);
            return (emp != null)
                ? new Context(Evento.MOSTRAR_EMPLEADO_OK, emp)
                : new Context(Evento.MOSTRAR_EMPLEADO_KO, null);
        } catch (IllegalArgumentException e) {
            return new Context(Evento.MOSTRAR_EMPLEADO_KO, null);
        }
    }
}
