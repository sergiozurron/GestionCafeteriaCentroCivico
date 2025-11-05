package com.grupoms.app.integracion.proveedor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.proveedor.TProveedor;

public class DAOProveedorImpl implements DAOProveedor {

	private static final String INSERT = "INSERT INTO PROVEEDORES(nombre, tarifa, tiempo_entrega, activo) VALUES (?, ?, ?, ?)";
	private static final String READ_BY_ID = "SELECT * FROM PROVEEDORES WHERE id = ? FOR UPDATE";
	private static final String READ_BY_NAME = "SELECT * FROM PROVEEDORES WHERE nombre = ? FOR UPDATE";
	private static final String UPDATE = "UPDATE PROVEEDORES SET nombre = ?, tarifa = ?, tiempo_entrega = ?, activo = ? WHERE id = ?";
	private static final String DELETE_ALL = "DELETE FROM PROVEEDORES";

	@Override
	public void crea(TProveedor proveedor) {
		Connection conn = getConnection();
		try (PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, proveedor.getNombre());
			stmt.setDouble(2, proveedor.getTarifa());
			stmt.setInt(3, proveedor.getTiempoEntrega());
			stmt.setBoolean(4, proveedor.getActivo());

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			proveedor.setId(rs.getInt(1));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	@Override
	public TProveedor buscaPorId(int id) {
		Connection conn = getConnection();
		try (PreparedStatement stmt = conn.prepareStatement(READ_BY_ID)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					TProveedor proveedor = new TProveedor();
					proveedor.setId(rs.getInt("id"));
					proveedor.setNombre(rs.getString("nombre"));
					proveedor.setTarifa(rs.getDouble("tarifa"));
					proveedor.setTiempoEntrega(rs.getInt("tiempo_entrega"));
					proveedor.setActivo(rs.getBoolean("activo"));
					return proveedor;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	@Override
	public TProveedor buscaPorNombre(String nombre) {
		Connection conn = getConnection();
		try (PreparedStatement stmt = conn.prepareStatement(READ_BY_NAME)) {
			stmt.setString(1, nombre);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					TProveedor proveedor = new TProveedor();
					proveedor.setId(rs.getInt("id"));
					proveedor.setNombre(rs.getString("nombre"));
					proveedor.setTarifa(rs.getDouble("tarifa"));
					proveedor.setTiempoEntrega(rs.getInt("tiempo_entrega"));
					proveedor.setActivo(rs.getBoolean("activo"));
					return proveedor;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	@Override
	public void eliminaTodos() {
		Connection conn = getConnection();
		
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(DELETE_ALL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actualiza(TProveedor proveedor) {
		Connection conn = getConnection();

		try (PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
			stmt.setString(1, proveedor.getNombre());
			stmt.setDouble(2, proveedor.getTarifa());
			stmt.setInt(3, proveedor.getTiempoEntrega());
			stmt.setBoolean(4, proveedor.getActivo());
			stmt.setInt(5, proveedor.getId());

			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	private Connection getConnection() {
		Transaction tx = getTransaction();
		if (tx == null) {
			try {
				return DriverManager.getConnection(System.getenv("MS_DB_URL"), System.getenv("MS_DB_USER"),
						System.getenv("MS_DB_PASSWORD"));
			} catch (SQLException e) {
				return null;
			}
		}
		return (Connection) tx.getResource();
	}
	
	private void closeConnection(Connection conn) {
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
	
}
