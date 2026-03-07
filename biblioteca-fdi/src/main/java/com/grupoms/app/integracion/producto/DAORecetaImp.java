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

    @Override
    public Integer vincular(Integer idProducto, Integer idIngrediente) {

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            String sql = "INSERT INTO entradas_recetas (producto_id, ingrediente_id, activo) VALUES (?, ?, ?)";

            try (PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, idProducto);
                ps.setInt(2, idIngrediente);
                ps.setBoolean(3, true);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    @Override
    public Integer desvincular(Integer idProducto, Integer idIngrediente) {

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            // 1. Comprobar si existe la relación y bloquearla
            String selectSQL =
                "SELECT id FROM entradas_recetas " +
                "WHERE producto_id = ? AND ingrediente_id = ? AND activo = TRUE FOR UPDATE";

            try (PreparedStatement ps = c.prepareStatement(selectSQL)) {

                ps.setInt(1, idProducto);
                ps.setInt(2, idIngrediente);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        return -1; // No existe o ya estaba desactivado
                    }

                    int idEntrada = rs.getInt("id");

                    // 2. Borrado lógico
                    String updateSQL =
                        "UPDATE entradas_recetas SET activo = FALSE WHERE id = ?";

                    try (PreparedStatement ps2 = c.prepareStatement(updateSQL)) {
                        ps2.setInt(1, idEntrada);

                        int filas = ps2.executeUpdate();
                        return filas > 0 ? 1 : -1;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -99;
        }
    }



    @Override
    public List<TIngrediente> listarIngredientesProducto(Integer idProducto) {

        List<TIngrediente> lista = new ArrayList<>();

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            String sql =
                "SELECT i.id, i.nombre, i.precio, i.activo, i.proveedor_id " +
                "FROM ingredientes i " +
                "JOIN entradas_recetas er ON i.id = er.ingrediente_id " +
                "WHERE er.producto_id = ?";

            try (PreparedStatement ps = c.prepareStatement(sql)) {

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
            e.printStackTrace();
        }

        return lista;
    }


    @Override
    public TEntradaReceta mostrarLineaReceta(Integer idProducto, Integer idIngrediente) {

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            String sql =
                "SELECT id, producto_id, ingrediente_id " +
                "FROM entradas_recetas " +
                "WHERE producto_id = ? AND ingrediente_id = ?";

            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setInt(1, idProducto);
                ps.setInt(2, idIngrediente);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        TEntradaReceta er = new TEntradaReceta();
                        er.setProductoID(rs.getInt("producto_id"));
                        er.setIngredienteID(rs.getInt("ingrediente_id"));
                        return er;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; 
    }


}
