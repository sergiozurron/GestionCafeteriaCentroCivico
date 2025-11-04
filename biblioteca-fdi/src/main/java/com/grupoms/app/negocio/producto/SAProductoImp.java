package com.grupoms.app.negocio.producto;

import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.producto.DAOProducto;

public class SAProductoImp implements SAProducto{
    private DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();

    public Integer altaProducto(TProducto producto) {

        Transaction t = null;
        Integer idGenerado = null;

        try {
            
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            producto.setNombre("");
            producto.setPrecio(-1.0);
            producto.setStock(-1);
		    producto.setActivo(true);

            idGenerado = daoProducto.altaProducto(producto);
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
        }

        return idGenerado;
    }

    public void bajaProducto(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'bajaProducto'");
    }

    public void modificarProducto(TProducto producto) {
        throw new UnsupportedOperationException("Unimplemented method 'modificarProducto'");
    }

    public TProducto mostrarProducto(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'mostrarProducto'");
    }

    public List<TProducto> mostrarProductos() {
        throw new UnsupportedOperationException("Unimplemented method 'mostrarProductos'");
    }
}
