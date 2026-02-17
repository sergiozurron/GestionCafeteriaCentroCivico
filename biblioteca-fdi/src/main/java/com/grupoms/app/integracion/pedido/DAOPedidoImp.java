package com.grupoms.app.integracion.pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.DBConfig;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.pedido.TPedido;

public class DAOPedidoImp implements DAOPedido {

	
	@Override
	public int modificarPedido(TPedido pedido) {
		int act = -1;
		try {
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

				act = ps.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return act;
	}

	@Override
	public TPedido mostrarPedido(Integer idPedido) {
		TPedido pedido = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null)
				throw new IllegalStateException("No hay transaccion activa");
			Connection c = (Connection) t.getResource();

			String sql = "SELECT id, fecha, total_factura, estado, activo, empleado_id, mesa_id FROM pedidos WHERE id = ?";
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
	public List<TPedido> listarPedidos() {
		List<TPedido> lista = new ArrayList<>();

		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			String sql = "SELECT id, fecha, total_factura, estado, activo, empleado_id, mesa_id FROM pedidos";

			try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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
			System.err.println("Error mostrando la lista de pedidos: " + e.getMessage());
		}

		return lista;
	}

	@Override
	public Integer devolverPedido(Integer id) {
		int exito = -1;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			String sql = "UPDATE pedidos SET estado = ?, activo = ? WHERE id = ?";
			try (PreparedStatement ps = c.prepareStatement(sql)) {
				ps.setString(1, "DEVUELTO");
				ps.setBoolean(2, false);
				ps.setInt(3,id);

				exito = ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	@Override
	public List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado) {
		List<TPedido> lista = new ArrayList<>();

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
	public List<TPedido> mostrarPedidosPorMesa(Integer idMesa) {
		List<TPedido> lista = new ArrayList<>();

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

	private Connection getConnection() throws SQLException {
		Transaction tx = getTransaction();
		if (tx == null) {
			return DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword());
		}
		return (Connection) tx.getResource();
	}

	private void closeConnection(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			if (getTransaction() == null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Transaction getTransaction() {
		try {
			return TransactionManager.getInstance().getTransaction();
		} catch (IllegalStateException e) {
			return null;
		}
	}

	@Override
	public Integer cerrarPedido(TPedido pedido) {
		int exito = -1;
		try {
			Connection c = getConnection();
			PreparedStatement st = c.prepareStatement("INSERT INTO pedidos(fecha, total_factura,activo) VALUES(?, NOW(),true",
					Statement.RETURN_GENERATED_KEYS);
			st.setDouble(1,pedido.getTotal());
			st.executeUpdate();
			ResultSet result = st.getGeneratedKeys();
			if(result.next())exito = result.getInt(1);
			st.close();
			result.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

}