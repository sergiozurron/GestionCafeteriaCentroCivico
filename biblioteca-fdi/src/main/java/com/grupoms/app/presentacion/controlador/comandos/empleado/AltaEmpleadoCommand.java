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
            return new Context(Evento.ALTA_EMPLEADO_KO, "Datos inválidos");
        }

        TEmpleado emp = (TEmpleado) data;
        SAEmpleado sa = FactoriaSA.getInstance().creaSAEmpleado();

        try {
            Integer id = sa.crearEmpleado(emp);

            if (id == null) {
                return new Context(Evento.ALTA_EMPLEADO_KO, "No se pudo crear el empleado");
            }

            return new Context(Evento.ALTA_EMPLEADO_OK, emp);

        } catch (RuntimeException e) {
            return new Context(Evento.ALTA_EMPLEADO_KO, e.getMessage());
        }
    }
}