package com.grupoms.app.integracion.producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.negocio.producto.TEntradaReceta;

public class DAORecetaImp implements DAOReceta {

    private static final String INSERT =
        "INSERT INTO entradas_recetas (producto_id, ingrediente_id, activo) VALUES (?, ?, ?)";

    private static final String SELECT_LINEA =
        "SELECT id, producto_id, ingrediente_id, activo " +
        "FROM entradas_recetas " +
        "WHERE producto_id = ? AND ingrediente_id = ? FOR UPDATE";

    private static final String UPDATE_DESVINCULAR =
        "UPDATE entradas_recetas SET activo = FALSE WHERE id = ?";

    private static final String UPDATE_REACTIVAR =
        "UPDATE entradas_recetas SET activo = TRUE WHERE id = ?";

    private static final String SELECT_INGREDIENTES_PRODUCTO =
        "SELECT i.id, i.nombre, i.precio, i.activo, i.proveedor_id " +
        "FROM ingredientes i " +
        "JOIN entradas_recetas er ON i.id = er.ingrediente_id " +
        "WHERE er.producto_id = ? FOR UPDATE";

    @Override
    public Integer vincular(Integer idProducto, Integer idIngrediente) {

        Integer idGenerado = null;

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(SELECT_LINEA)) {

                ps.setInt(1, idProducto);
                ps.setInt(2, idIngrediente);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int idExistente = rs.getInt("id");
                        boolean activo = rs.getBoolean("activo");

                        if (!activo) {
                            try (PreparedStatement psUpdate = c.prepareStatement(UPDATE_REACTIVAR)) {
                                psUpdate.setInt(1, idExistente);
                                psUpdate.executeUpdate();
                            }
                        }
                        return idExistente;
                    }
                }
            }

            try (PreparedStatement ps = c.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, idProducto);
                ps.setInt(2, idIngrediente);
                ps.setBoolean(3, true);

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error en DAOReceta.vincular", e);
        }

        return idGenerado;
    }


    @Override
    public Integer desvincular(Integer idProducto, Integer idIngrediente) {

        Integer filasActualizadas = 0;

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            Integer idEntrada = null;

            try (PreparedStatement ps = c.prepareStatement(SELECT_LINEA)) {

                ps.setInt(1, idProducto);
                ps.setInt(2, idIngrediente);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idEntrada = rs.getInt("id");
                    }
                }
            }

            if (idEntrada != null) {
                try (PreparedStatement ps = c.prepareStatement(UPDATE_DESVINCULAR)) {
                    ps.setInt(1, idEntrada);
                    filasActualizadas = ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error en DAOReceta.desvincular", e);
        }

        return filasActualizadas;
    }


    @Override
    public List<TIngrediente> listarIngredientesProducto(Integer idProducto) {

        List<TIngrediente> lista = new ArrayList<>();

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(SELECT_INGREDIENTES_PRODUCTO)) {

                ps.setInt(1, idProducto);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        TIngrediente ing = new TIngrediente();

                        ing.setID(rs.getInt("id"));
                        ing.setNombre(rs.getString("nombre"));
                        ing.setPrecio(rs.getDouble("precio"));
                        ing.setActivo(rs.getBoolean("activo"));
                        ing.setIDProveedor(rs.getInt("proveedor_id"));

                        lista.add(ing);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error en DAOReceta.listarIngredientesProducto", e);
        }

        return lista;
    }


    @Override
    public TEntradaReceta mostrarLineaReceta(Integer idProducto, Integer idIngrediente) {

        TEntradaReceta er = null;

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(SELECT_LINEA)) {

                ps.setInt(1, idProducto);
                ps.setInt(2, idIngrediente);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        er = new TEntradaReceta();
                        er.setProductoID(rs.getInt("producto_id"));
                        er.setIngredienteID(rs.getInt("ingrediente_id"));
                        er.setActivo(rs.getBoolean("activo"));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error en DAOReceta.mostrarLineaReceta", e);
        }

        return er;
    }
}