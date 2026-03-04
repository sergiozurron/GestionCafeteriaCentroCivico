package com.grupoms.app.integracion.empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.empleado.TEmpleado;

public class DAOEmpleadoImp implements DAOEmpleado {

	@Override
	public Integer crearEmpleado(TEmpleado empleado) {
		Integer idGenerado = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			String sql = "INSERT INTO empleados (nombre, activo, donde_atiende, sueldo) VALUES (?, ?, ?, ?)";
			try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, empleado.getNombre());
				ps.setBoolean(2, empleado.getActivo());
				ps.setString(3, empleado.getDondeAtiende());
				ps.setDouble(4, empleado.getSueldo());
				ps.executeUpdate();

				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						idGenerado = rs.getInt(1);
						empleado.setID(idGenerado);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idGenerado;
	}

	@Override
	public TEmpleado mostrarEmpleado(Integer id) {
		TEmpleado empleado = null;

		try {

			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null) {
				throw new IllegalStateException("No hay transacción activa al mostrar empleado");
			}
			Connection c = (Connection) t.getResource();

			String sql = "SELECT id, nombre, activo, donde_atiende, sueldo FROM empleados WHERE id = ? FOR UPDATE";
			try (PreparedStatement ps = c.prepareStatement(sql)) {
				ps.setInt(1, id);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						empleado = new TEmpleado();
						empleado.setID(rs.getInt("id"));
						empleado.setNombre(rs.getString("nombre"));
						empleado.setActivo(rs.getBoolean("activo"));
						empleado.setDondeAtiende(rs.getString("donde_atiende"));
						empleado.setSueldo(rs.getDouble("sueldo"));
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return empleado;
	}

	@Override
	public List<TEmpleado> mostrarListaEmpleados() throws Exception {
		List<TEmpleado> lista = new ArrayList<>();
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			String sql = "SELECT id, nombre, activo, donde_atiende, sueldo FROM empleados";
			try (PreparedStatement ps = c.prepareStatement(sql)) {
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					TEmpleado e = new TEmpleado();
					e.setID(rs.getInt("id"));
					e.setNombre(rs.getString("nombre"));
					e.setActivo(rs.getBoolean("activo"));
					e.setDondeAtiende(rs.getString("donde_atiende"));
					e.setSueldo(rs.getDouble("sueldo"));
					lista.add(e);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public Boolean modificarEmpleado(TEmpleado empleado) {
		Boolean exito = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			String sql = "UPDATE empleados SET nombre = ?, activo = ?, donde_atiende = ?, sueldo = ? WHERE id = ?";
			try (PreparedStatement st = c.prepareStatement(sql)) {
				st.setString(1, empleado.getNombre());
				st.setBoolean(2, empleado.getActivo());
				st.setString(3, empleado.getDondeAtiende());
				st.setDouble(4, empleado.getSueldo());
				st.setInt(5, empleado.getID());

				int rows = st.executeUpdate();
				exito = rows > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	@Override
	public Boolean bajaEmpleado(TEmpleado empleado) {

		Boolean exito = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			String sql = "UPDATE empleados SET activo = ? WHERE id = ?";
			try (PreparedStatement st = c.prepareStatement(sql)) {
				st.setBoolean(1, empleado.getActivo());
				st.setInt(2, empleado.getID());

				int rows = st.executeUpdate();
				exito = rows > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}
}
