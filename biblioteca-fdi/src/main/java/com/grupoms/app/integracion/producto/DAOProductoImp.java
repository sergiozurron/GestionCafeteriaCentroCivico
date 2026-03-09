package com.grupoms.app.integracion.producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.DBConfig;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.producto.TBebida;
import com.grupoms.app.negocio.producto.TComida;
import com.grupoms.app.negocio.producto.TProducto;

public class DAOProductoImp implements DAOProducto {

	private static final String INSERT_PRODUCTO = "INSERT INTO productos(nombre, precio, stock, activo) VALUES (?, ?, ?, ?)";
	private static final String INSERT_COMIDA = "INSERT INTO comidas(id, tipo, calorias, tiempo_preparacion) VALUES (?, ?, ?, ?)";
	private static final String INSERT_BEBIDA = "INSERT INTO bebidas(id, tipo, tamaño) VALUES (?, ?, ?)";
	private static final String READ_BY_ID = "SELECT p.*, " + " c.tipo AS tipo_comida, c.calorias, c.tiempo_preparacion, " + " b.tipo AS tipo_bebida, b.tamaño " + "FROM productos p " + "LEFT JOIN comidas c ON p.id = c.id " + "LEFT JOIN bebidas b ON p.id = b.id " + "WHERE p.id = ?";
	private static final String UPDATE_PRODUCTO =
    "UPDATE productos SET nombre = ?, precio = ?, stock = ?, activo = ? WHERE id = ?";
	private static final String UPDATE_COMIDA = "UPDATE comidas SET calorias = ?, tiempo_preparacion = ? WHERE id = ?";
	private static final String UPDATE_BEBIDA = "UPDATE bebidas SET tamaño = ? WHERE id = ?";
	private static final String DESACTIVAR_PRODUCTO = "UPDATE productos SET activo = false WHERE id = ?";
	private static final String ALL =
    	"SELECT p.*, " +
    	"c.calorias, c.tiempo_preparacion, " +
    	"b.tamaño " +
    	"FROM productos p " +
    	"LEFT JOIN comidas c ON p.id = c.id " +
    	"LEFT JOIN bebidas b ON p.id = b.id";
	private static final String DELETE_PRODUCTO = "DELETE FROM productos";
	private static final String PRODUCTOS_POR_PROVEEDOR =
		    "SELECT DISTINCT p.*, " +
		    "c.calorias, c.tiempo_preparacion, " +
		    "b.tamaño " +
		    "FROM productos p " +
		    "LEFT JOIN comidas c ON p.id = c.id " +
		    "LEFT JOIN bebidas b ON p.id = b.id " +
		    "JOIN entradas_recetas er ON p.id = er.producto_id " +
		    "JOIN ingredientes i ON er.ingrediente_id = i.id " +
		    "WHERE i.proveedor_id = ?";

	@Override
public Integer altaProducto(TProducto producto) {

    Integer idGenerado = null;

    try {
        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();

        try (PreparedStatement ps = c.prepareStatement(
                INSERT_PRODUCTO,
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setBoolean(4, producto.getActivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    producto.setId(idGenerado);
                }
            }
        }
        if (producto instanceof TComida) {

            try (PreparedStatement ps = c.prepareStatement(INSERT_COMIDA)) {
                ps.setInt(1, idGenerado);
                ps.setString(2, "Comida");
                ps.setInt(3, ((TComida) producto).getCalorias());
                ps.setInt(4, ((TComida) producto).getTiempoPreparacion());
                ps.executeUpdate();
            }

        } else if (producto instanceof TBebida) {

            try (PreparedStatement ps = c.prepareStatement(INSERT_BEBIDA)) {
                ps.setInt(1, idGenerado);
                ps.setString(2, "Bebida");
                ps.setInt(3, ((TBebida) producto).getTamanho());
                ps.executeUpdate();
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException("Error dando de alta producto", e);
    }

    return idGenerado;
}

	@Override
	public Boolean bajaProducto(TProducto producto) {
		Boolean exito = false;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();

			try (PreparedStatement ps = c.prepareStatement(DESACTIVAR_PRODUCTO)) {
				ps.setInt(1, producto.getId());
				int rows = ps.executeUpdate();
				exito = rows > 0;
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error dando de baja producto: " + e.getMessage(), e);
		}
		return exito;
	}

	@Override
public Boolean modificarProducto(TProducto producto) {

    boolean exito = false;

    try {
        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();

        try (PreparedStatement ps = c.prepareStatement(UPDATE_PRODUCTO)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setBoolean(4, producto.getActivo());
            ps.setInt(5, producto.getId());

            ps.executeUpdate();
        }

        if (producto instanceof TComida) {

            try (PreparedStatement ps = c.prepareStatement(UPDATE_COMIDA)) {
                ps.setInt(1, ((TComida) producto).getCalorias());
                ps.setInt(2, ((TComida) producto).getTiempoPreparacion());
                ps.setInt(3, producto.getId());
                ps.executeUpdate();
            }

        } else if (producto instanceof TBebida) {

            try (PreparedStatement ps = c.prepareStatement(UPDATE_BEBIDA)) {
                ps.setInt(1, ((TBebida) producto).getTamanho());
                ps.setInt(2, producto.getId());
                ps.executeUpdate();
            }
        }

        exito = true;

    } catch (SQLException e) {
        throw new RuntimeException("Error modificando producto", e);
    }

    return exito;
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
						if (rs.getObject("calorias") != null) {

                        	TComida comida = new TComida();
                        	comida.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
                        	comida.setCalorias(rs.getInt("calorias"));
                        	producto = comida;

                    	} else if (rs.getObject("tamaño") != null) {

                        	TBebida bebida = new TBebida();
                        	bebida.setTamanho(rs.getInt("tamaño"));
                        	producto = bebida;
                    	}

                    	if (producto != null) {
                       		producto.setId(rs.getInt("id"));
                        	producto.setNombre(rs.getString("nombre"));
                        	producto.setPrecio(rs.getDouble("precio"));
                        	producto.setStock(rs.getInt("stock"));
                        	producto.setActivo(rs.getBoolean("activo"));
                    	}
					}
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error mostrando producto: ", e);
		}
		return producto;
	}

	@Override
	public List<TProducto> mostrarProductosPorProveedor(Integer idProveedor) {

	    List<TProducto> listaProductos = new ArrayList<>();

	    try {
	        Transaction t = TransactionManager.getInstance().getTransaction();
	        Connection c = (Connection) t.getResource();

	        try (PreparedStatement ps = c.prepareStatement(PRODUCTOS_POR_PROVEEDOR)) {

	            ps.setInt(1, idProveedor);

	            try (ResultSet rs = ps.executeQuery()) {

	                while (rs.next()) {

	                    TProducto producto = null;

	                    // Para detectar el tipo de producto
	                    if (rs.getObject("calorias") != null) {

	                        TComida comida = new TComida();
	                        comida.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
	                        comida.setCalorias(rs.getInt("calorias"));
	                        producto = comida;

	                    } else if (rs.getObject("tamaño") != null) {

	                        TBebida bebida = new TBebida();
	                        bebida.setTamanho(rs.getInt("tamaño"));
	                        producto = bebida;

	                    } else {
	                        continue; 
	                    }

	                    producto.setId(rs.getInt("id"));
	                    producto.setNombre(rs.getString("nombre"));
	                    producto.setPrecio(rs.getDouble("precio"));
	                    producto.setStock(rs.getInt("stock"));
	                    producto.setActivo(rs.getBoolean("activo"));

	                    listaProductos.add(producto);
	                }
	            }
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error mostrando productos por proveedor: ", e);
	    }

	    return listaProductos;
	}

	@Override
	public List<TProducto> mostrarListaProductos() {

	    List<TProducto> listaProductos = new ArrayList<>();

	    try {
	        Transaction t = TransactionManager.getInstance().getTransaction();
	        Connection c = (Connection) t.getResource();

	        try (PreparedStatement ps = c.prepareStatement(ALL);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {

	                TProducto producto = null;

	                if (rs.getObject("calorias") != null) {

	                    TComida comida = new TComida();
	                    comida.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
	                    comida.setCalorias(rs.getInt("calorias"));
	                    producto = comida;

	                } else if (rs.getObject("tamaño") != null) {

	                    TBebida bebida = new TBebida();
	                    bebida.setTamanho(rs.getInt("tamaño"));
	                    producto = bebida;

	                } else {
	                    continue;
	                }

	                producto.setId(rs.getInt("id"));
	                producto.setNombre(rs.getString("nombre"));
	                producto.setPrecio(rs.getDouble("precio"));
	                producto.setStock(rs.getInt("stock"));
	                producto.setActivo(rs.getBoolean("activo"));

	                listaProductos.add(producto);
	            }
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error mostrando lista de productos: ", e);
	    }

	    return listaProductos;
	}

	
	
}
