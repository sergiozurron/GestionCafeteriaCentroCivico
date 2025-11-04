package com.grupoms.app.negocio.pedido;

import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.pedido.DAOOrden;

public class SAOrdenImp implements SAOrden {

    private DAOOrden dao = FactoriaDAO.getInstancia().creaDAOOrden();

    @Override
    public Integer crearOrden(TOrden orden) {
        Transaction t = null;
        Integer idGenerado = null;

        try {
            if (orden == null)
                throw new IllegalArgumentException("La orden no puede ser nula.");
            if (orden.getCantidad() == null || orden.getCantidad() <= 0)
                throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");

            t = TransactionManager.getInstance().newTransaction();
            t.start();

            idGenerado = dao.crearOrden(orden);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al crear la orden.", e);
        }

        return idGenerado;
    }

    @Override
    public boolean eliminarOrden(Integer id) {
        Transaction t = null;
        boolean exito = false;

        try {
            if (id == null || id <= 0)
                throw new IllegalArgumentException("El ID de la orden no es válido.");

            t = TransactionManager.getInstance().getTransaction();
            t.start();

            exito = dao.bajaOrden(id);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            throw new IllegalArgumentException("Error al eliminar la orden.", e);
        }

        return exito;
    }

    @Override
    public TOrden mostrarOrden(Integer id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("El ID de la orden no es válido.");

        Transaction t = null;
        TOrden orden = null;

        try {
            t = TransactionManager.getInstance().getTransaction();
            t.start();

            orden = dao.mostrarOrden(id);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            throw new IllegalArgumentException("Error al mostrar la orden.", e);
        }

        return orden;
    }

    @Override
    public Set<TOrden> listarOrdenesPorPedido(Integer pedidoID) {
        if (pedidoID == null || pedidoID <= 0)
            throw new IllegalArgumentException("El ID del pedido no es válido.");

        Transaction t = null;
        Set<TOrden> lista = null;

        try {
            t = TransactionManager.getInstance().getTransaction();
            t.start();

            lista = dao.listarOrdenesPorPedido(pedidoID);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            throw new IllegalArgumentException("Error al listar las órdenes del pedido.", e);
        }

        return lista;
    }
}
