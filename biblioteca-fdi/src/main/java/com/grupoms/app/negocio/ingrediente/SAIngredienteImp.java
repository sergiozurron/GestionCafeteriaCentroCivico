package com.grupoms.app.negocio.ingrediente;

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
    public Boolean bajaIngrediente(Integer ID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bajaIngrediente'");
    }

    @Override
    public Boolean modificarIngrediente(TIngrediente ingrediente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificarIngrediente'");
    }

    @Override
    public TIngrediente mostrarIngrediente(Integer ID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarIngrediente'");
    }

    @Override
    public Set<TIngrediente> mostrarListaIngredientes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarListaIngredientes'");
    }

    @Override
    public Set<TIngrediente> mostrarIngredientePorProducto(Integer IDProducto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarIngredientePorProducto'");
    }

    @Override
    public Set<TIngrediente> mostrarProveedorPorIngrediente(TIngrediente ingrediente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarProveedorPorIngrediente'");
    }

    @Override
    public void vincularProducto(Integer idIngrediente, Integer idProducto, Integer cantidad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vincularProducto'");
    }

    
}
