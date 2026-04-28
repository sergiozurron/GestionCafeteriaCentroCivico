package com.grupoms.app.integracion.ingrediente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.negocio.producto.TEntradaReceta;

public class DAOIngredienteImp implements DAOIngrediente {

    private static final String INSERT = "INSERT INTO ingredientes(nombre, precio, activo, proveedor_id) VALUES (?, ?, ?, ?)";
    private static final String READ_BY_ID = "SELECT id, nombre, precio, activo, proveedor_id FROM ingredientes WHERE id = ? AND activo = True FOR UPDATE";
    private static final String READ_ALL = "SELECT id, nombre, precio, activo, proveedor_id FROM ingredientes WHERE activo = True FOR UPDATE";
    private static final String READ_BY_PROVEEDOR = 
    		"SELECT id, nombre, precio, activo, proveedor_id FROM ingredientes WHERE proveedor_id = ? AND activo = True FOR UPDATE";
    private static final String UPDATE = "UPDATE ingredientes SET nombre=?, precio=?, proveedor_id=? WHERE id=?";
    private static final String UPDATE_ACTIVO = "UPDATE ingredientes SET activo=? WHERE id=?";
    private static final String READ_ENTRADAS_RECETA = 
    		"SELECT id, producto_id, ingrediente_id, activo FROM entradas_recetas WHERE producto_id = ? AND activo = True FOR UPDATE";

    @Override
    public Integer crearIngrediente(TIngrediente ingrediente) {
        Integer idGenerado = null;

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, ingrediente.getNombre());
                ps.setDouble(2, ingrediente.getPrecio());
                ps.setBoolean(3, ingrediente.getActivo());
                ps.setInt(4, ingrediente.getIDProveedor());

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        ingrediente.setID(idGenerado);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return idGenerado;
    }

    @Override
    public TIngrediente mostrarIngrediente(Integer id) {
        TIngrediente ing = null;

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(READ_BY_ID)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ing = new TIngrediente();
                        ing.setID(rs.getInt("id"));
                        ing.setNombre(rs.getString("nombre"));
                        ing.setPrecio(rs.getDouble("precio"));
                        ing.setActivo(rs.getBoolean("activo"));
                        ing.setIDProveedor(rs.getInt("proveedor_id"));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error mostrando ingrediente " + id, e);
        }

        return ing;
    }

    @Override
    public List<TIngrediente> mostrarListaIngredientes() {
        List<TIngrediente> lista = new ArrayList<>();

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(READ_ALL);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    TIngrediente i = new TIngrediente();
                    i.setID(rs.getInt("id"));
                    i.setNombre(rs.getString("nombre"));
                    i.setPrecio(rs.getDouble("precio"));
                    i.setActivo(rs.getBoolean("activo"));
                    i.setIDProveedor(rs.getInt("proveedor_id"));
                    lista.add(i);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error mostrando lista de ingredientes", e);
        }

        return lista;
    }

    @Override
    public List<TIngrediente> mostrarIngredientesProveedor(Integer idProveedor) {
        List<TIngrediente> lista = new ArrayList<>();

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(READ_BY_PROVEEDOR)) {
                ps.setInt(1, idProveedor);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        TIngrediente i = new TIngrediente();
                        i.setID(rs.getInt("id"));
                        i.setNombre(rs.getString("nombre"));
                        i.setPrecio(rs.getDouble("precio"));
                        i.setActivo(rs.getBoolean("activo"));
                        i.setIDProveedor(rs.getInt("proveedor_id"));
                        lista.add(i);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error mostrando ingredientes del proveedor " + idProveedor, e);
        }

        return lista;
    }

    @Override
    public Boolean modificarIngrediente(TIngrediente ing) {

    	try {
    		Transaction t = TransactionManager.getInstance().getTransaction();
    		Connection c = (Connection) t.getResource();

    		try (PreparedStatement ps = c.prepareStatement(UPDATE)) {
    			ps.setString(1, ing.getNombre());
    			ps.setDouble(2, ing.getPrecio());
    			ps.setInt(3, ing.getIDProveedor());
    			ps.setInt(4, ing.getID());
    			int rows = ps.executeUpdate();

    			if (rows == 0) {
    				throw new RuntimeException("No se actualizó ningún registro");
    			}

    			return true;
    		}

    	} catch (SQLException e) {
    		throw new RuntimeException("Error modificando ingrediente " + ing.getID(), e);
    	}
    }

    @Override
    public Boolean bajaIngrediente(TIngrediente ing) {

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(READ_BY_ID)) {
                ps.setInt(1, ing.getID());
                ps.executeQuery();
            }

            try (PreparedStatement ps = c.prepareStatement(UPDATE_ACTIVO)) {
                ps.setBoolean(1, ing.getActivo());
                ps.setInt(2, ing.getID());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error dando de baja ingrediente " + ing.getID(), e);
        }
    }

    @Override
    public List<TEntradaReceta> listarIngredientesPorProducto(Integer idProducto) {

        List<TEntradaReceta> lista = new ArrayList<>();

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(READ_ENTRADAS_RECETA)) {

                ps.setInt(1, idProducto);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        TEntradaReceta er = new TEntradaReceta();
                        er.setProductoID(rs.getInt("producto_id"));
                        er.setIngredienteID(rs.getInt("ingrediente_id"));
                        er.setActivo(rs.getBoolean("activo"));

                        lista.add(er);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error listando ingredientes del producto " + idProducto, e);
        }

        return lista;
    }
}