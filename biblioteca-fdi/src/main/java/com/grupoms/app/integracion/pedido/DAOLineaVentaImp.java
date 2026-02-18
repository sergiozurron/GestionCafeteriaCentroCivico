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

		String sql = "INSERT INTO linea_venta (pedido_id, producto_id, cantidad, precio_venta) VALUES (?, ?, ?, ?)";
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
	public TLineaVenta bajaLineaVenta(Integer idP, Integer idPr) throws Exception {
		TLineaVenta lineaV = null;
		try {
			Connection c = (Connection) TransactionManager.getInstance().getTransaction().getResource();
			PreparedStatement statement = c.prepareStatement(
					"DELETE FROM linea_venta WHERE id_pedido = ? AND id_producto = ?",
					Statement.RETURN_GENERATED_KEYS
					);
			statement.setInt(1,idP);
			statement.setInt(2,idPr);
			
			statement = c.prepareStatement(
					"SELECT * FROM linea_venta WHERE id_pedido = ? AND id_producto = ? FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS
					);
			statement.setInt(1,idP);
			statement.setInt(2,idPr);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				lineaV = new TLineaVenta();
				lineaV.setPedidoID(result.getInt(1));
				lineaV.setProductID(2);
				lineaV.setCantidad(3);
				lineaV.setPrecioVenta(4);
			}
			statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return lineaV;
	}

	@Override
	public Integer modificarLineaVenta(TLineaVenta tLineaVenta) {
		try {
			Connection conn = (Connection) TransactionManager.getInstance().getTransaction().getResource();
			PreparedStatement st = conn.prepareStatement("UPDATE orden SET pedido_id = ?, producto_id = ?, cantidad = ?, precio_venta = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, tLineaVenta.getPedidoId());
			st.setInt(2,tLineaVenta.getProductoId());
			st.setInt(3, tLineaVenta.getCantidad());
			st.setDouble(4,tLineaVenta.getPrecio());
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
				linea.setPrecioVenta(result.getDouble(4));
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
				p.setPrecioVenta(tot);
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
					"SELECT id_pedido, id_producto, cantidad, precio FROM linea_venta WHERE id:pedido = ? FOR UPDATE",Statement.RETURN_GENERATED_KEYS
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
				p.setPrecioVenta(tot);
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
