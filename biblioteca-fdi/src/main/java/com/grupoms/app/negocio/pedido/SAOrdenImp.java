package com.grupoms.app.negocio.pedido;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.pedido.DAOOrden;
import com.grupoms.app.integracion.factoria.FactoriaDAO;

public class SAOrdenImp implements SAOrden {

    private DAOOrden dao = FactoriaDAO.getInstancia().creaDAOOrden();

    @Override
    public Integer altaOrden(TOrden orden) {
        Transaction t = null;
        Integer idGenerado = null;

        try {
            if (orden == null || orden.getPedidoId() == null)
                throw new IllegalArgumentException("La orden y el pedido asociado no pueden ser nulos");

            t = TransactionManager.getInstance().newTransaction();
            t.start();

            idGenerado = dao.altaOrden(orden);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al crear la orden", e);
        }

        return idGenerado;
    }

    @Override
    public void vincularProducto(TOrden orden) {
        Transaction t = null;
        try {
            if (orden == null || orden.getProductoId() == null || orden.getPedidoId() == null)
                throw new IllegalArgumentException("Datos de la orden incompletos");

            t = TransactionManager.getInstance().newTransaction();
            t.start();

            dao.vincularProducto(orden);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al vincular producto a la orden", e);
        }
    }

    @Override
    public void desvincularProducto(TOrden orden) {
        Transaction t = null;
        try {
            if (orden == null || orden.getId() == null)
                throw new IllegalArgumentException("Orden inválida");

            t = TransactionManager.getInstance().newTransaction();
            t.start();

            dao.desvincularProducto(orden);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al desvincular producto de la orden", e);
        }
    }

    @Override
    public TOrden mostrarOrden(Integer idOrden) {
        Transaction t = null;
        TOrden orden = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            orden = dao.mostrarOrden(idOrden);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al mostrar la orden", e);
        }
        return orden;
    }
}
