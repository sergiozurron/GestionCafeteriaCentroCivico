package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TOrden;
import com.grupoms.app.integracion.Transaction.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOOrdenImp implements DAOOrden {

    @Override
    public Integer altaOrden(TOrden orden) throws Exception {
        Integer idGenerado = null;
        Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();

        String sql = "INSERT INTO ordenes (pedido_id, producto_id, cantidad, precio_venta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, orden.getPedidoId());
            ps.setObject(2, orden.getProductoId());
            ps.setInt(3, orden.getCantidad() != null ? orden.getCantidad() : 0);
            ps.setDouble(4, orden.getPrecio() != null ? orden.getPrecio() : 0.0);

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    orden.setId(idGenerado);
                }
            }
        }

        return idGenerado;
    }

    @Override
    public void vincularProducto(TOrden orden) throws Exception {
        Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();
        String sql = "UPDATE ordenes SET producto_id = ?, cantidad = ?, precio_venta = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orden.getProductoId());
            ps.setInt(2, orden.getCantidad());
            ps.setDouble(3, orden.getPrecio());
            ps.setInt(4, orden.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void desvincularProducto(TOrden orden) throws Exception {
        Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();
        String sql = "UPDATE ordenes SET producto_id = NULL, cantidad = 0, precio_venta = 0 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orden.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public TOrden mostrarOrden(Integer idOrden) throws Exception {
        TOrden orden = null;
        Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();
        String sql = "SELECT id, pedido_id, producto_id, cantidad, precio_venta FROM ordenes WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orden = new TOrden();
                    orden.setId(rs.getInt("id"));
                    orden.setPedidoID(rs.getInt("pedido_id"));
                    orden.setProductID(rs.getInt("producto_id"));
                    orden.setCantidad(rs.getInt("cantidad"));
                    orden.setPrecioVenta(rs.getDouble("precio_venta"));
                }
            }
        }
        return orden;
    }
}
