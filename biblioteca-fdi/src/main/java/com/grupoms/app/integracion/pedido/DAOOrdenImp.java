package com.grupoms.app.integracion.pedido;

import java.sql.*;
import java.util.*;
import com.grupoms.app.negocio.pedido.TOrden;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;

public class DAOOrdenImp implements DAOOrden {



    @Override
    public Integer crearOrden(TOrden orden) {
        Integer idGenerado = null;
        Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO ordenes (pedido_id, producto_id, cantidad, precio_venta) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, orden.getPedidoId());
            ps.setInt(2, orden.getPedidoId());
            ps.setInt(3, orden.getCantidad());
            ps.setDouble(4, orden.getPrecio());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) idGenerado = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al crear la orden en la BD.", e);
        }

        return idGenerado;
    }

    @Override
    public boolean bajaOrden(Integer id) {
        Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();
        boolean exito = false;
        try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE ordenes SET activo = false WHERE id = ?")) {

                ps.setInt(1, id);
                exito = ps.executeUpdate() > 0;

        } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al dar de baja la orden.", e);
        }

        return exito;
    }

    @Override
    public TOrden mostrarOrden(Integer id) {
        Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();
        TOrden orden = null;

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM ordenes WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                orden = new TOrden();
                orden.setId(rs.getInt("id"));
                orden.setPedidoID(rs.getInt("pedido_id"));
                orden.setPedidoID(rs.getInt("producto_id"));
                orden.setCantidad(rs.getInt("cantidad"));
                orden.setPrecioVenta(rs.getDouble("precio_venta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al mostrar la orden.", e);
        }

        return orden;
    }

    @Override
    public Set<TOrden> listarOrdenesPorPedido(Integer pedidoID) {
        Set<TOrden> lista = new HashSet<>();

    try {
        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();

        String sql = "SELECT id, pedido_id, producto_id, cantidad, precio_venta FROM ordenes WHERE pedido_id = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pedidoID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TOrden orden = new TOrden();
                    orden.setId(rs.getInt("id"));
                    orden.setPedidoID(rs.getInt("pedido_id"));
                    orden.setProductID(rs.getInt("producto_id"));
                    orden.setCantidad(rs.getInt("cantidad"));
                    orden.setPrecioVenta(rs.getDouble("precio_venta"));
                    lista.add(orden);
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
    }
}
