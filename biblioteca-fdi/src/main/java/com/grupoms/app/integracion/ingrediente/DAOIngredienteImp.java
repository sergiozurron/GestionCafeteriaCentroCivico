package com.grupoms.app.integracion.ingrediente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.ingrediente.TIngrediente;

public class DAOIngredienteImp implements DAOIngrediente{

    @Override
    public Integer crearIngrediente(TIngrediente ingrediente) {
        Integer idGenerado = null;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            // Insert en la tabla ingredientes
            String sql = "INSERT INTO ingredientes (nombre, precio, activo, proveedor_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, ingrediente.getNombre());
                ps.setDouble(2, ingrediente.getPrecio());
                ps.setBoolean(3, ingrediente.getActivo());
                ps.setInt(4, ingrediente.getIDProveedor());
                ps.executeUpdate();

                // Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        ingrediente.setID(idGenerado);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return idGenerado;
    }

    @Override
    public TIngrediente mostrarIngrediente(Integer id) {
        TIngrediente ingrediente = null;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sql = "SELECT id, nombre, precio, activo, proveedor_id FROM ingredientes WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ingrediente = new TIngrediente();
                        ingrediente.setID(rs.getInt("id"));
                        ingrediente.setNombre(rs.getString("nombre"));
                        ingrediente.setPrecio(rs.getDouble("precio"));
                        ingrediente.setActivo(rs.getBoolean("activo"));
                        ingrediente.setIDProveedor(rs.getInt("proveedor_id"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingrediente;
    }

    @Override
    public Set<TIngrediente> mostrarListaIngredientes() {
        Set<TIngrediente> listaIngredientes = new HashSet<>();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaIngredientes;
    }

    @Override
    public Set<TIngrediente> mostrarIngredientePorProducto(Integer idProducto) {
        Set<TIngrediente> listaIngredientes = new HashSet<>();
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sql = "SELECT i.id, i.nombre, i.precio, i.activo, i.proveedor_id " +
                        "FROM ingredientes i " +
                        "INNER JOIN entradas_recetas er ON i.id = er.ingrediente_id " +
                        "WHERE er.producto_id = ?";

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, idProducto);

                try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaIngredientes;
    }

    @Override
    public Set<TIngrediente> mostrarProveedorPorIngrediente(Integer idProveedor) {
        Set<TIngrediente> listaIngredientes = new HashSet<>();
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
                        listaIngredientes.add(ingrediente);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaIngredientes;
    }

    @Override
    public Boolean modificarIngrediente(TIngrediente tingrediente) {
       Boolean exito = false;
       try{
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c= (Connection) t.getResource();

            String sql = "UPDATE ingredientes SET nombre = ?, precio = ?, proveedor_id = ? WHERE id =?"; 
            try (PreparedStatement st = c.prepareStatement(sql)){
                st.setString(1,tingrediente.getNombre());
                st.setDouble(2, tingrediente.getPrecio());
                st.setInt(3, tingrediente.getIDProveedor());
                st.setInt(4, tingrediente.getID());
                
                int  rows = st.executeUpdate();
                exito = rows > 0 ? true : false;
                st.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return exito;
    }

    @Override
    public Boolean bajaIngrediente(TIngrediente ingrediente) {
        Boolean exito = false;
         try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            String sql = "UPDATE ingredientes SET activo = ? WHERE id = ?";
            try (PreparedStatement stmt = c.prepareStatement(sql)) {
    
                stmt.setBoolean(1, ingrediente.getActivo());
                stmt.setInt(2, ingrediente.getID());

                stmt.executeUpdate();
                stmt.close();
                exito = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exito;
    }


    
}
