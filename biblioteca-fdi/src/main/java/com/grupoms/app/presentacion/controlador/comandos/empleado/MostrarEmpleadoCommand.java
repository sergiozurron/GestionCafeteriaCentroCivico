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
        if (!(data instanceof Integer)) {
            return new Context(Evento.MOSTRAR_EMPLEADO_KO, "ID inválido");
        }

        Integer id = (Integer) data;
        SAEmpleado sa = FactoriaSA.getInstance().creaSAEmpleado();

        try {
            TEmpleado emp = sa.mostrarEmpleado(id);

            if (emp == null) {
                return new Context(Evento.MOSTRAR_EMPLEADO_KO, "Empleado no encontrado");
            }

            return new Context(Evento.MOSTRAR_EMPLEADO_OK, emp);

        } catch (RuntimeException e) {
            return new Context(Evento.MOSTRAR_EMPLEADO_KO, e.getMessage());
        }
    }
}