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
    Connection c = null;
    boolean transaccionPropia = false;

    try {
        // Intentamos obtener la conexión de la transacción
        Transaction t = null;
        try {
            t = TransactionManager.getInstance().getTransaction();
            c = (Connection) t.getResource();
        } catch (IllegalStateException e) {
            // No hay transacción: abrimos conexión directa
            String url = System.getenv("MS_DB_URL");
            String user = System.getenv("MS_DB_USER");
            String pass = System.getenv("MS_DB_PASSWORD");
            c = DriverManager.getConnection(url, user, pass);
            c.setAutoCommit(false); // manejamos commit nosotros
            transaccionPropia = true;
        }

        // Insert en la tabla pedidos
        String sql = "INSERT INTO pedidos (fecha, total_factura, estado, activo, empleado_id, mesa_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, new Timestamp(pedido.getFecha().getTime()));
            ps.setDouble(2, pedido.getTotal());
            ps.setString(3, pedido.getEstado());
            ps.setBoolean(4, pedido.getActivo());
            ps.setInt(5, pedido.getIdEmpleado());
            ps.setInt(6, pedido.getIdMesa());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    pedido.setId(idGenerado);
                }
            }
        }

        // Commit si abrimos nuestra propia conexión
        if (transaccionPropia) {
            c.commit();
        }

    } catch (SQLException e) {
        // Rollback si abrimos nuestra propia conexión
        if (transaccionPropia && c != null) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        throw new RuntimeException("Error insertando pedido", e);
    } finally {
        // Cerramos conexión solo si es propia
        if (transaccionPropia && c != null) {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
    public Set<TPedido> mostrarListaPedidos() {
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