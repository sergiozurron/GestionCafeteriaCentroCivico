package com.grupoms.app.negocio.empleado;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.empleado.DAOEmpleado;
import com.grupoms.app.integracion.empleado.DAOEmpleadoImp;

public class SAEmpleadoImp implements SAEmpleado {

    DAOEmpleado dao = new DAOEmpleadoImp();

    @Override
    public Integer crearEmpleado(TEmpleado empleado) {
        Transaction t = null;
        Integer idGenerado = null;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // 2. Inicializar campos del empleado
            if (empleado == null)
                throw new IllegalArgumentException("El empleado no puede ser nulo.");
            empleado.setActivo(true); // por defecto alta lógica

            // 3. Crear
            idGenerado = dao.crearEmpleado(empleado);

            // 4. Commit
            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }

        return idGenerado;
    }

    @Override
    public Boolean bajaEmpleado(TEmpleado empleado) {
        Transaction t = null;
        Boolean exito = false;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // 2. Validación e inicialización
            if (empleado == null || empleado.getID() == null || empleado.getID() <= 0)
                throw new IllegalArgumentException("El empleado debe tener un ID válido para dar de baja.");

            // baja lógica
            empleado.setActivo(false);

            // 3. Ejecutar
            dao.bajaEmpleado(empleado);

            // 4. Commit
            t.commit();
            exito = true;

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }

        return exito;
    }

    @Override
    public Boolean modificarEmpleado(TEmpleado empleado) {
        Transaction t = null;
        Boolean exito = false;
        TEmpleado emp = null;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // 2. Validaciones
            if (empleado == null || empleado.getID() == null || empleado.getID() <= 0)
                throw new IllegalArgumentException("El empleado debe tener un ID válido para modificar.");

            // 3. Comprobar existencia
            emp = dao.mostrarEmpleado(empleado.getID());
            if (emp == null)
                throw new IllegalArgumentException("El empleado con ID " + empleado.getID() + " no existe.");

            // 4. Modificar
            exito = dao.modificarEmpleado(empleado);

            // 5. Commit
            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }

        return exito;
    }

    @Override
    public TEmpleado mostrarEmpleado(Integer ID) {
       if (ID == null || ID <= 0)
        throw new IllegalArgumentException("El ID del empleado no es válido.");

    Transaction t = null;
    TEmpleado emp = null;

    try {
        // Iniciar transacción
        t = TransactionManager.getInstance().newTransaction();
        t.start();

        // Consultar empleado
        emp = dao.mostrarEmpleado(ID);

        // Commit si todo va bien
        t.commit();

    } catch (Exception e) {
        e.printStackTrace();
        if (t != null) {
            try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
        }
        // No relanzamos excepción: dejamos que el Command decida qué hacer
    }

    return emp; 
    }

    @Override
    public List<TEmpleado> mostrarListaEmpleados() {
        List<TEmpleado> listaEmpleadosActivos = new ArrayList<>();
        Transaction t = null;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // 2. Obtener todos y filtrar activos
            List<TEmpleado> todos = dao.mostrarListaEmpleados();
            for (TEmpleado e : todos) {
                if (e.getActivo()) {
                    listaEmpleadosActivos.add(e);
                }
            }

            // 3. Commit
            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }

        return listaEmpleadosActivos;
    }
}
