package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TLineaPedido;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOLineaPedidoImp implements DAOLineaPedido {

	private static final String INSERT_LINEA = "INSERT INTO linea_pedido (pedido_id, producto_id, cantidad, activo) VALUES (?, ?, ?, ?)";
	private static final String SELECT_LINEA_FOR_DELETE = "SELECT id FROM linea_pedido WHERE pedido_id = ? AND producto_id = ? AND activo = TRUE FOR UPDATE";
	private static final String DELETE_LOGICO_LINEA = "UPDATE linea_pedido SET activo = FALSE WHERE id = ?";
	private static final String SELECT_LINEAS_PEDIDO = "SELECT id, pedido_id, producto_id, cantidad "
			+ "FROM linea_pedido WHERE pedido_id = ? AND activo = TRUE FOR UPDATE";

	@Override
	public Integer altaLineaPedido(TLineaPedido lp) {

		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null)
				throw new IllegalStateException("No hay transacción activa");

			Connection c = (Connection) t.getResource();
			try (PreparedStatement ps = c.prepareStatement(INSERT_LINEA, Statement.RETURN_GENERATED_KEYS)) {

				ps.setInt(1, lp.getPedidoId());
				ps.setInt(2, lp.getProductoId());
				ps.setInt(3, lp.getCantidad());
				ps.setBoolean(4, lp.getActivo());

				ps.executeUpdate();

				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						return rs.getInt(1);
					}
				}
			}

			throw new RuntimeException("No se pudo obtener ID generado");

		} catch (Exception e) {
			throw new RuntimeException(
					"Error añadiendo producto " + lp.getProductoId() + " al pedido " + lp.getPedidoId(), e);
		}
	}

	@Override
	public Integer bajaLineaPedido(Integer idPedido, Integer idProducto) {

		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null)
				throw new IllegalStateException("No hay transacción activa");

			Connection c = (Connection) t.getResource();
			Integer idLinea;

			try (PreparedStatement ps = c.prepareStatement(SELECT_LINEA_FOR_DELETE)) {

				ps.setInt(1, idPedido);
				ps.setInt(2, idProducto);

				try (ResultSet rs = ps.executeQuery()) {
					if (!rs.next()) {
						return null;
					}
					idLinea = rs.getInt("id");
				}
			}

			try (PreparedStatement ps = c.prepareStatement(DELETE_LOGICO_LINEA)) {
				ps.setInt(1, idLinea);
				ps.executeUpdate();
			}

			return idLinea;

		} catch (Exception e) {
			throw new RuntimeException("Error quitando producto " + idProducto + " del pedido " + idPedido, e);
		}
	}

	@Override
	public List<TLineaPedido> mostrarLineasPorPedido(Integer idPedido) {

		List<TLineaPedido> lista = new ArrayList<>();

		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null)
				throw new IllegalStateException("No hay transacción activa");

			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(SELECT_LINEAS_PEDIDO)) {

				ps.setInt(1, idPedido);

				try (ResultSet rs = ps.executeQuery()) {

					while (rs.next()) {
						TLineaPedido lp = new TLineaPedido();
						lp.setId(rs.getInt("id"));
						lp.setPedidoID(rs.getInt("pedido_id"));
						lp.setProductID(rs.getInt("producto_id"));
						lp.setCantidad(rs.getInt("cantidad"));

						lista.add(lp);
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error mostrando líneas del pedido " + idPedido, e);
		}

		return lista;
	}
}