package com.grupoms.app.negocio.producto;

import java.util.ArrayList;
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
        if (id == null || id <= 0)
            throw new IllegalArgumentException("El ID del producto no es válido.");
        Transaction t = null;
        TProducto producto = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            producto = daoProducto.mostrarProducto(id);
            if (producto == null) {
                throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            }
            producto.setActivo(false);
            daoProducto.bajaProducto(id);
            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void modificarProducto(TProducto producto) {
        Transaction t = null;
        TProducto prodExistente = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            prodExistente = daoProducto.mostrarProducto(producto.getId());
            if (prodExistente == null) {
                throw new IllegalArgumentException("El producto con ID " + producto.getId() + " no existe.");
            }
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public TProducto mostrarProducto(Integer id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("El ID del ingrediente no es válido.");
        Transaction t = null;
        TProducto producto = null;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // 2. Consultar el ingrediente
            producto = daoProducto.mostrarProducto(id);
            if (producto == null)
                throw new IllegalArgumentException("El producto con ID " + id + " no existe.");

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

        // 4. Devolver el producto (null si hubo error)
        return producto;
    }

    public List<TProducto> mostrarProductos() {
        List<TProducto> productos = new ArrayList<>();
        Transaction t = null;

        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // 2. Consultar los productos
            List<TProducto> todos = daoProducto.mostrarListaProductos();

            for (TProducto producto : todos) {
                if (producto.getActivo()) {
                    productos.add(producto);
                }
            }

            if (productos.isEmpty()) {
                throw new IllegalArgumentException("No hay productos activos en la base de datos.");
            }
            t.commit();
        }
            catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al mostrar la lista de productos.", e);
        }
        return productos;
    }
}
