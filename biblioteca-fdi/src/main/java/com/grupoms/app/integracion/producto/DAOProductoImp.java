package com.grupoms.app.integracion.producto;

import java.util.List;

import com.grupoms.app.negocio.producto.TProducto;

public class DAOProductoImp implements DAOProducto {
    private static final String INSERT = "INSERT INTO PRODUCTOS(nombre, precio, stock, activo) VALUES (?, ?, ?, ?)";
	private static final String READ_BY_ID = "SELECT * FROM PRODUCTOS WHERE id = ?";
	private static final String READ_BY_NAME = "SELECT * FROM PRODUCTOS WHERE nombre = ?";
	private static final String UPDATE = "UPDATE PRODUCTOS SET nombre = ?, precio = ?, stock = ?, activo = ? WHERE id = ?";
	private static final String ALL = "SELECT * FROM PRODUCTOS";

    @Override
	public Integer altaProducto(TProducto producto) {
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, producto.getNombre());
			stmt.setDouble(2, producto.getPrecio());
            stmt.setInteger(3, producto.getStock());
			stmt.setBoolean(4, producto.getActivo());

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			producto.setId(rs.getInt(1));
            return producto.getId();

		} catch (SQLException e) {
	        System.err.println("Error dando de alta producto: " + e.getMessage());
		}
	}

    public Integer bajaProducto(Integer id) {

	}

	public Integer modificarProducto(TProducto producto) {

	}

	public TProducto mostrarProducto(Integer id) {

	}

	public List<TProducto> mostrarListaProductos() {
		
	}
}