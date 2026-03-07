package com.grupoms.app.negocio.ingrediente;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.ingrediente.DAOIngrediente;
import com.grupoms.app.integracion.ingrediente.DAOIngredienteImp;
import com.grupoms.app.negocio.producto.TEntradaReceta;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;

public class SAIngredienteImp implements SAIngrediente {

    private DAOIngrediente dao = new DAOIngredienteImp();

    @Override
    public Integer crearIngrediente(TIngrediente ingrediente) {
        Transaction t = TransactionManager.getInstance().newTransaction();
        try {
            t.start();
            ingrediente.setActivo(true);
            Integer id = dao.crearIngrediente(ingrediente);
            t.commit();
            return id;
        } catch (Exception e) {
            t.rollback();
            throw new RuntimeException("Error en SA al crear ingrediente", e);
        }
    }

    @Override
    public Boolean modificarIngrediente(TIngrediente ingrediente) {
        Transaction t = TransactionManager.getInstance().newTransaction();
        try {
            t.start();
            Boolean exito = dao.modificarIngrediente(ingrediente);
            t.commit();
            return exito;
        } catch (Exception e) {
            t.rollback();
            throw new RuntimeException("Error en SA al modificar ingrediente", e);
        }
    }

    @Override
    public Boolean bajaIngrediente(TIngrediente ingrediente) {
        Transaction t = TransactionManager.getInstance().newTransaction();
        try {
            t.start();
            ingrediente.setActivo(false);
            Boolean exito = dao.bajaIngrediente(ingrediente);
            t.commit();
            return exito;
        } catch (Exception e) {
            t.rollback();
            throw new RuntimeException("Error en SA al dar de baja ingrediente", e);
        }
    }

    @Override
    public TIngrediente mostrarIngrediente(Integer id) {
        Transaction t = TransactionManager.getInstance().newTransaction();
        try {
            t.start();
            TIngrediente ing = dao.mostrarIngrediente(id);
            t.commit();
            return ing;
        } catch (Exception e) {
            t.rollback();
            throw new RuntimeException("Error en SA al mostrar ingrediente", e);
        }
    }

    @Override
    public List<TIngrediente> mostrarListaIngredientes() {

        Transaction t = null;
        List<TIngrediente> lista;

        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            lista = dao.mostrarListaIngredientes();

            // Solo activos
            lista.removeIf(ing -> !ing.getActivo());

            t.commit();
        } 
        catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } 
                catch (Exception ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("Error en SA al mostrar lista de ingredientes", e);
        }

        return lista;
    }


    @Override
	public List<TEntradaReceta> mostrarIngredientesPorProducto(Integer IDProducto){

        if (IDProducto == null || IDProducto <= 0)
            throw new IllegalArgumentException("ID de producto no válido");

        Transaction t = null;
        List<TEntradaReceta> listaV = new ArrayList<>();

        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            List<TEntradaReceta>lista = dao.listarIngredientesPorProducto(IDProducto);
            for(TEntradaReceta er: lista) {
            	if(er.getActivo()) {
            		listaV.add(er);
            	}
            }
            t.commit();
        } 
        catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } 
                catch (Exception ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("Error en SA al listar ingredientes por producto", e);
        }

        return listaV;
    }
    @Override
    public List<TIngrediente> mostrarIngredientesProveedor(Integer idProveedor) {

        if (idProveedor == null || idProveedor <= 0)
            throw new IllegalArgumentException("Proveedor no válido");

        Transaction t = null;
        List<TIngrediente> lista;

        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // Llamada correcta al DAO
            lista = dao.mostrarIngredientesProveedor(idProveedor);

            // Filtrar solo activos
            lista.removeIf(ing -> !ing.getActivo());

            t.commit();
        } 
        catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } 
                catch (Exception ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("Error al mostrar ingredientes por proveedor", e);
        }

        return lista;
    }

}
