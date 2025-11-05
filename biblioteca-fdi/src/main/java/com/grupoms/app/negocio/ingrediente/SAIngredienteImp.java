package com.grupoms.app.negocio.ingrediente;

import java.util.HashSet;
import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.ingrediente.DAOIngrediente;
import com.grupoms.app.integracion.ingrediente.DAOIngredienteImp;

public class SAIngredienteImp implements SAIngrediente{

    DAOIngrediente dao = new DAOIngredienteImp();

    @Override
    public Integer crearIngrediente(TIngrediente ingrediente) {
        Transaction t = null;
        Integer idGenerado = null;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            // 2. Inicializar campos del ingrediente
            ingrediente.setActivo(true);
            idGenerado = dao.crearIngrediente(ingrediente);
            
            // 4. Commit
            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
        }

        return idGenerado;
    }

    @Override
    public Boolean bajaIngrediente(TIngrediente ingrediente) {
        Transaction t = null;
        Boolean exito = false;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            // 2. Inicializar campos del ingrediente
            ingrediente.setActivo(false);
            dao.bajaIngrediente(ingrediente);
            
            // 4. Commit
            t.commit();
            exito = true;

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
        }
        return exito;

    }

    @Override
    public Boolean modificarIngrediente(TIngrediente ingrediente) {
        Transaction t = null;
        Boolean exito = false;
        TIngrediente ing = null;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            ing = dao.mostrarIngrediente(ingrediente.getID());
            if (ing == null)
                throw new IllegalArgumentException("El ingrediente con ID " + ingrediente.getID() + " no existe.");
            
            exito = dao.modificarIngrediente(ingrediente);
            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
        }

        return exito;
    }

    @Override
    public TIngrediente mostrarIngrediente(Integer ID) {
        if (ID == null || ID <= 0)
            throw new IllegalArgumentException("El ID del ingrediente no es válido.");
        Transaction t = null;
        TIngrediente ing = null;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // 2. Consultar el ingrediente
            ing = dao.mostrarIngrediente(ID);
            if (ing == null)
                throw new IllegalArgumentException("El ingrediente con ID " + ID + " no existe.");

            // 3. Commit de la transacción
            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { 
                    t.rollback(); 
                } catch(Exception ex) { 
                    ex.printStackTrace(); 
                }
            }
            throw new RuntimeException(e.getMessage(), e);
        }

        // 4. Devolver el ingrediente (null si hubo error)
        return ing;
    }

    @Override
    public Set<TIngrediente> mostrarListaIngredientes() {
        Set<TIngrediente> listaIngredientes = new HashSet<>();
        Transaction t = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            Set<TIngrediente> todos = dao.mostrarListaIngredientes();
            for(TIngrediente ing : todos) {
                if(ing.getActivo()) {
                    listaIngredientes.add(ing);
                }
            }

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            listaIngredientes = null;
        }
        return listaIngredientes;
    }

    @Override
    public Set<TIngrediente> mostrarIngredientePorProducto(Integer IDProducto) {
        if (IDProducto == null || IDProducto <= 0)
            throw new IllegalArgumentException("El ID del producto no es válido.");

        Set<TIngrediente> listaIngredientes = null;
        Transaction t = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            listaIngredientes = dao.mostrarIngredientePorProducto(IDProducto);
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al mostrar ingredientes del producto con ID: " + IDProducto, e);
        }
        return listaIngredientes;
    }

    @Override
    public Set<TIngrediente> mostrarProveedorPorIngrediente(TIngrediente ingrediente) {
        if (ingrediente == null || ingrediente.getIDProveedor() == null || ingrediente.getIDProveedor() <= 0)
            throw new IllegalArgumentException("El ingrediente debe tener un ID de proveedor válido.");

        Set<TIngrediente> listaIngredientes = null;
        Transaction t = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            listaIngredientes = dao.mostrarProveedorPorIngrediente(ingrediente.getIDProveedor());
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al mostrar ingredientes del proveedor con ID: " + ingrediente.getIDProveedor(), e);
        }
        return listaIngredientes;
    }

    @Override
    public void vincularProducto(Integer idIngrediente, Integer idProducto, Integer cantidad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vincularProducto'");
    }

    
}
