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
        Integer idGenerado = null;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            // Insert en la tabla pedido
            String sql = "INSERT INTO pedido (idMesa, idEmpleado, total, estado, activo, fecha) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, pedido.getIdMesa());
                ps.setInt(2, pedido.getIdEmpleado());
                ps.setDouble(3, pedido.getTotal());
                ps.setString(4, pedido.getEstado());
                ps.setBoolean(5, pedido.getActivo());
                ps.setDate(6, new java.sql.Date(pedido.getFecha().getTime()));

                ps.executeUpdate();

                // Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return idGenerado;
    }
    
    @Override
    public Integer modificarPedido(TPedido pedido) {
        int exito = -1;
        try{
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
            String sql = "UPDATE pedido SET total = ?, estado = ?, activo = ? WHERE id = ?";
            try (PreparedStatement statement = c.prepareStatement(sql)) {
                statement.setDouble(1, pedido.getTotal());
                statement.setString(2, pedido.getEstado());
                statement.setBoolean(3, pedido.getActivo());
                statement.setInt(4, pedido.getId());

                exito = statement.executeUpdate(); // número de filas afectadas
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return exito!=-1? pedido.getId():exito;
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
    public void devolverPedido(TPedido pedido) {
        try {
        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();

        String sql = "UPDATE pedido SET estado = ?, activo = ? WHERE id = ?";
        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, "DEVUELTO");
            stmt.setBoolean(2, false);
            stmt.setInt(3, pedido.getId());

            stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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