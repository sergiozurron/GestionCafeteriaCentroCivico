package com.grupoms.app.negocio.pedido;

import java.util.List;

public interface SALineaPedido {

	Integer altaLineaPedido (TLineaPedido lineaPedido);
	
	Integer bajaLineaPedido (Integer idPed, Integer idPr);
	
	List<TLineaPedido> mostrarLineasPorPedido(Integer id);
}
