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
            return new Context(Evento.BAJA_EMPLEADO_KO, "Datos inválidos");
        }

        TEmpleado emp = (TEmpleado) data;
        SAEmpleado sa = FactoriaSA.getInstance().creaSAEmpleado();

        try {
            Boolean ok = sa.bajaEmpleado(emp);

            if (!ok) {
                return new Context(Evento.BAJA_EMPLEADO_KO, "No se pudo dar de baja el empleado");
            }

            return new Context(Evento.BAJA_EMPLEADO_OK, emp);

        } catch (RuntimeException e) {
            String mensaje = e.getMessage();

            if (mensaje == null || mensaje.isEmpty()) {
                mensaje = "Error interno al dar de baja el empleado";
            }

            return new Context(Evento.BAJA_EMPLEADO_KO, mensaje);
        }
    }
}