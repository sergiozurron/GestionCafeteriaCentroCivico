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
	private static final String INSERT_MESA = "INSERT INTO mesas (numero, ubicacion, capacidad, activo) VALUES (?, ?, ?, ?)";
	private static final String INSERT_SALA = "INSERT INTO salas (id_mesa, reservada, privacidad) VALUES (?, ?, ?)";
	private static final String INSERT_TERRAZA = "INSERT INTO terrazas (id_mesa, cubierta, suplemento) VALUES (?, ?, ?)";
	private static final String UPDATE_MESA = "UPDATE mesas SET numero = ?, ubicacion = ?, capacidad = ?, activo = ? WHERE id = ?";
	private static final String UPDATE_SALA = "UPDATE salas SET reservada = ?, privacidad = ? WHERE id_mesa = ?";
	private static final String UPDATE_TERRAZA = "UPDATE terrazas SET cubierta = ?, suplemento = ? WHERE id_mesa = ?";
	private static final String DESACTIVAR_MESA = "UPDATE mesas SET activo = ? WHERE id = ?";
	private static final String BASE_SELECT = "SELECT m.id, m.numero, m.ubicacion, m.capacidad, m.activo, "
			+ "s.reservada, s.privacidad, t.cubierta, t.suplemento " + "FROM mesas m "
			+ "LEFT JOIN salas s ON m.id = s.id_mesa " + "LEFT JOIN terrazas t ON m.id = t.id_mesa";
	private static final String READ_BY_ID = BASE_SELECT + " WHERE m.id = ? FOR UPDATE";
	private static final String ALL = BASE_SELECT;
	private static final String ALL_ACTIVAS = BASE_SELECT + " WHERE m.activo = true";

	@Override
	public Integer altaMesa(TMesa mesa) {
		Integer idGenerado = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

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
				} else {
					throw new RuntimeException("No se pudo obtener el ID generado para la mesa.");
				}
			}

			if (mesa instanceof TMesaSala) {
				TMesaSala sala = (TMesaSala) mesa;
				try (PreparedStatement psSala = c.prepareStatement(INSERT_SALA)) {
					psSala.setInt(1, idGenerado);
					psSala.setBoolean(2, sala.getReservada() != null ? sala.getReservada() : false);
					psSala.setString(3, sala.getPrivacidad());
					psSala.executeUpdate();
				}
			} else if (mesa instanceof TMesaTerraza) {
				TMesaTerraza terraza = (TMesaTerraza) mesa;
				try (PreparedStatement psTerraza = c.prepareStatement(INSERT_TERRAZA)) {
					psTerraza.setInt(1, idGenerado);
					psTerraza.setBoolean(2, terraza.getCubierta() != null ? terraza.getCubierta() : false);
					psTerraza.setDouble(3, terraza.getSuplemento() != null ? terraza.getSuplemento() : 0.0);
					psTerraza.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error en Integracion dando de alta mesa: " + e.getMessage());
		}
		return idGenerado;
	}

	@Override
	public Boolean bajaMesa(TMesa mesa) {
		boolean ok = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(DESACTIVAR_MESA)) {
				ps.setBoolean(1, mesa.getActivo());
				ps.setInt(2, mesa.getId());
				ok = ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(
					"Error en Integracion dando de baja mesa " + mesa.getId() + ": " + e.getMessage());
		}
		return ok;
	}

	@Override
	public TMesa mostrarMesa(Integer id) {
		TMesa mesaResult = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(READ_BY_ID)) {
				ps.setInt(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						mesaResult = extraerMesaDeResultSet(rs);
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
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(ALL)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						lista.add(extraerMesaDeResultSet(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error en Integración leyendo la lista de mesas: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public List<TMesa> mostrarListaMesaActivas() {
		List<TMesa> lista = new ArrayList<>();
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(ALL_ACTIVAS)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						lista.add(extraerMesaDeResultSet(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error en Integración leyendo la lista de mesas activas: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public Boolean modificarMesa(TMesa mesa) {
		Boolean ok = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(UPDATE_MESA)) {
				ps.setInt(1, mesa.getNumero());
				ps.setString(2, mesa.getUbicacion());
				ps.setInt(3, mesa.getCapacidad());
				ps.setBoolean(4, mesa.getActivo());
				ps.setInt(5, mesa.getId());
				ps.executeUpdate();
			}

			if (mesa instanceof TMesaSala) {
				TMesaSala sala = (TMesaSala) mesa;
				try (PreparedStatement psSala = c.prepareStatement(UPDATE_SALA)) {
					psSala.setBoolean(1, sala.getReservada() != null ? sala.getReservada() : false);
					psSala.setString(2, sala.getPrivacidad());
					psSala.setInt(3, sala.getId());
					psSala.executeUpdate();
				}
			} else if (mesa instanceof TMesaTerraza) {
				TMesaTerraza terraza = (TMesaTerraza) mesa;
				try (PreparedStatement psTerraza = c.prepareStatement(UPDATE_TERRAZA)) {
					psTerraza.setBoolean(1, terraza.getCubierta() != null ? terraza.getCubierta() : false);
					psTerraza.setDouble(2, terraza.getSuplemento() != null ? terraza.getSuplemento() : 0.0);
					psTerraza.setInt(3, terraza.getId());
					psTerraza.executeUpdate();
				}
			}
			ok = true;
		} catch (SQLException e) {
			throw new RuntimeException(
					"Error en Integración actualizando mesa " + mesa.getId() + ": " + e.getMessage());
		}
		return ok;
	}

	private TMesa extraerMesaDeResultSet(ResultSet rs) throws SQLException {
		TMesa mesaResult;

		if (rs.getObject("reservada") != null) {
			TMesaSala sala = new TMesaSala();
			sala.setReservada(rs.getBoolean("reservada"));
			sala.setPrivacidad(rs.getString("privacidad"));
			mesaResult = sala;
		} else {
			TMesaTerraza terraza = new TMesaTerraza();
			terraza.setCubierta(rs.getBoolean("cubierta"));
			terraza.setSuplemento(rs.getDouble("suplemento"));
			mesaResult = terraza;
		}

		mesaResult.setId(rs.getInt("id"));
		mesaResult.setNumero(rs.getInt("numero"));
		mesaResult.setUbicacion(rs.getString("ubicacion"));
		mesaResult.setCapacidad(rs.getInt("capacidad"));
		mesaResult.setActivo(rs.getBoolean("activo"));

		return mesaResult;
	}
}