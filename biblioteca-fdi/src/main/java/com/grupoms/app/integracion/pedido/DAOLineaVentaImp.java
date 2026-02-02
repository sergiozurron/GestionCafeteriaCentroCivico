package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TLineaVenta;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOLineaVentaImp implements DAOLineaVenta {


	@Override
	public Integer altaLineaVenta(TLineaVenta orden) throws Exception {
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
	public Boolean bajaLineaVenta(Integer id) throws Exception {

		Boolean exito = false;
		Transaction t = null;
		try {
			t = com.grupoms.app.integracion.Transaction.TransactionManager.getInstance().getTransaction();
			Connection conn = (Connection) t.getResource();

			String sql = "DELETE FROM ordenes WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, id);
				exito = ps.executeUpdate() > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Error al eliminar la orden.", e);
		}

		return exito;
	}

}
