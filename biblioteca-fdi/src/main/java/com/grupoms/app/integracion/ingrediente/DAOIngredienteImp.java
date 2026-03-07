package com.grupoms.app.integracion.ingrediente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.DBConfig;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.negocio.producto.TEntradaReceta;

public class DAOIngredienteImp implements DAOIngrediente {

	@Override
	public Integer crearIngrediente(TIngrediente ingrediente) {
		 Integer idGenerado = null;
	        try {
	            Transaction t = TransactionManager.getInstance().getTransaction();
	            Connection c = (Connection) t.getResource();

	            String sql = "INSERT INTO ingredientes(nombre, precio, activo, proveedor_id) VALUES (?, ?, ?, ?)";
	            try (PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
	            e.printStackTrace();
	            throw new RuntimeException("Error creando ingrediente", e);
	        }
	        return idGenerado;
	}

	@Override
	public TIngrediente mostrarIngrediente(Integer id) {
		TIngrediente ing = null;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sql = "SELECT id, nombre, precio, activo, proveedor_id FROM ingredientes WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
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
            e.printStackTrace();
            throw new RuntimeException("Error mostrando ingrediente", e);
        }
        return ing;
	}

	@Override
	public List<TIngrediente> mostrarListaIngredientes(){
		List<TIngrediente> listaIngredientes = new ArrayList<>();
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			String sql = "SELECT id, nombre, precio, activo, proveedor_id FROM ingredientes";
			try (PreparedStatement ps = c.prepareStatement(sql)) {
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					TIngrediente ingrediente = new TIngrediente();
					ingrediente.setID(rs.getInt("id"));
					ingrediente.setNombre(rs.getString("nombre"));
					ingrediente.setPrecio(rs.getDouble("precio"));
					ingrediente.setActivo(rs.getBoolean("activo"));
					ingrediente.setIDProveedor(rs.getInt("proveedor_id"));
					listaIngredientes.add(ingrediente);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error mostrando la lista de ingredientes",e);
		}
		return listaIngredientes;
	}

	@Override
	public List<TIngrediente> mostrarIngredientesProveedor(Integer idProveedor) {
		List<TIngrediente> listaIngredientes = new ArrayList<>();
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			String sql = "SELECT id, nombre, precio, activo, proveedor_id FROM ingredientes WHERE proveedor_id = ?";
			try (PreparedStatement ps = c.prepareStatement(sql)) {
				ps.setInt(1, idProveedor);

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						TIngrediente ingrediente = new TIngrediente();
						ingrediente.setID(rs.getInt("id"));
						ingrediente.setNombre(rs.getString("nombre"));
						ingrediente.setPrecio(rs.getDouble("precio"));
						ingrediente.setActivo(rs.getBoolean("activo"));
						ingrediente.setIDProveedor(rs.getInt("proveedor_id"));
						if(ingrediente.getActivo())listaIngredientes.add(ingrediente);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error mostrando la lista de proveedores por ingrediente",e);
		}
		return listaIngredientes;
	}

	@Override
	public Boolean modificarIngrediente(TIngrediente ing) {
	    try {
	        Transaction t = TransactionManager.getInstance().getTransaction();
	        Connection c = (Connection) t.getResource();

	        // 1. BLOQUEO
	        String lock = "SELECT id FROM ingredientes WHERE id = ? FOR UPDATE";
	        try (PreparedStatement ps = c.prepareStatement(lock)) {
	            ps.setInt(1, ing.getID());
	            ps.executeQuery();   // SELECT → executeQuery()
	        }

	        // 2. UPDATE REAL
	        String sql = "UPDATE ingredientes SET nombre=?, precio=?, proveedor_id=? WHERE id=?";
	        try (PreparedStatement ps = c.prepareStatement(sql)) {
	            ps.setString(1, ing.getNombre());
	            ps.setDouble(2, ing.getPrecio());
	            ps.setInt(3, ing.getIDProveedor());
	            ps.setInt(4, ing.getID());
	            return ps.executeUpdate() > 0;
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error modificando ingrediente", e);
	    }
	}


	@Override
	public Boolean bajaIngrediente(TIngrediente ing) {
	    try {
	        Transaction t = TransactionManager.getInstance().getTransaction();
	        Connection c = (Connection) t.getResource();

	        // 1. BLOQUEO
	        String lock = "SELECT id FROM ingredientes WHERE id = ? FOR UPDATE";
	        try (PreparedStatement ps = c.prepareStatement(lock)) {
	            ps.setInt(1, ing.getID());
	            ps.executeQuery();
	        }

	        // 2. UPDATE REAL
	        String sql = "UPDATE ingredientes SET activo=? WHERE id=?";
	        try (PreparedStatement ps = c.prepareStatement(sql)) {
	            ps.setBoolean(1, ing.getActivo());
	            ps.setInt(2, ing.getID());
	            return ps.executeUpdate() > 0;
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error dando de baja ingrediente", e);
	    }
	}


	@Override
	public List<TEntradaReceta> listarIngredientesPorProducto(Integer idProducto) {

	    List<TEntradaReceta> lista = new ArrayList<>();

	    try {
	        Transaction t = TransactionManager.getInstance().getTransaction();
	        Connection c = (Connection) t.getResource();

	        String sql =
	            "SELECT id, producto_id, ingrediente_id, activo " +
	            "FROM entradas_recetas " +
	            "WHERE producto_id = ?";

	        try (PreparedStatement ps = c.prepareStatement(sql)) {

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
	        e.printStackTrace();
	        throw new RuntimeException("Error listando ingredientes por producto", e);
	    }

	    return lista;
	}


}
