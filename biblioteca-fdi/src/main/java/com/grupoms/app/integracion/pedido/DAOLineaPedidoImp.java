package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TLineaPedido;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOLineaPedidoImp implements DAOLineaPedido {

	@Override
	public Integer altaLineaPedido(TLineaPedido lineaPedido) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer bajaLineaPedido(Integer idPed, Integer idPr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TLineaPedido> mostrarLineasPorPedido(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
