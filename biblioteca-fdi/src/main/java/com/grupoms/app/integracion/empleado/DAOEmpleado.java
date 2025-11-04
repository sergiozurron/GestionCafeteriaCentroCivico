package com.grupoms.app.integracion.empleado;

import java.util.Set;
import com.grupoms.app.negocio.empleado.*;

public interface DAOEmpleado {
    public Integer crearEmpleado(TEmpleado empleado);

    public TEmpleado mostrarEmpleado(Integer id);

    public Set<TEmpleado> mostrarListaEmpleados() throws Exception;

    public Boolean modificarEmpleado(TEmpleado empleado);

    public Boolean bajaEmpleado(TEmpleado empleado);
}