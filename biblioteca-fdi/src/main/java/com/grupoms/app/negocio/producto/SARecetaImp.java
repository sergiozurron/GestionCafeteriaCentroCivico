package com.grupoms.app.negocio.producto;

import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.ingrediente.DAOIngrediente;
import com.grupoms.app.integracion.producto.DAOProducto;
import com.grupoms.app.integracion.producto.DAOReceta;
import com.grupoms.app.negocio.ingrediente.TIngrediente;

public class SARecetaImp implements SAReceta {

    @Override
    public Integer vincularIngredienteAProducto(Integer idProducto, Integer idIngrediente) {

        Transaction t = TransactionManager.getInstance().newTransaction();

        try {
            t.start();

            DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
            DAOIngrediente daoIngrediente = FactoriaDAO.getInstancia().creaDAOIngrediente();
            DAOReceta daoReceta = FactoriaDAO.getInstancia().creaDAOReceta();

            // 1. Comprobar producto
            TProducto producto = daoProducto.mostrarProducto(idProducto);
            if (producto == null || !producto.getActivo()) {
                t.commit();
                return -1; // producto no válido
            }

            // 2. Comprobar ingrediente
            TIngrediente ingrediente = daoIngrediente.mostrarIngrediente(idIngrediente);
            if (ingrediente == null || !ingrediente.getActivo()) {
                t.commit();
                return -2; // ingrediente no válido
            }

            // 3. Comprobar si ya existe
            if (daoReceta.existeRelacion(idProducto, idIngrediente)) {
                t.commit();
                return -3; // ya vinculado
            }

            // 4. Crear relación
            Integer id = daoReceta.vincular(idProducto, idIngrediente);

            t.commit();
            return id;

        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
            return -99;
        }
    }


    @Override
    public Integer desvincularIngredienteDeProducto(Integer idProducto, Integer idIngrediente) {

        Transaction t = TransactionManager.getInstance().newTransaction();

        try {
            t.start();

            DAOReceta daoReceta = FactoriaDAO.getInstancia().creaDAOReceta();

            daoReceta.desvincular(idProducto, idIngrediente);

            t.commit();
            return 1;

        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
            return -99;
        }
    }


    @Override
    public List<TIngrediente> listarIngredientesDeProducto(Integer idProducto) {

        Transaction t = TransactionManager.getInstance().newTransaction();

        try {
            t.start();

            DAOReceta daoReceta = FactoriaDAO.getInstancia().creaDAOReceta();
            List<TIngrediente> lista = daoReceta.listarIngredientes(idProducto);

            t.commit();
            return lista;

        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
            return null;
        }
    }
}
