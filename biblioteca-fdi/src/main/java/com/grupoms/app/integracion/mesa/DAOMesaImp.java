
package com.grupoms.app.integracion.mesa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.negocio.mesa.TMesaTerraza;

public class DAOMesaImp implements DAOMesa{
	
	
	private static final String READ_BY_ID = "SELECT * FROM MESAS WHERE id = ?";
	private static final String DESACTIVAR_MESA = "UPDATE MESAS SET activo = ? WHERE id = ?";
	private static final String UPDATE_TERRAZA = "UPDATE MESAS SET ubicacion = ?, numero = ?, capacidad = ?, activo = ?, suplemento = ?, cubierta = ? WHERE id = ?";
	private static final String UPDATE_SALA = "UPDATE MESAS SET ubicacion = ?, numero = ?, capacidad = ?, activo = ?, reservada = ?, privacidad = ? WHERE id = ?";
	private static final String ALL = "SELECT * FROM MESAS";
	
public Integer altaMesa(TMesa mesa) {
    if (mesa == null) return -1;
    try {
        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();

        Integer subId = null; // id de sala o terraza

        if ("Terraza".equalsIgnoreCase(mesa.getTipo())) {
            String sqlTerraza = "INSERT INTO terrazas(cubierta, suplemento) VALUES (?, ?)";
            try (PreparedStatement ps = c.prepareStatement(sqlTerraza, Statement.RETURN_GENERATED_KEYS)) {
                TMesaTerraza terraza = (TMesaTerraza) mesa;
                ps.setBoolean(1, terraza.getCubierta() != null ? terraza.getCubierta() : false);
                ps.setDouble(2, terraza.getSuplemento() != null ? terraza.getSuplemento() : 0.0);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) subId = rs.getInt(1);
                }
            }
        } else { // Sala
            String sqlSala = "INSERT INTO salas(reservada, privacidad) VALUES (?, ?)";
            try (PreparedStatement ps = c.prepareStatement(sqlSala, Statement.RETURN_GENERATED_KEYS)) {
                TMesaSala sala = (TMesaSala) mesa;
                ps.setBoolean(1, sala.getReservada() != null ? sala.getReservada() : false);
                ps.setString(2, sala.getPrivacidad() != null ? sala.getPrivacidad() : "");
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) subId = rs.getInt(1);
                }
            }
        }

        if (subId == null) return -1;

        // Ahora insertamos la mesa
        String sqlMesa = "INSERT INTO mesas(numero, ubicacion, capacidad, activo, sala_id, terraza_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sqlMesa, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, mesa.getNumero());
            ps.setString(2, mesa.getUbicacion());
            ps.setInt(3, mesa.getCapacidad());
            ps.setBoolean(4, mesa.getActivo());

            if ("Terraza".equalsIgnoreCase(mesa.getTipo())) {
                ps.setNull(5, java.sql.Types.INTEGER); // sala_id
                ps.setInt(6, subId); // terraza_id
            } else {
                ps.setInt(5, subId); // sala_id
                ps.setNull(6, java.sql.Types.INTEGER); // terraza_id
            }

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    mesa.setId(rs.getInt(1));
                    return mesa.getId();
                } else return -1;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        return -1;
    }
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
		TMesa mesa = null;
    try {

        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();

        String sql = "SELECT * FROM mesa WHERE id = ? FOR UPDATE";
        try (PreparedStatement s = c.prepareStatement(sql)) {
            s.setInt(1, id);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    if ("Terraza".equalsIgnoreCase(tipo)) {
                        TMesaTerraza terraza = new TMesaTerraza();
                        terraza.setId(rs.getInt("id"));
                        terraza.setUbicacion(rs.getString("ubicacion"));
                        terraza.setNumero(rs.getInt("numero"));
                        terraza.setCapacidad(rs.getInt("capacidad"));
                        terraza.setActivo(rs.getBoolean("activo"));
                        terraza.setCubierta(rs.getBoolean("cubierta"));
                        terraza.setSuplemento(rs.getDouble("suplemento"));
                        mesa = terraza;
                    } else { // Sala
                        TMesaSala sala = new TMesaSala();
                        sala.setId(rs.getInt("id"));
                        sala.setUbicacion(rs.getString("ubicacion"));
                        sala.setNumero(rs.getInt("numero"));
                        sala.setCapacidad(rs.getInt("capacidad"));
                        sala.setActivo(rs.getBoolean("activo"));
                        sala.setReservada(rs.getBoolean("reservada"));
                        sala.setPrivacidad(rs.getString("privacidad"));
                        mesa = sala;
                    }
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
	return mesa;
	}

	

	@Override
	public List<TMesa> mostrarListaMesa(){
	     List<TMesa> mesas = new ArrayList<>();
    try {
        TransactionManager tm = TransactionManager.getInstance();
        Transaction t = tm.getTransaction();
        Connection c = (Connection) t.getResource();

        String sql = "SELECT * FROM mesa FOR UPDATE";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TMesa mesa;
                String tipo = rs.getString("tipo");

                if ("Terraza".equalsIgnoreCase(tipo)) {
                    TMesaTerraza terraza = new TMesaTerraza();
                    terraza.setId(rs.getInt("id"));
                    terraza.setUbicacion(rs.getString("ubicacion"));
                    terraza.setNumero(rs.getInt("numero"));
                    terraza.setCapacidad(rs.getInt("capacidad"));
                    terraza.setActivo(rs.getBoolean("activo"));
                    terraza.setCubierta(rs.getBoolean("cubierta"));
                    terraza.setSuplemento(rs.getDouble("suplemento"));
                    mesa = terraza;
                } else {
                    TMesaSala sala = new TMesaSala();
                    sala.setId(rs.getInt("id"));
                    sala.setUbicacion(rs.getString("ubicacion"));
                    sala.setNumero(rs.getInt("numero"));
                    sala.setCapacidad(rs.getInt("capacidad"));
                    sala.setActivo(rs.getBoolean("activo"));
                    sala.setReservada(rs.getBoolean("reservada"));
                    sala.setPrivacidad(rs.getString("privacidad"));
                    mesa = sala;
                }

                mesas.add(mesa);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }

    return mesas;
	}
	
	@Override
	public void modificarMesa(TMesa mesa) {
		try {
				TransactionManager tm = TransactionManager.getInstance();
				Transaction t = tm.getTransaction();
				Connection c = (Connection) t.getResource();

				// Actualizar campos comunes
				String sql = "UPDATE mesa SET ubicacion = ?, numero = ?, capacidad = ?, activo = ? WHERE id = ?";
				try (PreparedStatement ps = c.prepareStatement(sql)) {
					ps.setString(1, mesa.getUbicacion());
					ps.setInt(2, mesa.getNumero());
					ps.setInt(3, mesa.getCapacidad());
					ps.setBoolean(4, mesa.getActivo());
					ps.setInt(5, mesa.getId());
					ps.executeUpdate();
				}
				String tipo = mesa.getTipo();
				// Actualizar campos específicos según tipo de mesa
				if (tipo == "Terraza") {
					TMesaTerraza terraza = (TMesaTerraza)mesa;
					sql = "UPDATE mesa SET cubierta = ?, suplemento = ? WHERE id = ?";
					try (PreparedStatement ps = c.prepareStatement(sql)) {
						ps.setBoolean(1, terraza.getCubierta());
						ps.setDouble(2, terraza.getSuplemento());
						ps.setInt(3, terraza.getId());
						ps.executeUpdate();
					}
				} else if (tipo == "Sala") {
					TMesaSala sala = (TMesaSala)mesa;
					sql = "UPDATE mesa SET reservada = ?, privacidad = ? WHERE id = ?";
					try (PreparedStatement ps = c.prepareStatement(sql)) {
						ps.setBoolean(1, sala.getReservada());
						ps.setString(2, sala.getPrivacidad());
						ps.setInt(3, sala.getId());
						ps.executeUpdate();
					}
				}


			} catch (Exception e) {

			}

	}


}
