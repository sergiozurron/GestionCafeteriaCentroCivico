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
        Integer idGenerado = -1;
        Transaction t = null;
        try {
            // Inicializaciones internas del pedido
            pedido.setActivo(true);
            pedido.setEstado("Abierto");
            pedido.setTotal(0.0);
            pedido.setFecha(new java.sql.Date(System.currentTimeMillis()));

            // Crear transacción
            t = TransactionManager.getInstance().newTransaccion();
            
            // Llamada al DAO (el DAO se encargará de usar TransactionManager)
            DAOPedido dao = new DAOPedidoImp();
            idGenerado = dao.altaPedido(pedido);

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
    public void modificarPedido(TPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificarPedido'");
    }

    @Override
    public void confirmarPedido(TPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmarPedido'");
    }

    @Override
    public void devolverPedido(TPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'devolverPedido'");
    }

    @Override
    public void vincularProductoPedido(TProducto producto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vincularProductoPedido'");
    }

    @Override
    public void desvincularProductoPedido(TProducto producto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'desvincularProductoPedido'");
    }

    @Override
    public TPedido mostrarPedido(TPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPedido'");
    }

    @Override
    public Set<TPedido> mostrarListaPedidos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarListaPedidos'");
    }

    @Override
    public Set<TPedido> mostrarPedidosEmpleado(Integer idEmpleado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPedidosEmpleado'");
    }

    @Override
    public Set<TPedido> mostrarPedidosMesa(Integer idMesa) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPedidosMesa'");
    }

    
}
