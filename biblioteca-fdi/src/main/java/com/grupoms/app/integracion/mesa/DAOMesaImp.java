package com.grupoms.app.integracion.mesa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.negocio.mesa.TMesa;

public class DAOMesaImp implements DAOMesa{

	private static final String INSERT = "INSERT INTO MESAS(ubicacion, numero, activo) VALUES (?, ?, ?)";
	private static final String READ_BY_ID = "SELECT * FROM PROVEEDORES WHERE id = ?";
	private static final String READ_BY_NUMBER = "SELECT * FROM PROVEEDORES WHERE numero = ?";
	private static final String UPDATE = "UPDATE MESAS SET ubicacion = ?, numero = ?, activo = ? WHERE id = ?";
	private static final String ALL = "SELECT * FROM MESAS";
	
	@Override
	public void crea(TMesa mesa) {
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, mesa.getUbicacion());
			stmt.setInt(2, mesa.getNumero());
			stmt.setBoolean(3, mesa.getActivo());

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			mesa.setId(rs.getInt(1));

		} catch (SQLException e) {
	        System.err.println("Error dando de alta mesa: " + e.getMessage());
		}
	}

	@Override
	public TMesa buscaPorNumero(Integer numero) {
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(READ_BY_NUMBER)) {
			stmt.setInt(1, numero);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					TMesa mesa = new TMesa();
					mesa.setId(rs.getInt("id"));
					mesa.setUbicacion(rs.getString("ubicacion"));
					mesa.setNumero(rs.getInt("numero"));
					mesa.setActivo(rs.getBoolean("activo"));
					return mesa;
				}
			}
		} catch (SQLException e) {
	        System.err.println("Error encontrando mesa: " + e.getMessage());
		}
		return null;
	}

	@Override
	public TMesa buscarPorId(Integer id) {
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(READ_BY_ID)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					TMesa mesa = new TMesa();
					mesa.setId(rs.getInt("id"));
					mesa.setUbicacion(rs.getString("ubicacion"));
					mesa.setNumero(rs.getInt("numero"));
					mesa.setActivo(rs.getBoolean("activo"));
					return mesa;
				}
			}
		} catch (SQLException e) {
	        System.err.println("Error encontrando mesa: " + e.getMessage());
		}
		return null;
	}

	@Override
	public void eliminaTodos() {
		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("DELETE FROM MESAS");
		} catch (SQLException e) {
	        System.err.println("Error eliminando todas las mesas: " + e.getMessage());
		}		
	}
	
	@Override
	public List<TMesa> mostrarTodos(){
	    List<TMesa> lista = new ArrayList<>();

	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(ALL)) {

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	        	TMesa mesa = new TMesa();
	            mesa.setId(rs.getInt("id"));
	            mesa.setUbicacion(rs.getString("ubicacion"));
	            mesa.setNumero(rs.getInt("numero"));
	            mesa.setActivo(rs.getBoolean("activo"));
	            lista.add(mesa);
	        }

	    } catch (SQLException e) {
	        System.err.println("Error leyendo todas las mesas: " + e.getMessage());
	    }

	    return lista;
	}
	
	@Override
	public void update(TMesa mesa) {

	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(UPDATE)) {

	        ps.setString(1, mesa.getUbicacion());
	        ps.setInt(2, mesa.getNumero());
	        ps.setBoolean(3, mesa.getActivo());

	        ps.executeUpdate();

	    } catch (SQLException e) {
	        System.err.println("Error actualizando mesa: " + e.getMessage());
	    }

	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(System.getenv("MS_DB_URL"), System.getenv("MS_DB_USERNAME"),
				System.getenv("MS_DB_PASSWORD"));
	}
}
