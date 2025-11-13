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

    private static final String INSERT_PRODUCTO = 
        "INSERT INTO PRODUCTOS(nombre, precio, stock, activo, tipo, tamanho, tiempo_preparacion, calorias) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String READ_BY_ID = "SELECT * FROM PRODUCTOS WHERE id = ?";
    private static final String UPDATE_PRODUCTO = 
        "UPDATE PRODUCTOS SET nombre = ?, precio = ?, stock = ?, activo = ?, tamanho = ?, tiempo_preparacion = ?, calorias = ? WHERE id = ?";
    private static final String DESACTIVAR_PRODUCTO = "UPDATE PRODUCTOS SET activo = false WHERE id = ?";
    private static final String ALL = "SELECT * FROM PRODUCTOS";
    private static final String DELETE_PRODUCTO = "DELETE FROM PRODUCTOS";

    @Override
    public Integer altaProducto(TProducto producto) {
        Integer idGenerado = null;
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(INSERT_PRODUCTO, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, producto.getNombre());
                ps.setDouble(2, producto.getPrecio());
                ps.setInt(3, producto.getStock());
                ps.setBoolean(4, producto.getActivo());

                // Tipo de producto y campos específicos
                if (producto instanceof TBebida) {
                    ps.setString(5, "Bebida");
                    ps.setInt(6, ((TBebida) producto).getTamanho());
                    ps.setNull(7, Types.INTEGER);
                    ps.setNull(8, Types.INTEGER);
                } else if (producto instanceof TComida) {
                    ps.setString(5, "Comida");
                    ps.setNull(6, Types.INTEGER);
                    ps.setInt(7, ((TComida) producto).getTiempoPreparacion());
                    ps.setInt(8, ((TComida) producto).getCalorias());
                }

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        producto.setId(idGenerado);
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
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(DESACTIVAR_PRODUCTO)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                return rows > 0 ? id : -1;
            }

        } catch (SQLException e) {
            System.err.println("Error dando de baja producto: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public Integer modificarProducto(TProducto producto) {
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(UPDATE_PRODUCTO)) {
                ps.setString(1, producto.getNombre());
                ps.setDouble(2, producto.getPrecio());
                ps.setInt(3, producto.getStock());
                ps.setBoolean(4, producto.getActivo());

                if (producto instanceof TBebida) {
                    ps.setInt(5, ((TBebida) producto).getTamanho());
                    ps.setNull(6, Types.INTEGER);
                    ps.setNull(7, Types.INTEGER);
                } else if (producto instanceof TComida) {
                    ps.setNull(5, Types.INTEGER);
                    ps.setInt(6, ((TComida) producto).getTiempoPreparacion());
                    ps.setInt(7, ((TComida) producto).getCalorias());
                }

                ps.setInt(8, producto.getId());

                int rows = ps.executeUpdate();
                return rows > 0 ? producto.getId() : -1;
            }

        } catch (SQLException e) {
            System.err.println("Error modificando producto: " + e.getMessage());
        }
        return -1;
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
                        String tipo = rs.getString("tipo");
                        if ("Bebida".equals(tipo)) {
                            TBebida bebida = new TBebida();
                            bebida.setTamanho(rs.getInt("tamanho"));
                            producto = bebida;
                        } else if ("Comida".equals(tipo)) {
                            TComida comida = new TComida();
                            comida.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
                            comida.setCalorias(rs.getInt("calorias"));
                            producto = comida;
                        }

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

            try (PreparedStatement ps = c.prepareStatement(ALL);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    TProducto producto;
                    String tipo = rs.getString("tipo");

                    if ("Bebida".equals(tipo)) {
                        TBebida bebida = new TBebida();
                        bebida.setTamanho(rs.getInt("tamanho"));
                        producto = bebida;
                    } else {
                        TComida comida = new TComida();
                        comida.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
                        comida.setCalorias(rs.getInt("calorias"));
                        producto = comida;
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
            System.err.println("Error mostrando lista de productos: " + e.getMessage());
        }

        return listaProductos;
    }

    @Override
    public void eliminaTodas() {
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(DELETE_PRODUCTO)) {
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error eliminando todos los productos: " + e.getMessage());
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
