package com.grupoms.app.integracion.producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.producto.TProducto;

public class DAOProductoImp implements DAOProducto {
	private static final String READ_BY_ID = "SELECT * FROM PRODUCTOS WHERE id = ?";
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

    @Override
    public Integer bajaProducto(Integer id) {
		int exito = -1;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
			String UPDATE = "UPDATE PRODUCTOS SET activo = false WHERE id = ?";
			try (PreparedStatement ps = c.prepareStatement(UPDATE)) {
				ps.setInt(1, id);
				exito = ps.executeUpdate(); // numero de filas afectadas
			}
		} catch (SQLException e) {
	        System.err.println("Error dando de baja producto: " + e.getMessage());
		}
		return exito > 0 ? id : -1;
	}

	@Override
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

	@Override
	public TProducto mostrarProducto(Integer id) {
		TProducto producto = null;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
			try (PreparedStatement ps = c.prepareStatement(READ_BY_ID)) {
				ps.setInt(1, id);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						producto = new TProducto();
						producto.setId(rs.getInt("id"));
						producto.setNombre(rs.getString("nombre"));
						producto.setPrecio(rs.getDouble("precio"));
						producto.setStock(rs.getInt("stock"));
						producto.setActivo(rs.getBoolean("activo"));
					}
				}
			}
		} catch (SQLException e) {
	        System.err.println("Error mostrando producto: " + e.getMessage());
		}
		return producto;
	}

	@Override
	public List<TProducto> mostrarListaProductos() {
		List<TProducto> listaProductos = new ArrayList<>();
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
			try (PreparedStatement ps = c.prepareStatement(ALL)) {
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					TProducto producto = new TProducto();
					producto.setId(rs.getInt("id"));
					producto.setNombre(rs.getString("nombre"));
					producto.setPrecio(rs.getDouble("precio"));
					producto.setStock(rs.getInt("stock"));
					producto.setActivo(rs.getBoolean("activo"));
					listaProductos.add(producto);
				}
			}
		} catch (SQLException e) {
	        System.err.println("Error mostrando lista de productos: " + e.getMessage());
		}
		return listaProductos;
	}
}