package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TLineaVenta;
import com.grupoms.app.negocio.pedido.TPedidoLinea;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOLineaVentaImp implements DAOLineaVenta {


	@Override
	public Integer altaLineaVenta(TLineaVenta orden) throws Exception {
		Integer idGenerado = null;
		Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();

		String sql = "INSERT INTO linea_venta (pedido_id, producto_id, cantidad) VALUES (?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, orden.getPedidoId());
			ps.setObject(2, orden.getProductoId());
			ps.setInt(3, orden.getCantidad() != null ? orden.getCantidad() : 0);

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
	public TLineaVenta bajaLineaVenta(Integer idP, Integer idPr) throws Exception {
	    TLineaVenta lineaV = null;
	    Connection c = (Connection) TransactionManager.getInstance().getTransaction().getResource();

	    try {
	        String sqlSelect = "SELECT id_pedido, id_producto, cantidad FROM linea_venta " +
	                           "WHERE id_pedido = ? AND id_producto = ? FOR UPDATE";
	        try (PreparedStatement ps = c.prepareStatement(sqlSelect)) {
	            ps.setInt(1, idP);
	            ps.setInt(2, idPr);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    lineaV = new TLineaVenta();
	                    lineaV.setPedidoID(rs.getInt("id_pedido"));
	                    lineaV.setProductID(rs.getInt("id_producto"));
	                    lineaV.setCantidad(rs.getInt("cantidad"));
	                } else {
	                    return null;
	                }
	            }
	        }

	        String sqlUpdate = "UPDATE linea_venta SET activo = false WHERE id_pedido = ? AND id_producto = ?";
	        try (PreparedStatement ps = c.prepareStatement(sqlUpdate)) {
	            ps.setInt(1, idP);
	            ps.setInt(2, idPr);
	            ps.executeUpdate();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }

	    return lineaV;
	}


	@Override
	public Integer modificarLineaVenta(TLineaVenta tLineaVenta) {
		try {
			Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();
			PreparedStatement st = conn.prepareStatement("UPDATE orden SET pedido_id = ?, producto_id = ?, cantidad = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, tLineaVenta.getPedidoId());
			st.setInt(2,tLineaVenta.getProductoId());
			st.setInt(3, tLineaVenta.getCantidad());
			int affectedRows = st.executeUpdate();

			st.close();

			if (affectedRows == 0)
				return -1;

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public TLineaVenta mostrarLineaPedido(Integer idPedido, Integer idProducto) {
		TLineaVenta linea = null;
		try {
			Connection c = (Connection) TransactionManager.getInstance().getTransaction().getResource();
			PreparedStatement st = c.prepareStatement(
					"SELECT * FROM linea_venta WHERE pedido_id = ? AND producto_id = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS
					);
			st.setInt(1,idPedido);
			st.setInt(2, idProducto);
			ResultSet result = st.executeQuery();
			if(result.next()) {
				linea = new TLineaVenta();
				linea.setPedidoID(result.getInt(1));
				linea.setProductID(result.getInt(2));
				linea.setCantidad(result.getInt(3));
			}
			result.close();
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return linea;
	}

	@Override
	public List<TLineaVenta> listarLineas() {
		List<TLineaVenta> lineasPedido = null;
		try {
			Connection c = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			PreparedStatement statement = c.prepareStatement("SELECT * FROM lineas_venta");
			ResultSet result = statement.executeQuery();
			lineasPedido = new ArrayList<TLineaVenta>();
			while(result.next()) {
				Integer idP = result.getInt(1);
				Integer idPR = result.getInt(2);
				Integer cat = result.getInt(3);
				Double tot = result.getDouble(4);
				TLineaVenta p = new TLineaVenta();
				p.setPedidoID(idP);
				p.setProductID(idPR);
				p.setCantidad(cat);
			}
			result.close();
			statement.close();
			return lineasPedido;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return lineasPedido;
	}

	@Override
	public List<TLineaVenta> mostrarLineaPedidoPorPedido(Integer idPedido) {
		List<TLineaVenta> lineasPedido = null;
		try {
			Connection c = (Connection) TransactionManager.getInstance().getTransaction().getResource();
			PreparedStatement statement = c.prepareStatement(
					"SELECT id_pedido, id_producto, cantidad FROM linea_venta WHERE id:pedido = ? FOR UPDATE",Statement.RETURN_GENERATED_KEYS
					);
			statement.setInt(1,idPedido);
			ResultSet result = statement.executeQuery();
			lineasPedido = new ArrayList<TLineaVenta>();
			while(result.next()) {
				Integer idP = result.getInt(1);
				Integer idPR = result.getInt(2);
				Integer cat = result.getInt(3);
				Double tot = result.getDouble(4);
				TLineaVenta p = new TLineaVenta();
				p.setPedidoID(idP);
				p.setProductID(idPR);
				p.setCantidad(cat);
			}
			result.close();
			statement.close();
			return lineasPedido;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return lineasPedido;
	}

	

}
