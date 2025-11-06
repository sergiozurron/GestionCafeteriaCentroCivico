package com.grupoms.app.integracion.proveedor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.DBConfig;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.proveedor.TProveedor;

public class DAOProveedorImpl implements DAOProveedor {

	private static final String INSERT = "INSERT INTO proveedores(nombre, tarifa, tiempo_entrega, activo) VALUES (?, ?, ?, ?)";
	private static final String READ_BY_ID = "SELECT * FROM proveedores WHERE id = ?";
	private static final String READ_BY_NAME = "SELECT * FROM proveedores WHERE nombre = ?";
	private static final String READ_ALL = "SELECT * FROM proveedores";
	private static final String UPDATE = "UPDATE proveedores SET nombre = ?, tarifa = ?, tiempo_entrega = ?, activo = ? WHERE id = ?";
	private static final String UPDATE_ACTIVO = "UPDATE proveedores SET activo = ? WHERE id = ?";
	private static final String DELETE_ALL = "DELETE FROM proveedores";

	@Override
	public Integer crea(TProveedor proveedor) {
		Integer idGenerado = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement stmt = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, proveedor.getNombre());
				stmt.setDouble(2, proveedor.getTarifa());
				stmt.setInt(3, proveedor.getTiempoEntrega());
				stmt.setBoolean(4, proveedor.getActivo());

				stmt.executeUpdate();

				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						idGenerado = rs.getInt(1);
						proveedor.setId(idGenerado);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idGenerado;
	}

	@Override
	public TProveedor buscaPorId(int id) {
		TProveedor proveedor = null;
		Connection c = null;
		try {
			c = getConnection();
			try (PreparedStatement stmt = c.prepareStatement(READ_BY_ID)) {
				stmt.setInt(1, id);

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						proveedor = new TProveedor();
						proveedor.setId(rs.getInt("id"));
						proveedor.setNombre(rs.getString("nombre"));
						proveedor.setTarifa(rs.getDouble("tarifa"));
						proveedor.setTiempoEntrega(rs.getInt("tiempo_entrega"));
						proveedor.setActivo(rs.getBoolean("activo"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(c);
		}
		return proveedor;
	}

	@Override
	public TProveedor buscaPorNombre(String nombre) {
		TProveedor proveedor = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement stmt = c.prepareStatement(READ_BY_NAME)) {
				stmt.setString(1, nombre);

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						proveedor = new TProveedor();
						proveedor.setId(rs.getInt("id"));
						proveedor.setNombre(rs.getString("nombre"));
						proveedor.setTarifa(rs.getDouble("tarifa"));
						proveedor.setTiempoEntrega(rs.getInt("tiempo_entrega"));
						proveedor.setActivo(rs.getBoolean("activo"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return proveedor;
	}

	@Override
	public List<TProveedor> listar() {
		List<TProveedor> listaProveedores = new ArrayList<>();
		Connection c = null;
		try {
			c = getConnection();

			try (PreparedStatement stmt = c.prepareStatement(READ_ALL); ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					TProveedor proveedor = new TProveedor();
					proveedor.setId(rs.getInt("id"));
					proveedor.setNombre(rs.getString("nombre"));
					proveedor.setTarifa(rs.getDouble("tarifa"));
					proveedor.setTiempoEntrega(rs.getInt("tiempo_entrega"));
					proveedor.setActivo(rs.getBoolean("activo"));
					listaProveedores.add(proveedor);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al listar proveedores: " + e.getMessage());
		} finally {
			closeConnection(c);
		}
		return listaProveedores;
	}

	@Override
	public Boolean actualiza(TProveedor proveedor) {
		Boolean exito = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement stmt = c.prepareStatement(UPDATE)) {
				stmt.setString(1, proveedor.getNombre());
				stmt.setDouble(2, proveedor.getTarifa());
				stmt.setInt(3, proveedor.getTiempoEntrega());
				stmt.setBoolean(4, proveedor.getActivo());
				stmt.setInt(5, proveedor.getId());

				int rows = stmt.executeUpdate();
				exito = rows > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exito;
	}

	@Override
	public Boolean baja(TProveedor proveedor) {
		Boolean exito = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement stmt = c.prepareStatement(UPDATE_ACTIVO)) {
				stmt.setBoolean(1, proveedor.getActivo());
				stmt.setInt(2, proveedor.getId());

				int rows = stmt.executeUpdate();
				exito = rows > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exito;
	}

	@Override
	public void eliminaTodos() {
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (Statement stmt = c.createStatement()) {
				stmt.executeUpdate(DELETE_ALL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

}
