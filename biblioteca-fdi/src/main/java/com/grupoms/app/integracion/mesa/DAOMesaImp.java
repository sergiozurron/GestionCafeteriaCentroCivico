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

	// ¡Míralo! Adiós a los JOIN y a las tablas extra.
	private static final String INSERT_MESA = "INSERT INTO mesas (numero, ubicacion, capacidad, activo, tipo, reservada, privacidad, cubierta, suplemento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String READ_BY_ID = "SELECT * FROM mesas WHERE id = ? FOR UPDATE";
	private static final String ALL = "SELECT * FROM mesas";
	private static final String ALL_ACTIVAS = "SELECT * FROM mesas WHERE activo = true";
	private static final String DESACTIVAR_MESA = "UPDATE mesas SET activo = ? WHERE id = ?";
	private static final String READ_BY_NUMERO = "SELECT * FROM mesas WHERE numero = ? FOR UPDATE";
	
	// Un solo UPDATE que machaca la fila entera y cambia el tipo sin problemas
	private static final String UPDATE_MESA = "UPDATE mesas SET numero = ?, ubicacion = ?, capacidad = ?, activo = ?, tipo = ?, reservada = ?, privacidad = ?, cubierta = ?, suplemento = ? WHERE id = ?";

	@Override
	public Integer altaMesa(TMesa mesa) {
		Integer idGenerado = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(INSERT_MESA, Statement.RETURN_GENERATED_KEYS)) {
				prepararStatementMesa(ps, mesa); // Usamos un método auxiliar para no repetir código
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					idGenerado = rs.getInt(1);
					mesa.setId(idGenerado);
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
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(DESACTIVAR_MESA)) {
				ps.setBoolean(1, mesa.getActivo());
				ps.setInt(2, mesa.getId());
				ok = ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error en Integracion dando de baja mesa " + mesa.getId() + ": " + e.getMessage());
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
	public Boolean modificarMesa(TMesa mesa) {
		Boolean ok = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(UPDATE_MESA)) {
				prepararStatementMesa(ps, mesa);
				ps.setInt(10, mesa.getId()); // El parámetro 10 es el WHERE id = ?
				ps.executeUpdate();
				ok = true; // Si no hay SQLException, el UPDATE fue bien
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error en Integración actualizando mesa " + mesa.getId() + ": " + e.getMessage());
		}
		return ok;
	}

	@Override
	public TMesa leerMesaPorNumero(Integer numero) {
		TMesa mesaResult = null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(READ_BY_NUMERO)) {
				ps.setInt(1, numero);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						mesaResult = extraerMesaDeResultSet(rs);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error en Integracion buscando mesa por numero: " + numero, e);
		}
		return mesaResult;
	}

	// --- MÉTODOS AUXILIARES PARA NO REPETIR CÓDIGO ---

	private void prepararStatementMesa(PreparedStatement ps, TMesa mesa) throws SQLException {
		ps.setInt(1, mesa.getNumero());
		ps.setString(2, mesa.getUbicacion());
		ps.setInt(3, mesa.getCapacidad());
		ps.setBoolean(4, mesa.getActivo());

		if (mesa instanceof TMesaSala) {
			TMesaSala sala = (TMesaSala) mesa;
			ps.setString(5, "Sala");
			ps.setBoolean(6, sala.getReservada() != null ? sala.getReservada() : false);
			ps.setString(7, sala.getPrivacidad());
			ps.setNull(8, Types.BOOLEAN); // La sala no tiene cubierta
			ps.setNull(9, Types.DECIMAL); // La sala no tiene suplemento
		} else if (mesa instanceof TMesaTerraza) {
			TMesaTerraza terraza = (TMesaTerraza) mesa;
			ps.setString(5, "Terraza");
			ps.setNull(6, Types.BOOLEAN); // La terraza no se reserva igual
			ps.setNull(7, Types.VARCHAR); // La terraza no tiene privacidad
			ps.setBoolean(8, terraza.getCubierta() != null ? terraza.getCubierta() : false);
			ps.setDouble(9, terraza.getSuplemento() != null ? terraza.getSuplemento() : 0.0);
		}
	}

	private TMesa extraerMesaDeResultSet(ResultSet rs) throws SQLException {
		TMesa mesaResult;
		String tipo = rs.getString("tipo");

		if ("Sala".equalsIgnoreCase(tipo)) {
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
	

	@Override
	public List<TMesa> mostrarListaMesaActivas() {
	    List<TMesa> lista = new ArrayList<>();
	    try {
	        Transaction t = TransactionManager.getInstance().getTransaction();
	        Connection c = (Connection) t.getResource(); // Correcto uso del recurso [cite: 295]

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
}