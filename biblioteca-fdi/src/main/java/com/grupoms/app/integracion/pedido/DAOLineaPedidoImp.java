package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TLineaPedido;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOLineaPedidoImp implements DAOLineaPedido {

	@Override
	public Integer altaLineaPedido(TLineaPedido lp) {

	    try {
	        Transaction t = TransactionManager.getInstance().getTransaction();
	        if (t == null)
	            throw new IllegalStateException("No hay transacción activa");

	        Connection c = (Connection) t.getResource();

	        String sql = "INSERT INTO linea_pedido (pedido_id, producto_id, cantidad, activo) " +
	                     "VALUES (?, ?, ?, ?)";

	        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	            ps.setInt(1, lp.getPedidoId());
	            ps.setInt(2, lp.getProductoId());
	            ps.setInt(3, lp.getCantidad());
	            ps.setBoolean(4, lp.getActivo());

	            ps.executeUpdate();

	            try (ResultSet rs = ps.getGeneratedKeys()) {
	                if (rs.next()) return rs.getInt(1);
	            }
	        }

	    } catch (Exception e) {
	    	throw new RuntimeException("Error añadiendo el producto "+lp.getProductoId()+" al pedido "+lp.getProductoId(),e);
	    }

	    return -1;
	}
	@Override
	public Integer bajaLineaPedido(Integer idPedido, Integer idProducto) {

	    try {
	        Transaction t = TransactionManager.getInstance().getTransaction();
	        if (t == null)
	            throw new IllegalStateException("No hay transacción activa");

	        Connection c = (Connection) t.getResource();

	        // 1. Buscar la línea (FOR UPDATE)
	        String sqlSelect =
	            "SELECT id FROM linea_pedido " +
	            "WHERE pedido_id = ? AND producto_id = ? AND activo = TRUE " +
	            "FOR UPDATE";

	        Integer idLinea = null;

	        try (PreparedStatement ps = c.prepareStatement(sqlSelect)) {
	            ps.setInt(1, idPedido);
	            ps.setInt(2, idProducto);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    idLinea = rs.getInt("id");
	                } else {
	                    return -1; // no existe
	                }
	            }
	        }

	        // 2. Baja lógica
	        String sqlUpdate =
	            "UPDATE linea_pedido SET activo = FALSE WHERE id = ?";

	        try (PreparedStatement ps = c.prepareStatement(sqlUpdate)) {
	            ps.setInt(1, idLinea);
	            ps.executeUpdate();
	        }

	        return idLinea;

	    } catch (Exception e) {
	    	throw new RuntimeException("Error quitando el producto "+idProducto+" del pedido "+idPedido,e);
	    }
	}

	@Override
	public List<TLineaPedido> mostrarLineasPorPedido(Integer idPedido) {
		List<TLineaPedido> lista = new ArrayList<>(); 
		try { 
			Transaction t = TransactionManager.getInstance().getTransaction(); 
			if (t == null) 
				throw new IllegalStateException("No hay transaccion activa"); 
			Connection c = (Connection) t.getResource(); 
			String sql = "SELECT id, pedido_id, producto_id, cantidad FROM linea_pedido WHERE pedido_id = ? AND activo = TRUE FOR UPDATE"; 
			try (PreparedStatement ps = c.prepareStatement(sql)) { 
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
		    	throw new RuntimeException("Error mostrando el pedido "+idPedido,e);
			} 
		return lista;
	}

}
