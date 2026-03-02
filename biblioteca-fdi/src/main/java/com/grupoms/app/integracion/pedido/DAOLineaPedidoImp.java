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
	public Integer altaLineaPedido(TLineaPedido lineaPedido) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer bajaLineaPedido(Integer idPed, Integer idPr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TLineaPedido> mostrarLineasPorPedido(Integer idPedido) {
		List<TLineaPedido> lista = new ArrayList<>(); 
		try { 
			Transaction t = TransactionManager.getInstance().getTransaction(); 
			if (t == null) 
				throw new IllegalStateException("No hay transaccion activa"); 
			Connection c = (Connection) t.getResource(); 
			String sql = "SELECT id, pedido_id, producto_id, cantidad FROM lineas_pedido WHERE pedido_id = ? FOR UPDATE"; 
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
				e.printStackTrace(); 
			} 
		return lista;
	}

}
