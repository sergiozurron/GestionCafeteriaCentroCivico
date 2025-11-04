package com.grupoms.app.integracion.ingrediente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

            // Insert en la tabla pedido
            String sql = "INSERT INTO ingrediente (nombre,precio,activo,proveedor_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(2, ingrediente.getNombre());
                ps.setBoolean(3, ingrediente.getActivo());
                ps.setDouble(4, ingrediente.getPrecio());
                ps.setInt(5, ingrediente.getIDProveedor());
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarIngrediente'");
    }

    @Override
    public Set<TIngrediente> mostrarListaIngredientes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarListaIngredientes'");
    }

    @Override
    public Set<TIngrediente> mostrarIngredientePorProducto(Integer idProducto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarIngredientePorProducto'");
    }

    @Override
    public Set<TIngrediente> mostrarProveedorPorIngrediente(Integer idProveedor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarProveedorPorIngrediente'");
    }

    @Override
    public Boolean modificarIngrediente(TIngrediente tingrediente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificarIngrediente'");
    }

    @Override
    public Boolean bajaIngrediente(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bajaIngrediente'");
    }


    
}
