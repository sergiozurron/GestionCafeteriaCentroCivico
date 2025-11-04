package com.grupoms.app.integracion.pedido;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
            String sql = "INSERT INTO pedidos (fecha, total_factura, estado, activo, empleado_id, mesa_id) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(6, pedido.getIdMesa());
                ps.setInt(5, pedido.getIdEmpleado());
                ps.setDouble(2, pedido.getTotal());
                ps.setString(3, pedido.getEstado());
                ps.setBoolean(4, pedido.getActivo());
                ps.setDate(1, new java.sql.Date(pedido.getFecha().getTime()));

                ps.executeUpdate();

                // Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        pedido.setId(idGenerado);
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
            String sql = "UPDATE pedidos SET total_factura = ?, estado = ?, activo = ? WHERE id = ?";
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
         TPedido pedido = null;
    try {
        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();

        String sql = "SELECT * FROM pedidos WHERE id = ?";
        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido = new TPedido();
                    pedido.setId(rs.getInt("id"));
                    pedido.setIdMesa(rs.getInt("mesa_id"));
                    pedido.setIdEmpleado(rs.getInt("empleado_id"));
                    pedido.setEstado(rs.getString("estado"));
                    pedido.setFecha(rs.getDate("fecha"));
                    pedido.setTotal(rs.getDouble("total_factura"));
                    pedido.setActivo(rs.getBoolean("activo"));
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return pedido;
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

        String sql = "UPDATE pedidos SET estado = ?, activo = ? WHERE id = ?";
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