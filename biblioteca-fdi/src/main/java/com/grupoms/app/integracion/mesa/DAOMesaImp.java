
package com.grupoms.app.integracion.mesa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.negocio.mesa.TMesaTerraza;

public class DAOMesaImp implements DAOMesa{
	
	private static final String INSERT= "INSERT INTO MESAS(ubicacion, numero, capacidad, activo) VALUES (?, ?, ?)";
	private static final String INSERT_TERRAZA = "INSERT INTO MESAS(tipo, suplemento, cubierta) VALUES (?, ?, ?)";
	private static final String INSERT_SALA = "INSERT INTO MESAS(tipo, reservada, privacidad) VALUES (?, ?, ?)";
	private static final String READ_BY_ID = "SELECT * FROM MESAS WHERE id = ?";
	private static final String DESACTIVAR_MESA = "UPDATE MESAS SET activo = ? WHERE id = ?";
	private static final String UPDATE_TERRAZA = "UPDATE MESAS SET ubicacion = ?, numero = ?, capacidad = ?, activo = ?, suplemento = ?, cubierta = ? WHERE id = ?";
	private static final String UPDATE_SALA = "UPDATE MESAS SET ubicacion = ?, numero = ?, capacidad = ?, activo = ?, reservada = ?, privacidad = ? WHERE id = ?";
	private static final String ALL = "SELECT * FROM MESAS";
	
	@Override
	public Integer altaMesa(TMesa mesa) {

		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection)t.getResource();

			try (PreparedStatement ps = c.prepareStatement(INSERT)){
				ps.setString(1, mesa.getUbicacion());
			ps.setInt(2, mesa.getNumero());
			ps.setInt(3, mesa.getCapacidad());
			ps.setBoolean(4, mesa.getActivo());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			mesa.setId(rs.getInt(1));
			if("Terraza".equals(mesa.getTipo())) {
				PreparedStatement stmt2 = c.prepareStatement(INSERT_TERRAZA, Statement.RETURN_GENERATED_KEYS);

				stmt2.setString(1, mesa.getTipo());
				stmt2.setDouble(2, mesa.getSuplemento());
				stmt2.setBoolean(3, mesa.getCubierta());		
			}
			else {
				PreparedStatement stmt2 = c.prepareStatement(INSERT_SALA, Statement.RETURN_GENERATED_KEYS);
				stmt2.setString(1, mesa.getTipo());
				stmt2.setBoolean(2, mesa.getReservada());
				stmt2.setString(3, mesa.getPrivacidad());
			}
			ps.executeUpdate();

			}
		}catch (SQLException e) {
	        System.err.println("Error dando de alta mesa: " + e.getMessage());
		}
		return mesa.getId();
		
	}
	
	@Override
	public void bajaMesa(Integer id) {
		try{
			Transaction t=  TransactionManager.getInstance().getTransaction();
			Connection c = (Connection)t.getResource();
			try(PreparedStatement ps = c.prepareStatement(DESACTIVAR_MESA)){
				ps.setInt(2,id);
				ps.setBoolean(1,false);
				ps.executeUpdate();
				ps.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}  

	}


	@Override
	public TMesa mostrarMesa(Integer id) {
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(READ_BY_ID)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String tipo = rs.getString("tipo");
					if("Terraza".equals(tipo)) {
						TMesa mesa = new TMesaTerraza();
						mesa.setId(rs.getInt("id"));
						mesa.setUbicacion(rs.getString("ubicacion"));
						mesa.setNumero(rs.getInt("numero"));
						mesa.setCapacidad(rs.getInt("capacidad"));
						mesa.setActivo(rs.getBoolean("activo"));
						mesa.setCubierta(rs.getBoolean("cubierta"));
						mesa.setSuplemento(rs.getDouble("suplemento"));
						return mesa;
					}
					else {
						TMesa mesa = new TMesaSala();
						mesa.setId(rs.getInt("id"));
						mesa.setUbicacion(rs.getString("ubicacion"));
						mesa.setNumero(rs.getInt("numero"));
						mesa.setCapacidad(rs.getInt("capacidad"));
						mesa.setActivo(rs.getBoolean("activo"));
						mesa.setReservada(rs.getBoolean("reservada"));
						mesa.setPrivacidad(rs.getString("privacidad"));
						return mesa;
					}
				}
			}
		} catch (SQLException e) {
	        System.err.println("Error encontrando mesa: " + e.getMessage());
		}
		return null;
	}
	

	@Override
	public List<TMesa> mostrarListaMesa(){
	    List<TMesa> lista = new ArrayList<>();

	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(ALL)) {

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	        	String tipo = rs.getString("tipo");
				if("Terraza".equals(tipo)) {
					TMesa mesa = new TMesaTerraza();
					mesa.setId(rs.getInt("id"));
					mesa.setUbicacion(rs.getString("ubicacion"));
					mesa.setNumero(rs.getInt("numero"));
					mesa.setCapacidad(rs.getInt("capacidad"));
					mesa.setActivo(rs.getBoolean("activo"));
					mesa.setCubierta(rs.getBoolean("cubierta"));
					mesa.setSuplemento(rs.getDouble("suplemento"));
		            lista.add(mesa);
				}
				else {
					TMesa mesa = new TMesaSala();
					mesa.setId(rs.getInt("id"));
					mesa.setUbicacion(rs.getString("ubicacion"));
					mesa.setNumero(rs.getInt("numero"));
					mesa.setCapacidad(rs.getInt("capacidad"));
					mesa.setActivo(rs.getBoolean("activo"));
					mesa.setReservada(rs.getBoolean("reservada"));
					mesa.setPrivacidad(rs.getString("privacidad"));
		            lista.add(mesa);
				}
	        }

	    } catch (SQLException e) {
	        System.err.println("Error leyendo todas las mesas: " + e.getMessage());
	    }

	    return lista;
	}
	
	@Override
	public void modificarMesa(TMesa mesa) {

	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(UPDATE_TERRAZA)) {
	    	if(mesa.getTipo().equals("sala")) {
		    	PreparedStatement ps2 = conn.prepareStatement(UPDATE_SALA);
		        ps2.setString(1, mesa.getUbicacion());
		        ps2.setInt(2, mesa.getNumero());
		        ps2.setInt(3, mesa.getCapacidad());
		        ps2.setBoolean(4, mesa.getActivo());
		        ps2.setBoolean(5, mesa.getReservada());
		        ps2.setString(6, mesa.getPrivacidad());
		        ps2.executeUpdate();
	    	}
	    	else {
	    		ps.setString(1, mesa.getUbicacion());
				ps.setInt(2, mesa.getNumero());
		        ps.setInt(3, mesa.getCapacidad());
				ps.setBoolean(4, mesa.getActivo());
				ps.setDouble(5, mesa.getSuplemento());
				ps.setBoolean(6, mesa.getCubierta());
	    		ps.executeUpdate();
	    	}

	    } catch (SQLException e) {
	        System.err.println("Error actualizando mesa: " + e.getMessage());
	    }

	}


}
