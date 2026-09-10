package com.grupoms.app.presentacion.controlador.comandos.empleado;

import com.grupoms.app.negocio.empleado.SAEmpleado;
import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarEmpleadoCommand implements Command {

    @Override
    public Context execute(Object data) {
        if (!(data instanceof TEmpleado)) {
            return new Context(Evento.MODIFICAR_EMPLEADO_KO, "Datos inválidos");
        }

        TEmpleado emp = (TEmpleado) data;
        SAEmpleado sa = FactoriaSA.getInstance().creaSAEmpleado();

        try {
            Boolean ok = sa.modificarEmpleado(emp);

            if (!ok) {
                return new Context(Evento.MODIFICAR_EMPLEADO_KO, "No se pudo modificar el empleado");
            }

            return new Context(Evento.MODIFICAR_EMPLEADO_OK, emp);

        } catch (RuntimeException e) {
            return new Context(Evento.MODIFICAR_EMPLEADO_KO, e.getMessage());
        }
    }
}