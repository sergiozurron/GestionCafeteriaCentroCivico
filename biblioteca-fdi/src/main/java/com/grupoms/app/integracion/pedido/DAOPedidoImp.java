package com.grupoms.app.integracion.pedido;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.pedido.TPedido;

public class DAOPedidoImp implements DAOPedido{

    @Override
    public Integer confirmarPedido(TPedido tpedido) {
        Integer resultado = -1;
        Transaction t = null;

        try {
            t = TransactionManager.getInstance().getTransaction();
            t.start();
            Connection c = (Connection) t.getResource();

            // Bloqueamos el pedido
            try (PreparedStatement statement = c.prepareStatement(
                    "SELECT estado FROM pedido WHERE id = ? FOR UPDATE")) {

                statement.setInt(1, tpedido.getId());

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        String estadoActual = rs.getString("estado");

                        // Solo se puede confirmar si está abierto (pedido inicial)
                        if (!"ABIERTO".equalsIgnoreCase(estadoActual)) {
                            System.out.println("No se puede confirmar el pedido, ya está en preparación o terminado.");
                            t.rollback();
                        } else {
                            // Actualizamos el estado a EN_PREPARACION
                            try (PreparedStatement stUpdate = c.prepareStatement(
                                    "UPDATE pedido SET estado = 'EN_PREPARACION', total_factura = ? WHERE id = ?")) {

                                stUpdate.setDouble(1, tpedido.getTotal()); // Total calculado
                                stUpdate.setInt(2, tpedido.getId());

                                int filas = stUpdate.executeUpdate();
                                if (filas > 0) {
                                    System.out.println("Pedido confirmado y en preparación correctamente.");
                                    resultado = tpedido.getId();
                                    t.commit();
                                } else {
                                    System.out.println("No se pudo confirmar el pedido.");
                                    t.rollback();
                                }
                            }
                        }
                    } else {
                        System.out.println("Pedido no encontrado.");
                        t.rollback();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (t != null) t.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return resultado;
    }

    @Override
    public TPedido mostrarPedido(Integer id) {
        TPedido pedido = null;
        try{
            TransactionManager tm = TransactionManager.getInstance();
            Transaction t = tm.getTransaction();
            Connection c = (Connection) t.getResource();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM pedido WHERE id = ? FOR UPDATE");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
				pedido = new TPedido();
				pedido.setId(result.getInt(1));
				pedido.setTotal(result.getDouble(2));
				pedido.setFecha(new Date(result.getDate(3).getTime()));
				pedido.setActivo(result.getBoolean(4));
                pedido.setEstado(result.getString(5));
                pedido.setIdEmpleado(result.getInt(6));
                pedido.setIdMesa(result.getInt(7));
			}
			result.close();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public Set<TPedido> mostrarListaPedidos() {
        Set<TPedido> pedidos = new LinkedHashSet<TPedido>();
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement("SELECT * FROM factura FOR UPDATE");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				TPedido pedido = new TPedido();
				pedido.setId(result.getInt(1));
				pedido.setTotal(result.getDouble(2));
				pedido.setFecha(new Date(result.getDate(3).getTime()));
				pedido.setActivo(result.getBoolean(4));
                pedido.setEstado(result.getString(5));
                pedido.setIdEmpleado(result.getInt(6));
                pedido.setIdMesa(result.getInt(7));
				pedidos.add(pedido);
			}
			statement.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pedidos;
    }
       
    @Override
    public Integer modificarPedido(TPedido tpedido) {
        Integer exito = -1;

        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();

        String sql = "UPDATE pedido SET totalFactura = ?, estado = ?, activo = ? WHERE id = ?";
       

        return exito;
    }

    @Override
    public Integer devolverPedido(Integer id) {
        int exito = -1;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			Connection c = (Connection) t.getResource();
			Statement s = c.createStatement();
			exito = s.executeUpdate("UPDATE pedido SET activo = 0 WHERE id = " + id + ";");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

    @Override
    public Set<TPedido> mostrarListaPedidosEmpleado(Integer idEmpleado) {
        Set<TPedido> pedidos = new LinkedHashSet<>();
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
            PreparedStatement statement = c.prepareStatement(
                "SELECT * FROM pedido FOR UPDATE WHERE idEmpleado = ?"
            );
            statement.setInt(1, idEmpleado);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                TPedido pedido = new TPedido();
                pedido.setId(result.getInt("id"));
                pedido.setTotal(result.getDouble("totalFactura"));
                pedido.setFecha(new Date(result.getDate("fecha").getTime()));
                pedido.setActivo(result.getBoolean("activo"));
                pedido.setEstado(result.getString("estado"));
                pedido.setIdEmpleado(result.getInt("idEmpleado"));
                pedido.setIdMesa(result.getInt("idMesa"));
                pedidos.add(pedido);
            }
            statement.close();
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }
      
    @Override
    public Set<TPedido> mostrarListaPedidosMesa(Integer idMesa) {
        Set<TPedido> pedidos = new LinkedHashSet<>();
        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            Connection c = (Connection) t.getResource();
            PreparedStatement statement = c.prepareStatement(
                "SELECT * FROM pedido FOR UPDATE WHERE idMesa = ?"
            );
            statement.setInt(1, idMesa);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                TPedido pedido = new TPedido();
                pedido.setId(result.getInt("id"));
                pedido.setTotal(result.getDouble("totalFactura"));
                pedido.setFecha(new Date(result.getDate("fecha").getTime()));
                pedido.setActivo(result.getBoolean("activo"));
                pedido.setEstado(result.getString("estado"));
                pedido.setIdEmpleado(result.getInt("idEmpleado"));
                pedido.setIdMesa(result.getInt("idMesa"));
                pedidos.add(pedido);
            }
            statement.close();
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public Integer altaPedido(TPedido pedido)throws Exception {
        Integer idGenerado = -1;
        
        Transaction t = TransactionManager.getInstance().getTransaction();
        Connection c = (Connection) t.getResource();
        
        String sql = "INSERT INTO pedido (idEmpleado, idMesa, totalFactura, estado, activo, fecha) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pedido.getIdEmpleado());
            ps.setInt(2, pedido.getIdMesa());
            ps.setDouble(3, pedido.getTotal());
            ps.setString(4, pedido.getEstado());
            ps.setBoolean(5, pedido.getActivo());
            ps.setDate(6, pedido.getFecha());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
        }

        return idGenerado;
    }

}