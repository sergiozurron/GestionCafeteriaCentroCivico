package com.grupoms.app.presentacion.controlador.comandos.empleado;

import java.util.List;

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
            List<TEmpleado> lista = sa.mostrarListaEmpleados();
            return new Context(Evento.MOSTRAR_EMPLEADOS_OK,lista);
        } catch (Exception e) {
            return new Context(Evento.MOSTRAR_EMPLEADOS_KO, null);
        }
    }
}
