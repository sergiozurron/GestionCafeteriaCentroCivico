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
    public Integer altaPedido(TPedido pedido, Transaction t) {
        Integer idGenerado = null;

        // La conexión se obtiene de la transacción
        Connection c = (Connection) t.getResource();

        // PreparedStatement con RETURN_GENERATED_KEYS para obtener el id
        String sql = "INSERT INTO pedido (idMesa, idEmpleado, total, estado, activo, fecha) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getIdMesa());
            stmt.setInt(2, pedido.getIdEmpleado());
            stmt.setDouble(3, pedido.getTotal());
            stmt.setString(4, pedido.getEstado());
            stmt.setBoolean(5, pedido.getActivo());
            stmt.setDate(6, new java.sql.Date(pedido.getFecha().getTime()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
        }

        return idGenerado;
    }
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