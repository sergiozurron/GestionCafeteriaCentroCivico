package com.grupoms.app.integracion.pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.DBConfig;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.negocio.pedido.TLineaPedido;
import com.grupoms.app.negocio.pedido.TPedido;

public class DAOPedidoImp implements DAOPedido {

	
	@Override
	public Boolean modificarPedido(TPedido pedido) {
		try { 
			Transaction t = TransactionManager.getInstance().getTransaction(); 
			Connection c = (Connection) t.getResource(); 
			String sql = "UPDATE pedidos SET fecha = ?, total_factura = ?, estado = ?, empleado_id = ?, mesa_id = ? " + "WHERE id = ?"; 
			try (PreparedStatement ps = c.prepareStatement(sql)) { 
				ps.setDate(1, new java.sql.Date(pedido.getFecha().getTime())); 
				ps.setDouble(2, pedido.getTotal()); 
				ps.setString(3, pedido.getEstado()); 
				ps.setInt(4, pedido.getIdEmpleado());
				ps.setInt(5, pedido.getIdMesa()); 
				ps.setInt(6, pedido.getId()); 
				ps.executeUpdate(); 
				} 
			return true; 
			} catch (Exception e) { 
				throw new RuntimeException("Error modificando el pedido "+pedido.getId(),e);
			}
	}

	@Override
	public TPedido mostrarPedido(Integer idPedido) {
		TPedido pedido = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null)
				throw new IllegalStateException("No hay transaccion activa");
			Connection c = (Connection) t.getResource();

			String sql = "SELECT id, fecha, total_factura, estado, activo, empleado_id, mesa_id FROM pedidos WHERE id = ? FOR UPDATE";
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
			throw new RuntimeException("Error mostrando el pedido "+idPedido,e);
		}
		return pedido;
	}

	@Override
	public List<TPedido> mostrarListaPedidos() {
		List<TPedido> lista = new ArrayList<>(); 
		try { 
			Transaction t = TransactionManager.getInstance().getTransaction(); 
			if (t == null) 
				throw new IllegalStateException("No hay transacción activa"); 
			Connection c = (Connection) t.getResource(); 
			String sql = "SELECT id, empleado_id, mesa_id, fecha, estado, total_factura " + "FROM pedidos WHERE activo = TRUE"; 
			try (PreparedStatement ps = c.prepareStatement(sql); 
					ResultSet rs = ps.executeQuery()) {
				while (rs.next()) { 
					TPedido p = new TPedido(); 
					p.setId(rs.getInt("id")); 
					p.setIdEmpleado(rs.getInt("empleado_id")); 
					p.setIdMesa(rs.getInt("mesa_id")); 
					p.setFecha(rs.getDate("fecha")); 
					p.setEstado(rs.getString("estado")); 
					p.setTotal(rs.getDouble("total_factura")); 
					p.setActivo(true); lista.add(p); 
					} 
				} 
			} catch (Exception e) { 
				throw new RuntimeException("Error mostrando la lista de pedidos",e);
			} 
		return lista;
	}

	@Override
	public Boolean devolverPedido(Integer idPedido) {
	    try { 
	        Transaction t = TransactionManager.getInstance().getTransaction(); 
	        if (t == null) 
	            throw new IllegalStateException("No hay transacción activa"); 

	        Connection c = (Connection) t.getResource(); 

	        // 1. Bloquear el pedido para modificarlo
	        String sqlSelect = "SELECT id FROM pedidos WHERE id = ? AND activo = TRUE FOR UPDATE"; 
	        try (PreparedStatement ps = c.prepareStatement(sqlSelect)) { 
	            ps.setInt(1, idPedido); 
	            try (ResultSet rs = ps.executeQuery()) { 
	                if (!rs.next()) { 
	                    return false; // no existe o ya está dado de baja 
	                } 
	            } 
	        }

	        // 2. Cambiar estado y dar de baja lógica
	        String sqlUpdate = "UPDATE pedidos SET estado = 'DEVUELTO', activo = FALSE WHERE id = ?"; 
	        try (PreparedStatement ps = c.prepareStatement(sqlUpdate)) { 
	            ps.setInt(1, idPedido); 
	            ps.executeUpdate(); 
	        } 

	        return true; 

	    } catch (Exception e) { 
			throw new RuntimeException("Error devolviendo el pedido "+idPedido,e);
	    }
	}


	@Override
	public List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado) {
		List<TPedido> lista = new ArrayList<>(); 
		try { 
			Transaction t = TransactionManager.getInstance().getTransaction(); 
			if (t == null) 
				throw new IllegalStateException("No hay transacción activa"); 
			Connection c = (Connection) t.getResource(); 
			String sql = "SELECT id, mesa_id, fecha, estado, total_factura, activo " + "FROM pedidos " + "WHERE empleado_id = ? AND activo = TRUE " + "FOR UPDATE";			try (PreparedStatement ps = c.prepareStatement(sql)) { 
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
				}
		} catch (Exception e) { 
			throw new RuntimeException("Error mostrando los pedidos del empleado "+idEmpleado,e);
			} 
		return lista;
		
	}

	@Override
	public List<TPedido> mostrarPedidosPorMesa(Integer idMesa) {
		List<TPedido> lista = new ArrayList<>(); 
		try { 
			Transaction t = TransactionManager.getInstance().getTransaction(); 
			if (t == null) 
				throw new IllegalStateException("No hay transacción activa"); 
			Connection c = (Connection) t.getResource(); 
			String sql = "SELECT id, empleado_id, fecha, estado, total_factura, activo " + "FROM pedidos " + "WHERE mesa_id = ? AND activo = TRUE " + "FOR UPDATE"; 
			try (PreparedStatement ps = c.prepareStatement(sql)) { 
				ps.setInt(1, idMesa); 
				try (ResultSet rs = ps.executeQuery()) { 
					while (rs.next()) { 
						TPedido p = new TPedido(); 
						p.setId(rs.getInt("id")); 
						p.setIdEmpleado(rs.getInt("empleado_id"));; 
						p.setFecha(rs.getDate("fecha")); 
						p.setEstado(rs.getString("estado")); 
						p.setTotal(rs.getDouble("total_factura"));
						p.setActivo(rs.getBoolean("activo")); 
						lista.add(p); 
						} 
					} 
				}
		} catch (Exception e) { 
			throw new RuntimeException("Error mostrando los pedidos de la mesa "+idMesa,e);
			} 
		return lista;
	}

	public Integer altaPedido(TPedido pedido) { 
		Integer id = null; 
		try { 
			Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource(); 
			String sql = "INSERT INTO pedidos (fecha, total_factura, estado, activo, empleado_id, mesa_id) " + "VALUES (?, ?, ?, ?, ?, ?)"; 
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
			ps.setDate(1, pedido.getFecha()); 
			ps.setDouble(2, pedido.getTotal()); 
			ps.setString(3, pedido.getEstado()); 
			ps.setBoolean(4, pedido.getActivo()); 
			ps.setInt(5, pedido.getIdEmpleado()); 
			ps.setInt(6, pedido.getIdMesa()); 
			ps.executeUpdate(); 
			ResultSet rs = ps.getGeneratedKeys(); 
			if (rs.next()) { 
				id = rs.getInt(1); 
				} 
			} catch (SQLException e) { 
				throw new RuntimeException("Error dando de alta el pedido ",e);
				} 
		return id;
	}

}