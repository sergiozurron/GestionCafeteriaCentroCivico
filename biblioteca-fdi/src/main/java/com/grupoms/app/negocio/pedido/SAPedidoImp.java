package com.grupoms.app.negocio.pedido;
import java.sql.Connection;
import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.pedido.*;

public class SAPedidoImp implements SAPedido{

    private DAOPedido dao = new DAOPedidoImp();

    @Override
    public Integer altaPedido(TPedido pedido) {
        Transaction t = null;
        Integer idGenerado = null;

        try {
            // 1. Iniciar transacción
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
    public Integer confirmarPedido(Integer idPedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmarPedido'");
    }

    @Override
    public Integer modificarPedido(TPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificarPedido'");
    }

    @Override
    public TPedido mostrarPedido(Integer idPedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPedido'");
    }

    @Override
    public Set<TPedido> mostrarPedidos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPedidos'");
    }

    @Override
    public Integer devolverPedido(Integer idPedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'devolverPedido'");
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


    
}
