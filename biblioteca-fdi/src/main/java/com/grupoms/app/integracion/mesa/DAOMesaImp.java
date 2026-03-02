package com.grupoms.app.integracion.mesa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.negocio.mesa.TMesaTerraza;

public class DAOMesaImp implements DAOMesa {

	private static final String INSERT_MESA = "INSERT INTO Mesa (numero, ubicacion, capacidad, activo) VALUES (?, ?, ?, ?)";
	private static final String INSERT_SALA = "INSERT INTO MesaSala (id_mesa, reservada, privacidad) VALUES (?, ?, ?)";
	private static final String INSERT_TERRAZA = "INSERT INTO MesaTerraza (id_mesa, cubierta, suplemento) VALUES (?, ?, ?)";

	private static final String READ_BY_ID = "SELECT m.*, s.reservada, s.privacidad, t.cubierta, t.suplemento "
			+ "FROM mesas m " + "LEFT JOIN salas s ON m.id = s.id " + "LEFT JOIN terrazas t ON m.id = t.id "
			+ "WHERE m.id = ? FOR UPDATE";

	private static final String ALL = "SELECT m.*, s.reservada, s.privacidad, t.cubierta, t.suplemento "
			+ "FROM Mesa m " + "LEFT JOIN MesaSala s ON m.id = s.id_mesa "
			+ "LEFT JOIN MesaTerraza t ON m.id = t.id_mesa";

	private static final String DESACTIVAR_MESA = "UPDATE Mesa SET activo = ? WHERE id = ?";
	private static final String UPDATE_MESA = "UPDATE Mesa SET numero = ?, ubicacion = ?, capacidad = ?, activo = ? WHERE id = ?";
	private static final String UPDATE_TERRAZA = "UPDATE MesaTerraza SET cubierta = ?, suplemento = ? WHERE id_mesa = ?";
	private static final String UPDATE_SALA = "UPDATE MesaSala SET reservada = ?, privacidad = ? WHERE id_mesa = ?";
	private static final String READ_BY_NUMERO = "SELECT m.*, s.reservada, s.privacidad, t.cubierta, t.suplemento "
			+ "FROM Mesa m " + "LEFT JOIN MesaSala s ON m.id = s.id_mesa "
			+ "LEFT JOIN MesaTerraza t ON m.id = t.id_mesa " + "WHERE m.numero = ? FOR UPDATE";

	@Override
	public Integer altaMesa(TMesa mesa) {
		Integer idGenerado = null;

		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) (Connection) t.getResource();

			try (PreparedStatement psMesa = c.prepareStatement(INSERT_MESA, Statement.RETURN_GENERATED_KEYS)) {
				psMesa.setInt(1, mesa.getNumero());
				psMesa.setString(2, mesa.getUbicacion());
				psMesa.setInt(3, mesa.getCapacidad());
				psMesa.setBoolean(4, mesa.getActivo());

				psMesa.executeUpdate();

				ResultSet rs = psMesa.getGeneratedKeys();
				if (rs.next()) {
					idGenerado = rs.getInt(1);
					mesa.setId(idGenerado);
				}
			}

			if (idGenerado != null) {
				if (mesa instanceof TMesaSala) {
					TMesaSala mesaS = (TMesaSala) mesa;
					try (PreparedStatement psSala = c.prepareStatement(INSERT_SALA)) {
						psSala.setInt(1, idGenerado);
						psSala.setBoolean(2, mesaS.getReservada());
						psSala.setString(3, mesaS.getPrivacidad());
						psSala.executeUpdate();
					}
				} else if (mesa instanceof TMesaTerraza) {
					TMesaTerraza mesaT = (TMesaTerraza) mesa;
					try (PreparedStatement psTerraza = c.prepareStatement(INSERT_TERRAZA)) {
						psTerraza.setInt(1, idGenerado);
						psTerraza.setBoolean(2, mesaT.getCubierta());
						psTerraza.setDouble(3, mesaT.getSuplemento());
						psTerraza.executeUpdate();
					}
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error en Integracion dande de alta mesa: " + e.getMessage());
		}

		return idGenerado;
	}

	@Override
	public Boolean bajaMesa(TMesa mesa) {
		boolean ok = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(DESACTIVAR_MESA)) {
				ps.setBoolean(1, mesa.getActivo());
				ps.setInt(2, mesa.getId());
				int rows = ps.executeUpdate();

				ok = rows > 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(
					"Error en Integracion dando de baja mesa " + mesa.getNumero() + ": " + e.getMessage());
		}
		return ok;
	}

	@Override
	public TMesa mostrarMesa(Integer id) {
		TMesa mesaResult = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null) {
				throw new IllegalStateException("No hay transacción activa al mostrar mesa");
			}

			Connection c = (Connection) (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(READ_BY_ID)) {
				ps.setInt(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {

						rs.getString("privacidad");
						boolean esSala = !rs.wasNull();

						if (esSala) {
							TMesaSala mesa = new TMesaSala();
							mesa.setReservada(rs.getBoolean("reservada"));
							mesa.setPrivacidad(rs.getString("privacidad"));
							mesaResult = mesa;
						} else {
							TMesaTerraza mesa = new TMesaTerraza();
							mesa.setCubierta(rs.getBoolean("cubierta"));
							mesa.setSuplemento(rs.getDouble("suplemento"));
							mesaResult = mesa;
						}

						mesaResult.setId(rs.getInt("id"));
						mesaResult.setNumero(rs.getInt("numero"));
						mesaResult.setUbicacion(rs.getString("ubicacion"));
						mesaResult.setCapacidad(rs.getInt("capacidad"));
						mesaResult.setActivo(rs.getBoolean("activo"));
					}
				}
			}
		} catch (SQLException e) {

			throw new RuntimeException("Error en Integracion buscando mesa por id: " + id, e);
		}

		return mesaResult;
	}

	@Override
	public List<TMesa> mostrarListaMesa() {
		List<TMesa> lista = new ArrayList<>();

		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null) {
				throw new IllegalStateException("No hay transacción activa al mostrar lista de mesas");
			}
			Connection c = (Connection) (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(ALL)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						TMesa mesaResult = null;

						rs.getString("privacidad");
						boolean esSala = !rs.wasNull();

						if (esSala) {
							TMesaSala mesa = new TMesaSala();
							mesa.setReservada(rs.getBoolean("reservada"));
							mesa.setPrivacidad(rs.getString("privacidad"));
							mesaResult = mesa;
						} else {
							TMesaTerraza mesa = new TMesaTerraza();
							mesa.setCubierta(rs.getBoolean("cubierta"));
							mesa.setSuplemento(rs.getDouble("suplemento"));
							mesaResult = mesa;
						}

						mesaResult.setId(rs.getInt("id"));
						mesaResult.setNumero(rs.getInt("numero"));
						mesaResult.setUbicacion(rs.getString("ubicacion"));
						mesaResult.setCapacidad(rs.getInt("capacidad"));
						mesaResult.setActivo(rs.getBoolean("activo"));

						lista.add(mesaResult);
					}
				}
			}

		} catch (SQLException e) {

			throw new RuntimeException("Error en Integración leyendo la lista de mesas: " + e.getMessage());
		}

		return lista;
	}

	@Override
	public Boolean modificarMesa(TMesa mesa) {
		Boolean ok = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null) {
				throw new IllegalStateException("No hay transacción activa al modificar la mesa");
			}
			Connection c = (Connection) (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(UPDATE_MESA)) {
				ps.setInt(1, mesa.getNumero());
				ps.setString(2, mesa.getUbicacion());
				ps.setInt(3, mesa.getCapacidad());
				ps.setBoolean(4, mesa.getActivo());
				ps.setInt(5, mesa.getId());
				ps.executeUpdate();
			}

			int rows = 0;

			if (mesa instanceof TMesaSala) {
				TMesaSala mesaS = (TMesaSala) mesa;
				try (PreparedStatement ps = c.prepareStatement(UPDATE_SALA)) {
					ps.setBoolean(1, mesaS.getReservada());
					ps.setString(2, mesaS.getPrivacidad());
					ps.setInt(3, mesaS.getId());
					rows = ps.executeUpdate();
				}
			} else if (mesa instanceof TMesaTerraza) {
				TMesaTerraza mesaT = (TMesaTerraza) mesa;
				try (PreparedStatement ps = c.prepareStatement(UPDATE_TERRAZA)) {
					ps.setBoolean(1, mesaT.getCubierta());
					ps.setDouble(2, mesaT.getSuplemento());
					ps.setInt(3, mesaT.getId());
					rows = ps.executeUpdate();
				}
			}
			ok = rows > 0;

		} catch (SQLException e) {

			throw new RuntimeException(
					"Error en Integración actualizando mesa " + mesa.getId() + ": " + e.getMessage());
		}
		return ok;
	}

	@Override
	public TMesa leerMesaPorNumero(Integer numero) {
		TMesa mesaResult = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null)
				throw new IllegalStateException("No hay transacción activa al buscar por número");
			Connection c = (Connection) (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(READ_BY_NUMERO)) {
				ps.setInt(1, numero);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						rs.getString("privacidad");
						boolean esSala = !rs.wasNull();

						if (esSala) {
							TMesaSala mesa = new TMesaSala();
							mesa.setReservada(rs.getBoolean("reservada"));
							mesa.setPrivacidad(rs.getString("privacidad"));
							mesaResult = mesa;
						} else {
							TMesaTerraza mesa = new TMesaTerraza();
							mesa.setCubierta(rs.getBoolean("cubierta"));
							mesa.setSuplemento(rs.getDouble("suplemento"));
							mesaResult = mesa;
						}

						mesaResult.setId(rs.getInt("id"));
						mesaResult.setNumero(rs.getInt("numero"));
						mesaResult.setUbicacion(rs.getString("ubicacion"));
						mesaResult.setCapacidad(rs.getInt("capacidad"));
						mesaResult.setActivo(rs.getBoolean("activo"));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error en Integracion buscando mesa por numero: " + numero, e);
		}

		return mesaResult;
	}
}
