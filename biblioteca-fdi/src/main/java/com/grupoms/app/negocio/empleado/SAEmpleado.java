package com.grupoms.app.negocio.empleado;

import java.util.Set;

public interface SAEmpleado {
    Integer crearEmpleado(TEmpleado empleado);

    Boolean bajaEmpleado(TEmpleado empleado);

    Boolean modificarEmpleado(TEmpleado empleado);

    TEmpleado mostrarEmpleado(Integer id);

    Set<TEmpleado> mostrarListaEmpleados();
}
