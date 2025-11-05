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

    private static final String INSERT_MESA = "INSERT INTO MESAS(numero, ubicacion, capacidad, activo, sala_id, terraza_id) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String INSERT_TERRAZA = "INSERT INTO TERRAZAS(cubierta, suplemento) VALUES (?, ?)";

    private static final String INSERT_SALA = "INSERT INTO SALAS(reservada, privacidad) VALUES (?, ?)";

    private static final String READ_BY_ID =
    		"SELECT m.*, s.reservada, s.privacidad, t.cubierta, t.suplemento " +
			"FROM MESAS m " +
			"LEFT JOIN SALAS s ON m.sala_id = s.id " +
			"LEFT JOIN TERRAZAS t ON m.terraza_id = t.id " +
			"WHERE m.id = ?";
	
	private static final String DESACTIVAR_MESA = "UPDATE MESAS SET activo = false WHERE id = ?";

    private static final String UPDATE_MESA = "UPDATE MESAS SET numero = ?, ubicacion = ?, capacidad = ?, activo = ? WHERE id = ?";

    private static final String UPDATE_TERRAZA = "UPDATE TERRAZAS SET cubierta = ?, suplemento = ? WHERE id = ?";

    private static final String UPDATE_SALA = "UPDATE SALAS SET reservada = ?, privacidad = ? WHERE id = ?";

    private static final String ALL =
            "SELECT m.*, s.reservada, s.privacidad, t.cubierta, t.suplemento " +
            "FROM MESAS m " +
            "LEFT JOIN SALAS s ON m.sala_id = s.id " +
            "LEFT JOIN TERRAZAS t ON m.terraza_id = t.id";
    
    private static final String DELETE_MESA = "DELETE FROM MESA";
    private static final String DELETE_SALA = "DELETE FROM SALA";
    private static final String DELETE_TERRAZA = "DELETE FROM TERRAZA";

    public void eliminaTodas() throws SQLException {
    	try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
            try (PreparedStatement ps = c.prepareStatement(DELETE_MESA)) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = c.prepareStatement(DELETE_SALA)) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = c.prepareStatement(DELETE_TERRAZA)) {
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al borrar todas las mesa: " + e.getMessage());
        }
    }


    @Override
    public Integer altaMesa(TMesa mesa) {
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            Integer salaId = null;
            Integer terrazaId = null;

            if (mesa instanceof TMesaSala) {
                TMesaSala mesaS = (TMesaSala) mesa;
                try (PreparedStatement ps = c.prepareStatement(INSERT_SALA, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setBoolean(1, mesaS.getReservada());
                    ps.setString(2, mesaS.getPrivacidad());
                    ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) salaId = rs.getInt(1);
                }
            } else if (mesa instanceof TMesaTerraza) {
                TMesaTerraza mesaT = (TMesaTerraza) mesa;
                try (PreparedStatement ps = c.prepareStatement(INSERT_TERRAZA, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setBoolean(1, mesaT.getCubierta());
                    ps.setDouble(2, mesaT.getSuplemento());
                    ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) terrazaId = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = c.prepareStatement(INSERT_MESA, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, mesa.getNumero());
                ps.setString(2, mesa.getUbicacion());
                ps.setInt(3, mesa.getCapacidad());
                ps.setBoolean(4, mesa.getActivo());
                if (salaId != null) {
                    ps.setInt(5, salaId);
                    ps.setNull(6, java.sql.Types.INTEGER);
                } else {
                    ps.setNull(5, java.sql.Types.INTEGER);
                    ps.setInt(6, terrazaId);
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) mesa.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("Error dando de alta mesa: " + e.getMessage());
        }

        return mesa.getId();
    }

    @Override
    public void bajaMesa(Integer id) {
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
            try (PreparedStatement ps = c.prepareStatement(DESACTIVAR_MESA)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al dar de baja la mesa: " + e.getMessage());
        }
    }

    @Override
    public TMesa mostrarMesa(Integer id) {
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
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
                        } else if (salaId != 0){
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
    public void modificarMesa(TMesa mesa) {
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
                TMesaSala mesaS = (TMesaSala) mesa;
                try (PreparedStatement ps = c.prepareStatement(UPDATE_SALA)) {
                    ps.setBoolean(1, mesaS.getReservada());
                    ps.setString(2, mesaS.getPrivacidad());
                    ps.setInt(3, mesaS.getId());
                    ps.executeUpdate();
                }
            } else if (mesa instanceof TMesaTerraza) {
                TMesaTerraza mesaT = (TMesaTerraza) mesa;
                try (PreparedStatement ps = c.prepareStatement(UPDATE_TERRAZA)) {
                    ps.setBoolean(1, mesaT.getCubierta());
                    ps.setDouble(2, mesaT.getSuplemento());
                    ps.setInt(3, mesaT.getId());
                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("Error actualizando mesa: " + e.getMessage());
        }
    }
}
