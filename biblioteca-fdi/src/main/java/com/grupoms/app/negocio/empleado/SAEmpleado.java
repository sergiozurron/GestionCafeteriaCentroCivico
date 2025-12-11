package com.grupoms.app.negocio.empleado;

import java.util.List;

public interface SAEmpleado {
	Integer crearEmpleado(TEmpleado empleado);

	Boolean bajaEmpleado(TEmpleado empleado);

	Boolean modificarEmpleado(TEmpleado empleado);

	TEmpleado mostrarEmpleado(Integer id);

	List<TEmpleado> mostrarListaEmpleados();
}
