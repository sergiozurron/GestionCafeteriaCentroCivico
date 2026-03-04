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

public class DAORecetaImp implements DAOReceta {

    @Override
    public Integer vincular(Integer idProducto, Integer idIngrediente) {

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            String sql = "INSERT INTO entradas_recetas (producto_id, ingrediente_id) VALUES (?, ?)";

            try (PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, idProducto);
                ps.setInt(2, idIngrediente);

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

            String sql = "DELETE FROM entradas_recetas WHERE producto_id = ? AND ingrediente_id = ?";

            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setInt(1, idProducto);
                ps.setInt(2, idIngrediente);

                int filas = ps.executeUpdate();
                return filas > 0 ? 1 : -1; // -1 si no existía
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


}
