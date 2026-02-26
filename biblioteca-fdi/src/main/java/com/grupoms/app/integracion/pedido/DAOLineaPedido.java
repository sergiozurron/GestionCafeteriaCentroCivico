package com.grupoms.app.integracion.pedido;

import java.util.List;

import com.grupoms.app.negocio.pedido.TLineaPedido;

public interface DAOLineaPedido {
	
	Integer altaLineaPedido(TLineaPedido lineaPedido);
	
	Integer bajaLineaPedido(Integer idPed, Integer idPr);
	
	List<TLineaPedido> mostrarLineasPorPedido(Integer id);
}
