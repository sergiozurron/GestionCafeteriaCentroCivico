package com.grupoms.app.integracion.pedido;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.pedido.TPedido;

public class DAOPedidoImp implements DAOPedido{

    @Override
    public Integer altaPedido(TPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'altaPedido'");
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