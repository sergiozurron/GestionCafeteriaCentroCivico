package com.grupoms.app.presentacion.controlador.comandos.empleado;

import java.util.Set;

import com.grupoms.app.negocio.empleado.SAEmpleado;
import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarListaEmpleadosCommand implements Command {

    @Override
    public Context execute(Object data) {
        SAEmpleado sa = FactoriaSA.getInstance().creaSAEmpleado();

        try {
            Set<TEmpleado> lista = sa.mostrarListaEmpleados();
            return (lista != null)
                ? new Context(Evento.MOSTRAR_EMPLEADOS_OK, lista)
                : new Context(Evento.MOSTRAR_EMPLEADOS_KO, null);
        } catch (IllegalArgumentException e) {
            return new Context(Evento.MOSTRAR_EMPLEADOS_KO, null);
        }
    }
}
