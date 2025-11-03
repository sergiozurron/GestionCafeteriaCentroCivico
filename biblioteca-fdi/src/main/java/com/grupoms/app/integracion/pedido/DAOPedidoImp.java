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
    public Integer confirmarPedido(TPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmarPedido'");
    }

    @Override
    public TPedido mostrarPedido(Integer id) {
        TPedido pedido = null;
        try{
            TransactionManager tm = TransactionManager.getInstance();
            Transaction t = tm.getTransaccion();
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
			Transaction t = TransactionManager.getInstance().getTransaccion();
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificarPedido'");
    }

    @Override
    public Integer devolverPedido(Integer id) {
        int exito = -1;
		try {
			Transaction t = TransactionManager.getInstance().getTransaccion();
			Connection c = (Connection) t.getResource();
			Statement s = c.createStatement();
			exito = s.executeUpdate("UPDATE pedido SET activo = 0 WHERE id = " + id + ";");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}
}