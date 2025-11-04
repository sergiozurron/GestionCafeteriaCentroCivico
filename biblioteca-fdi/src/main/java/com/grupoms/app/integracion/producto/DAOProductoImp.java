package com.grupoms.app.integracion.producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.producto.TProducto;
import java.sql.Statement;

public class DAOProductoImp implements DAOProducto {
	private static final String READ_BY_ID = "SELECT * FROM PRODUCTOS WHERE id = ?";
	private static final String READ_BY_NAME = "SELECT * FROM PRODUCTOS WHERE nombre = ?";
	private static final String ALL = "SELECT * FROM PRODUCTOS";

    @Override
	public Integer altaProducto(TProducto producto) {
		Integer idGenerado = null;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
			String INSERT = "INSERT INTO PRODUCTOS(nombre, precio, stock, activo) VALUES (?, ?, ?, ?)";
			try (PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

				ps.setString(1, producto.getNombre());
				ps.setDouble(2, producto.getPrecio());
           		ps.setInt(3, producto.getStock());
				ps.setBoolean(4, producto.getActivo());

				ps.executeUpdate();

				// Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
			}

		} catch (SQLException e) {
	        System.err.println("Error dando de alta producto: " + e.getMessage());
		}
		return idGenerado;
	}

    public Integer bajaProducto(Integer id) {
		
	}

	public Integer modificarProducto(TProducto producto) {
		int exito = -1;
        try{
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
			String UPDATE = "UPDATE PRODUCTOS SET nombre = ?, precio = ?, stock = ?, activo = ? WHERE id = ?";
			try (PreparedStatement ps = c.prepareStatement(UPDATE)) {

				ps.setString(1, producto.getNombre());
				ps.setDouble(2, producto.getPrecio());
           		ps.setInt(3, producto.getStock());
				ps.setBoolean(4, producto.getActivo());
				ps.setInt(5, producto.getId());

				exito = ps.executeUpdate(); // numero de filas afectadas
			}
		} catch (SQLException e) {
	        System.err.println("Error modificando producto: " + e.getMessage());
		}
		return exito != -1 ? producto.getId() : exito;
	}

	public TProducto mostrarProducto(Integer id) {

	}

	public List<TProducto> mostrarListaProductos() {
		
	}
}