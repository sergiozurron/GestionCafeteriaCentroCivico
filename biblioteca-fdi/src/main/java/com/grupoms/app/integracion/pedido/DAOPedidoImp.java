package com.grupoms.app.integracion.pedido;


import java.sql.*;
import java.util.Set;
import java.util.HashSet;

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
                ps.setTimestamp(1, new Timestamp(pedido.getFecha().getTime()));
                ps.setDouble(2, pedido.getTotal());
                ps.setString(3, pedido.getEstado());
                ps.setBoolean(4, pedido.getActivo());
                ps.setInt(5, pedido.getIdEmpleado());
                ps.setInt(6, pedido.getIdMesa());

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
    public Boolean modificarPedido(TPedido pedido) {
        boolean act=false;
        try{
            Connection c = (Connection) TransactionManager.getInstance().getTransaction().getResource();

            String sql = "UPDATE pedidos SET fecha = ?, total_factura = ?, estado = ?, activo = ?, empleado_id = ?, mesa_id = ? WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setTimestamp(1, new java.sql.Timestamp(pedido.getFecha().getTime()));
                ps.setDouble(2, pedido.getTotal());
                ps.setString(3, pedido.getEstado());
                ps.setBoolean(4, pedido.getActivo());
                ps.setInt(5, pedido.getIdEmpleado());
                ps.setInt(6, pedido.getIdMesa());
                ps.setInt(7, pedido.getId());

                int rows = ps.executeUpdate(); // número de filas afectadas
                act = (rows>0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return act;
    }

    @Override
    public TPedido mostrarPedido(Integer idPedido) {
         TPedido pedido = null;
    try {
        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();

         String sql = "SELECT id, fecha, total_factura, estado, activo, empleado_id, mesa_id " +
                         "FROM pedidos WHERE id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pedido = new TPedido();
                    pedido.setId(rs.getInt("id"));
                    pedido.setFecha(rs.getDate("fecha"));
                    pedido.setTotal(rs.getDouble("total_factura"));
                    pedido.setEstado(rs.getString("estado"));
                    pedido.setActivo(rs.getBoolean("activo"));
                    pedido.setIdEmpleado(rs.getInt("empleado_id"));
                    pedido.setIdMesa(rs.getInt("mesa_id"));
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
        Set<TPedido> lista = new HashSet<>();

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sql = "SELECT id, fecha, total_factura, estado, activo, empleado_id, mesa_id FROM pedidos";

            try (PreparedStatement ps = c.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    TPedido pedido = new TPedido();
                    pedido.setId(rs.getInt("id"));
                    pedido.setFecha(rs.getDate("fecha"));
                    pedido.setTotal(rs.getDouble("total_factura"));
                    pedido.setEstado(rs.getString("estado"));
                    pedido.setActivo(rs.getBoolean("activo"));
                    pedido.setIdEmpleado(rs.getInt("empleado_id"));
                    pedido.setIdMesa(rs.getInt("mesa_id"));
                    lista.add(pedido);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void devolverPedido(TPedido pedido) {
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sql = "UPDATE pedidos SET estado = ?, activo = ? WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, "DEVUELTO");
                ps.setBoolean(2, false);
                ps.setInt(3, pedido.getId());

                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer vincularProducto(Integer idPedido, Integer idProducto, Integer cantidad) {
        Integer idGenerado = null;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sqlPrecio = "SELECT precio FROM productos WHERE id = ?";
            Double precioVenta = null;
            try (PreparedStatement ps = c.prepareStatement(sqlPrecio)) {
                ps.setInt(1, idProducto);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        precioVenta = rs.getDouble("precio");
                    }
                }
            }

            if (precioVenta == null) {
                System.err.println("Error: Producto no encontrado con id " + idProducto);
                return null;
            }

            String sql = "INSERT INTO ordenes (pedido_id, producto_id, cantidad, precio_venta) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, idPedido);
                ps.setInt(2, idProducto);
                ps.setInt(3, cantidad);
                ps.setDouble(4, precioVenta);

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
    public Integer desvincularProducto(Integer idPedido, Integer idProducto) {
        int filasAfectadas = 0;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sql = "DELETE FROM ordenes WHERE pedido_id = ? AND producto_id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, idPedido);
                ps.setInt(2, idProducto);

                filasAfectadas = ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    @Override
    public Set<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado) {
        Set<TPedido> lista = new HashSet<>();

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sql = "SELECT id, fecha, total_factura, estado, activo, empleado_id, mesa_id FROM pedidos WHERE empleado_id = ?";

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, idEmpleado);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        TPedido pedido = new TPedido();
                        pedido.setId(rs.getInt("id"));
                        pedido.setFecha(rs.getDate("fecha"));
                        pedido.setTotal(rs.getDouble("total_factura"));
                        pedido.setEstado(rs.getString("estado"));
                        pedido.setActivo(rs.getBoolean("activo"));
                        pedido.setIdEmpleado(rs.getInt("empleado_id"));
                        pedido.setIdMesa(rs.getInt("mesa_id"));
                        lista.add(pedido);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Set<TPedido> mostrarPedidosPorMesa(Integer idMesa) {
        Set<TPedido> lista = new HashSet<>();

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sql = "SELECT id, fecha, total_factura, estado, activo, empleado_id, mesa_id FROM pedidos WHERE mesa_id = ?";

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, idMesa);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        TPedido pedido = new TPedido();
                        pedido.setId(rs.getInt("id"));
                        pedido.setFecha(rs.getDate("fecha"));
                        pedido.setTotal(rs.getDouble("total_factura"));
                        pedido.setEstado(rs.getString("estado"));
                        pedido.setActivo(rs.getBoolean("activo"));
                        pedido.setIdEmpleado(rs.getInt("empleado_id"));
                        pedido.setIdMesa(rs.getInt("mesa_id"));
                        lista.add(pedido);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}