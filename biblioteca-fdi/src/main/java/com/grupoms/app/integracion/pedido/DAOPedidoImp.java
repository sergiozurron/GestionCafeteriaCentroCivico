package com.grupoms.app.integracion.pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.pedido.TPedido;

public class DAOPedidoImp implements DAOPedido {

	private static final String UPDATE_PEDIDO = "UPDATE pedidos SET fecha = ?, total_factura = ?, estado = ?, empleado_id = ?, mesa_id = ? WHERE id = ?";
	private static final String SELECT_PEDIDO_BY_ID = "SELECT id, fecha, total_factura, estado, activo, empleado_id, mesa_id "
			+ "FROM pedidos WHERE id = ? AND activo = TRUE FOR UPDATE";
	private static final String SELECT_PEDIDOS_ACTIVOS = "SELECT id, empleado_id, mesa_id, fecha, estado, total_factura "
			+ "FROM pedidos WHERE activo = TRUE";
	private static final String SELECT_PEDIDOS_EMPLEADO = "SELECT id, mesa_id, fecha, estado, total_factura, activo "
			+ "FROM pedidos WHERE empleado_id = ? AND activo = TRUE FOR UPDATE";
	private static final String SELECT_PEDIDOS_MESA = "SELECT id, empleado_id, fecha, estado, total_factura, activo "
			+ "FROM pedidos WHERE mesa_id = ? AND activo = TRUE FOR UPDATE";
	private static final String INSERT_PEDIDO = "INSERT INTO pedidos (fecha, total_factura, estado, activo, empleado_id, mesa_id) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_PEDIDO_EXISTE = "SELECT id FROM pedidos WHERE id = ? AND activo = TRUE FOR UPDATE";
	private static final String UPDATE_DEVOLVER = "UPDATE pedidos SET estado = 'DEVUELTO', activo = FALSE WHERE id = ?";

	@Override
	public Boolean modificarPedido(TPedido pedido) {

		Transaction t = TransactionManager.getInstance().getTransaction();
		try (Connection c = (Connection) t.getResource()) {
			try (PreparedStatement ps = c.prepareStatement(UPDATE_PEDIDO)) {

				ps.setDate(1, new java.sql.Date(pedido.getFecha().getTime()));
				ps.setDouble(2, pedido.getTotal());
				ps.setString(3, pedido.getEstado());
				ps.setInt(4, pedido.getIdEmpleado());
				ps.setInt(5, pedido.getIdMesa());
				ps.setInt(6, pedido.getId());

				ps.executeUpdate();
				return true;

			} catch (Exception e) {
				throw new RuntimeException("Error modificando el pedido " + pedido.getId(), e);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error modificando el pedido " + pedido.getId(), e);
		}
	}

	@Override
	public TPedido mostrarPedido(Integer idPedido) {

		TPedido pedido = null;

		Transaction t = TransactionManager.getInstance().getTransaction();
		Connection c = (Connection) t.getResource();

		try (PreparedStatement ps = c.prepareStatement(SELECT_PEDIDO_BY_ID)) {

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

		} catch (Exception e) {
			throw new RuntimeException("Error mostrando el pedido " + idPedido, e);
		}

		return pedido;
	}

	@Override
	public List<TPedido> mostrarListaPedidos() {

		List<TPedido> lista = new ArrayList<>();

		Transaction t = TransactionManager.getInstance().getTransaction();
		Connection c = (Connection) t.getResource();

		try (PreparedStatement ps = c.prepareStatement(SELECT_PEDIDOS_ACTIVOS); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				TPedido p = new TPedido();
				p.setId(rs.getInt("id"));
				p.setIdEmpleado(rs.getInt("empleado_id"));
				p.setIdMesa(rs.getInt("mesa_id"));
				p.setFecha(rs.getDate("fecha"));
				p.setEstado(rs.getString("estado"));
				p.setTotal(rs.getDouble("total_factura"));
				p.setActivo(true);

				lista.add(p);
			}

		} catch (Exception e) {
			throw new RuntimeException("Error mostrando la lista de pedidos", e);
		}

		return lista;
	}

	@Override
	public Boolean devolverPedido(Integer idPedido) {

		Transaction t = TransactionManager.getInstance().getTransaction();
		try (Connection c = (Connection) t.getResource()) {
			try {
				try (PreparedStatement ps = c.prepareStatement(SELECT_PEDIDO_EXISTE)) {
					ps.setInt(1, idPedido);

					try (ResultSet rs = ps.executeQuery()) {
						if (!rs.next()) {
							return false;
						}
					}
				}
				try (PreparedStatement ps = c.prepareStatement(UPDATE_DEVOLVER)) {
					ps.setInt(1, idPedido);
					ps.executeUpdate();
				}
				return true;

			} catch (Exception e) {
				throw new RuntimeException("Error devolviendo el pedido " + idPedido, e);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error devolviendo el pedido " + idPedido, e);
		}
	}

	@Override
	public List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado) {

		List<TPedido> lista = new ArrayList<>();

		Transaction t = TransactionManager.getInstance().getTransaction();
		Connection c = (Connection) t.getResource();

		try (PreparedStatement ps = c.prepareStatement(SELECT_PEDIDOS_EMPLEADO)) {

			ps.setInt(1, idEmpleado);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					TPedido p = new TPedido();
					p.setId(rs.getInt("id"));
					p.setIdMesa(rs.getInt("mesa_id"));
					p.setFecha(rs.getDate("fecha"));
					p.setEstado(rs.getString("estado"));
					p.setTotal(rs.getDouble("total_factura"));
					p.setActivo(rs.getBoolean("activo"));

					lista.add(p);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error mostrando pedidos del empleado " + idEmpleado, e);
		}

		return lista;
	}

	@Override
	public List<TPedido> mostrarPedidosPorMesa(Integer idMesa) {

		List<TPedido> lista = new ArrayList<>();

		Transaction t = TransactionManager.getInstance().getTransaction();
		Connection c = (Connection) t.getResource();

		try (PreparedStatement ps = c.prepareStatement(SELECT_PEDIDOS_MESA)) {

			ps.setInt(1, idMesa);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					TPedido p = new TPedido();
					p.setId(rs.getInt("id"));
					p.setIdEmpleado(rs.getInt("empleado_id"));
					p.setFecha(rs.getDate("fecha"));
					p.setEstado(rs.getString("estado"));
					p.setTotal(rs.getDouble("total_factura"));
					p.setActivo(rs.getBoolean("activo"));

					lista.add(p);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error mostrando pedidos de la mesa " + idMesa, e);
		}

		return lista;
	}

	@Override
	public Integer altaPedido(TPedido pedido) {

		Integer id = null;

		Transaction t = TransactionManager.getInstance().getTransaction();
		Connection conn = (Connection) t.getResource();

		try (PreparedStatement ps = conn.prepareStatement(INSERT_PEDIDO, Statement.RETURN_GENERATED_KEYS)) {

			ps.setDate(1, pedido.getFecha());
			ps.setDouble(2, pedido.getTotal());
			ps.setString(3, pedido.getEstado());
			ps.setBoolean(4, pedido.getActivo());
			ps.setInt(5, pedido.getIdEmpleado());
			ps.setInt(6, pedido.getIdMesa());

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error dando de alta el pedido", e);
		}

		return id;
	}
}