package com.grupoms.app.negocio.pedido;


import java.util.ArrayList;
import java.util.List;
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
					for(TLineaVenta lv : carrito.getLineasVenta()) {
						lv.setPedidoID(id);
						int r = daoLinea.altaLineaVenta(lv);
						if(r<0) {
							t.rollback();
							return -3;
						}
					}
				}else {
					t.rollback();
					return -1;
				}
				t.commit();
				return id;
			}else {
				t.rollback();
				return -2;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return -3;
		}
	}

	@Override
	public TPedidoLinea mostrarPedidoPorID(int id) {
		TPedidoLinea pedidoLinea = new TPedidoLinea();
		TPedido p = new TPedido();
		try {
			Transaction t = TransactionManager.getInstance().newTransaction();
			t.start();
			TPedido pedido = daoPedido.mostrarPedido(id);
			if(pedido !=null) {
				pedidoLinea.settPedido(pedido);
				List<TPedidoLinea> lineaPedido = daoLinea.mostrarLineaPedidoPorPedido(id);
				
				for(TPedidoLinea tlinea: lineaPedido) {
					pedidoLinea.incluirLineaVenta(tlinea);
				}
				t.commit();
			}else {
				p.setId(-2);
				pedidoLinea.settPedido(p);
				t.rollback();
			}
		}catch(Exception e) {
			e.printStackTrace();
			p.setId(-2);
			pedidoLinea.settPedido(p);
		}
		return pedidoLinea;
	}

	@Override
	public List<TPedido> listarPedidos() {
		List<TPedido> pedidos = new ArrayList<>();
		try {
			Transaction t = TransactionManager.getInstance().newTransaction();
			t.start();
			List<TPedido> pedidosB = daoPedido.listarPedidos();
			for(TPedido p: pedidosB) {
				pedidos.add(p);
			}
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return pedidos;
	}

	@Override
	public Integer modificarPedido(TPedido pedido) {
		int r = -1;
		try {
			Transaction t = TransactionManager.getInstance().newTransaction();
			t.start();
			TPedido pedidoB = daoPedido.mostrarPedido(pedido.getId());
			if(pedidoB!=null) {
				if(pedidoB.getActivo()) {
					pedido.setTotal(pedidoB.getTotal());
					pedido.setActivo(pedidoB.getActivo());
					r = daoPedido.modificarPedido(pedido);
					if(r<0) {
						t.rollback();
						return -1;
					}
				}else {
					t.rollback();
					return -1;
				}
			}else {
				t.rollback();
				return -1;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return r;
		}
		return r;
	}

	@Override
	public boolean devolverLinea(TLineaVenta linea) {
		// TODO Auto-generated method stub
		return false;
	}

}
