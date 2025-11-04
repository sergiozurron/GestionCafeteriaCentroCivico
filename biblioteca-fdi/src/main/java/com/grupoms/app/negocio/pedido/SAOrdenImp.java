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

    public Boolean bajaOrden(TOrden orden){
        if (orden.getId() == null || orden.getId() <= 0)
            throw new IllegalArgumentException("ID de orden no válido.");

        Transaction t = null;
        Boolean exito = false;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // Borrado físico
            exito = dao.bajaOrden(orden.getId());

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al eliminar la orden.", e);
        }
        return exito;
    }

    @Override
    public void vincularProducto(TOrden orden) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vincularProducto'");
    }

    @Override
    public TOrden mostrarOrden(Integer idOrden) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarOrden'");
    }

}