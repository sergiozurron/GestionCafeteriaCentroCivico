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
        TIngrediente ingr = null;
        try{
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t==null)
                throw new IllegalStateException("No hay transaccion activa");
            Connection c = (Connection) t.getResource();

            String sql = "SELECT id, nombre, precio, activo, proveedor_id FROM ingredientes WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ingr = new TIngrediente();
                    ingr.setID(rs.getInt("id"));
                    ingr.setNombre(rs.getString("nombre"));
                    ingr.setPrecio(rs.getDouble("precio"));
                    ingr.setIDProveedor(rs.getInt("proveedor_id"));
                }
            }
        }
    }catch(Exception e){
        e.printStackTrace();
    }
        return ingr;  
    }

    @Override
    public List<TIngrediente> mostrarListaIngredientes() throws Exception{
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
            System.err.println("Error mostrando la lista de ingredientes: "+e.getMessage());
        }
        return listaIngredientes;
    }


    @Override
    public List<TIngrediente> mostrarProveedorPorIngrediente(Integer idProveedor) {
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

                int rows = stmt.executeUpdate();
                exito = rows > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exito;
    }

    @Override
    public List<TIngrediente> listarIngredientesPorProducto(Integer idProducto) throws Exception {
        Transaction t = TransactionManager.getInstance().getTransaction();
        if(t == null) {
            throw new IllegalStateException("No hay transacción activa al mostrar los ingredientes");
        }
        List<TIngrediente> lista = new ArrayList<>();
        try (Connection c = (Connection) t.getResource()) {
            String sql = "SELECT i.id, i.nombre, i.precio, i.activo, i.proveedor_id " +
                    "FROM ingredientes i " +
                    "JOIN producto_ingrediente pi ON i.id = pi.id_ingrediente " +
                    "WHERE pi.id_producto = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1,idProducto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TIngrediente ing = new TIngrediente();
                ing.setID(rs.getInt("id"));
                ing.setNombre(rs.getString("nombre"));
                ing.setPrecio(rs.getDouble("precio"));
                ing.setActivo(rs.getBoolean("activo"));
                ing.setIDProveedor(rs.getInt("proveedor_id"));
                lista.add(ing);
            }

            rs.close();
            ps.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al listar ingredientes por producto: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        Transaction tx = getTransaction();
        if (tx == null) {
            return DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword());
        }
        return (Connection) tx.getResource();
    }

    private void closeConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            if (getTransaction() == null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Transaction getTransaction() {
        try {
            return TransactionManager.getInstance().getTransaction();
        } catch (IllegalStateException e) {
            return null;
        }
    }

}
