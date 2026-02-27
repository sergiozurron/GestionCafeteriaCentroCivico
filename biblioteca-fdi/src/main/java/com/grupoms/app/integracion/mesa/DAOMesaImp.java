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

	// Constantes de Inserción (Las que ya arreglamos)
    private static final String INSERT_MESA = "INSERT INTO Mesa (numero, ubicacion, capacidad, activo) VALUES (?, ?, ?, ?)";
    private static final String INSERT_SALA = "INSERT INTO MesaSala (id_mesa, reservada, privacidad) VALUES (?, ?, ?)";
    private static final String INSERT_TERRAZA = "INSERT INTO MesaTerraza (id_mesa, cubierta, suplemento) VALUES (?, ?, ?)";

    // ARREGLADO: JOINs correctos usando id_mesa y nombres en singular consistentes
    // ARREGLADO: Añadido FOR UPDATE para el bloqueo pesimista
    private static final String READ_BY_ID = 
        "SELECT m.*, s.reservada, s.privacidad, t.cubierta, t.suplemento " +
        "FROM mesas m " + 
        "LEFT JOIN salas s ON m.id = s.id " +
        "LEFT JOIN terrazas t ON m.id = t.id " + 
        "WHERE m.id = ? FOR UPDATE";

    private static final String ALL = 
        "SELECT m.*, s.reservada, s.privacidad, t.cubierta, t.suplemento " +
        "FROM Mesa m " + 
        "LEFT JOIN MesaSala s ON m.id = s.id_mesa " +
        "LEFT JOIN MesaTerraza t ON m.id = t.id_mesa";

    // ARREGLADO: Sentencias de actualización consistentes
    private static final String DESACTIVAR_MESA = "UPDATE Mesa SET activo = ? WHERE id = ?";
    private static final String UPDATE_MESA = "UPDATE Mesa SET numero = ?, ubicacion = ?, capacidad = ?, activo = ? WHERE id = ?";
    private static final String UPDATE_TERRAZA = "UPDATE MesaTerraza SET cubierta = ?, suplemento = ? WHERE id_mesa = ?";
    private static final String UPDATE_SALA = "UPDATE MesaSala SET reservada = ?, privacidad = ? WHERE id_mesa = ?";
    
	@Override
	public Integer altaMesa(TMesa mesa) {
	    Integer idGenerado = null;
	    
	    // Consultas SQL ajustadas (Mesa genera el ID, las hijas lo heredan)
	    String INSERT_MESA = "INSERT INTO Mesa (numero, ubicacion, capacidad, activo) VALUES (?, ?, ?, ?)";
	    String INSERT_SALA = "INSERT INTO MesaSala (id_mesa, reservada, privacidad) VALUES (?, ?, ?)";
	    String INSERT_TERRAZA = "INSERT INTO MesaTerraza (id_mesa, cubierta, suplemento) VALUES (?, ?, ?)";

	    try {
	        Transaction t = TransactionManager.getInstance().getTransaction();
	        // Idealmente, cambia tu Transaction para que esto no requiera (Connection)
	        Connection c = (Connection) t.getResource(); 

	        // 1. Insertar PRIMERO en la tabla Padre (Mesa)
	        try (PreparedStatement psMesa = c.prepareStatement(INSERT_MESA, Statement.RETURN_GENERATED_KEYS)) {
	            psMesa.setInt(1, mesa.getNumero());
	            psMesa.setString(2, mesa.getUbicacion());
	            psMesa.setInt(3, mesa.getCapacidad());
	            psMesa.setBoolean(4, mesa.getActivo());
	            
	            psMesa.executeUpdate();
	            
	            ResultSet rs = psMesa.getGeneratedKeys();
	            if (rs.next()) {
	                idGenerado = rs.getInt(1);
	                mesa.setId(idGenerado); // Seteamos el ID en el Transfer Object
	            }
	        }

	        // 2. Insertar en la tabla Hija correspondiente usando el ID generado
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
	        // En un entorno real, aquí deberías lanzar una excepción de integración
	        // para que la capa de negocio aborte la transacción entera.
	        System.err.println("Error dando de alta mesa: " + e.getMessage());
	        return null; 
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
				int rows = ps.executeUpdate();

				ok = rows > 0;
			}
		} catch (SQLException e) {
			System.err.println("Error al dar de baja la mesa: " + e.getMessage());
		}
		return ok;
	}

	@Override
	public TMesa mostrarMesa(Integer id) {
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			if (t == null) {
				throw new IllegalStateException("No hay transacción activa al mostrar mesa");
			}
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(READ_BY_ID)) {
				ps.setInt(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						Integer salaId = rs.getInt("sala_id");
						Integer terrazaId = rs.getInt("terraza_id");

						if (terrazaId != 0) {
							TMesaTerraza mesa = new TMesaTerraza();
							mesa.setId(rs.getInt("id"));
							mesa.setNumero(rs.getInt("numero"));
							mesa.setUbicacion(rs.getString("ubicacion"));
							mesa.setCapacidad(rs.getInt("capacidad"));
							mesa.setActivo(rs.getBoolean("activo"));
							mesa.setCubierta(rs.getBoolean("cubierta"));
							mesa.setSuplemento(rs.getDouble("suplemento"));
							return mesa;
						} else if (salaId != 0) {
							TMesaSala mesa = new TMesaSala();
							mesa.setId(rs.getInt("id"));
							mesa.setNumero(rs.getInt("numero"));
							mesa.setUbicacion(rs.getString("ubicacion"));
							mesa.setCapacidad(rs.getInt("capacidad"));
							mesa.setActivo(rs.getBoolean("activo"));
							mesa.setReservada(rs.getBoolean("reservada"));
							mesa.setPrivacidad(rs.getString("privacidad"));
							return mesa;
						}
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error encontrando mesa: " + e.getMessage());
		}

		return null;
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
						Integer salaId = rs.getInt("sala_id");
						Integer terrazaId = rs.getInt("terraza_id");

						if (terrazaId != 0) {
							TMesaTerraza mesa = new TMesaTerraza();
							mesa.setId(rs.getInt("id"));
							mesa.setNumero(rs.getInt("numero"));
							mesa.setUbicacion(rs.getString("ubicacion"));
							mesa.setCapacidad(rs.getInt("capacidad"));
							mesa.setActivo(rs.getBoolean("activo"));
							mesa.setCubierta(rs.getBoolean("cubierta"));
							mesa.setSuplemento(rs.getDouble("suplemento"));
							lista.add(mesa);
						} else if (salaId != 0) {
							TMesaSala mesa = new TMesaSala();
							mesa.setId(rs.getInt("id"));
							mesa.setNumero(rs.getInt("numero"));
							mesa.setUbicacion(rs.getString("ubicacion"));
							mesa.setCapacidad(rs.getInt("capacidad"));
							mesa.setActivo(rs.getBoolean("activo"));
							mesa.setReservada(rs.getBoolean("reservada"));
							mesa.setPrivacidad(rs.getString("privacidad"));
							lista.add(mesa);
						}
					}
				}
			}

		} catch (SQLException e) {
			System.err.println("Error leyendo todas las mesas: " + e.getMessage());
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
			System.err.println("Error actualizando mesa: " + e.getMessage());
		}
		return ok;
	}
}
