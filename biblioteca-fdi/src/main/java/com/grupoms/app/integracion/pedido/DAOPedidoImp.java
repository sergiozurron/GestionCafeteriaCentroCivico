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
	public Boolean modificarPedido(TPedido pedido) {
		Boolean act = false;
		try {
			Connection c = TransactionManager.getInstance().getTransaction().getConnection();

			String sql = "UPDATE pedidos SET fecha = ?, total_factura = ?, estado = ?, activo = ?, empleado_id = ?, mesa_id = ? WHERE id = ?";
			try (PreparedStatement ps = c.prepareStatement(sql)) {
				ps.setTimestamp(1, new java.sql.Timestamp(pedido.getFecha().getTime()));
				ps.setDouble(2, pedido.getTotal());
				ps.setString(3, pedido.getEstado());
				ps.setBoolean(4, pedido.getActivo());
				ps.setInt(5, pedido.getIdEmpleado());
				ps.setInt(6, pedido.getIdMesa());
				ps.setInt(7, pedido.getId());

				act = ps.executeUpdate()>0;

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
			Connection c = t.getConnection();

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
	public List<TPedido> mostrarListaPedidos() {
		List<TPedido> lista = new ArrayList<>();

		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = t.getConnection();

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
	public Boolean devolverPedido(Integer id) {
		Boolean exito = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = t.getConnection();

			String sql = "UPDATE pedidos SET estado = ?, activo = ? WHERE id = ?";
			try (PreparedStatement ps = c.prepareStatement(sql)) {
				ps.setString(1, "DEVUELTO");
				ps.setBoolean(2, false);
				ps.setInt(3,id);

				exito = (ps.executeUpdate() > 0);
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
			Connection c = t.getConnection();

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
			Connection c = t.getConnection();

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

	@Override
	public Integer altaPedido(TPedido pedido) {
		// TODO Auto-generated method stub
		return null;
	}

}