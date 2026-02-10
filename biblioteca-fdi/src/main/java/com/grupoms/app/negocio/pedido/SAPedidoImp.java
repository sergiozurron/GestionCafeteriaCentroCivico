package com.grupoms.app.negocio.pedido;


import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.pedido.*;
import com.grupoms.app.integracion.producto.DAOProducto;
import com.grupoms.app.integracion.producto.DAOProductoImp;
import com.grupoms.app.negocio.producto.TProducto;

public class SAPedidoImp implements SAPedido {

	private DAOPedido daoPedido = new DAOPedidoImp();
	private DAOLineaVenta daoLinea = new DAOLineaVentaImp();
	private DAOProducto daoProducto = new DAOProductoImp();

	@Override
	public Integer altaPedido(TPedido pedido) {
		Transaction t = null;
		Integer id = null;
		try {
			if(pedido ==null)throw new IllegalArgumentException("El pedido no puede ser nulo");
			t = TransactionManager.getInstance().newTransaction();
			t.start();
			pedido.setEstado("ABIERTO");
			pedido.setTotal(0.0);
			pedido.setActivo(true);
			pedido.setFecha(new java.sql.Date(System.currentTimeMillis()));
			
			id = daoPedido.altaPedido(pedido);
			t.commit();
		}catch(Exception e) {
			e.printStackTrace();
            if (t != null) t.rollback();
            throw new IllegalArgumentException("Error al crear el pedido", e);
		}
		return id;
	}

	@Override
	public Integer cerrarPedido(TCarrito carrito) {
		TransactionManager tm = TransactionManager.getInstance();
		try {
			Transaction t = tm.newTransaction();
			t.start();
			if(!carrito.getLineasVenta().isEmpty()) {
				double precio = 0;
				for(TLineaVenta lineaV : carrito.getLineasVenta()) {
					TProducto producto = daoProducto.mostrarProducto(lineaV.getProductoId());
					if(producto!=null) {
						if(producto.getActivo()) {
							if(lineaV.getCantidad()<=producto.getStock()) {
								producto.setStock(producto.getStock()-lineaV.getCantidad());
								daoProducto.modificarProducto(producto);
								double precio_linea = (lineaV.getCantidad()*producto.getPrecio());
								lineaV.setPrecioVenta(precio_linea);
								precio = precio+ precio_linea;
							}
							else {
								t.rollback();
								return -2;
							}
						}
						else {
							t.rollback();
							return -2;
						}
					}else {
						t.rollback();
						return -2;
					}
				}
				TPedido pedido = carrito.getPedido();
				pedido.setTotal(precio);
				int id = daoPedido.cerrarPedido(pedido);
				if(id>0) {
					for
				}
			}
		}
	}

	@Override
	public TPedidoLinea mostrarPedidoPorID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TPedido> listarPedidos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modificarPedido(TPedido pedido) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean devolverLinea(TLineaVenta linea) {
		// TODO Auto-generated method stub
		return false;
	}

}
