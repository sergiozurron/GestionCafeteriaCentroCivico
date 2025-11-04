package com.grupoms.app.negocio.pedido;
import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.pedido.*;

public class SAPedidoImp implements SAPedido{

    private DAOPedido dao = new DAOPedidoImp();

    @Override
    public Integer altaPedido(TPedido pedido) {
        Transaction t = null;
        Integer idGenerado = null;

        try {
            if (pedido == null)
                throw new IllegalArgumentException("El pedido no puede ser nulo.");

            t = TransactionManager.getInstance().newTransaction();
            t.start();
            // 2. Inicializar campos del pedido
            pedido.setEstado("Abierto");
            pedido.setTotal(0.0);
            pedido.setActivo(true);
            pedido.setFecha(new java.sql.Date(System.currentTimeMillis()));

            // 3. Llamada al DAO pasando la transacción
            idGenerado = dao.altaPedido(pedido);

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
    public Boolean confirmarPedido(TPedido pedido) {
       Transaction t = null;
       Boolean exito = false;
       try{
         if (pedido == null || pedido.getId() == null)
            throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener ID.");

        t = TransactionManager.getInstance().getTransaction();
        t.start();

        pedido.setEstado("EN PREPARACION");

        dao.modificarPedido(pedido);  
        t.commit();
        exito = true;
       }catch (Exception e) {
            e.printStackTrace();
            try {
                if (t != null) t.rollback(); // rollback si falla algo
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new IllegalArgumentException("Error al confirmar el pedido.", e);
        }
        return exito;
    }

    @Override
    public TPedido mostrarPedido(Integer idPedido) {
        if (idPedido == null || idPedido <= 0)
            throw new IllegalArgumentException("El ID del pedido no es válido.");
        
            TPedido pedido = null;
        Transaction t = null;
        try{
            t = TransactionManager.getInstance().getTransaction();
            t.start();
            pedido = dao.mostrarPedido(idPedido);
            t.commit();
        }catch(Exception e){
          e.printStackTrace();
            try {
                if (t != null) t.rollback(); // rollback si falla algo
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new IllegalArgumentException("Error al mostrar el pedido con ID: " + idPedido, e);

        }
        return pedido;
    }

    @Override
    public void devolverPedido(TPedido pedido) {
        Transaction t = TransactionManager.getInstance().getTransaction();
        try {
            if (pedido == null || pedido.getId() == null)
                throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener ID.");
            t.start();

            // Llamada al DAO para cambiar estado y activo
            pedido.setActivo(false);
            pedido.setEstado("DEVUELTO");
            dao.modificarPedido(pedido);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (t != null) t.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new IllegalArgumentException("Error al devolver el pedido.", e);

        }
    }

    @Override
    public Integer modificarPedido(TPedido pedido) {
                throw new UnsupportedOperationException("Unimplemented method 'vincularProducto'");

    }


    @Override
    public Integer vincularProducto(Integer idPedido, Integer idProducto, Integer cantidad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vincularProducto'");
    }

    @Override
    public Integer desvincularProducto(Integer idPedido, Integer idProducto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'desvincularProducto'");
    }

    @Override
    public Set<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPedidosPorEmpleado'");
    }

    @Override
    public Set<TPedido> mostrarPedidosPorMesa(Integer idMesa) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPedidosPorMesa'");
    }

     @Override
    public Set<TPedido> mostrarPedidos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPedidos'");
    }

    
}
