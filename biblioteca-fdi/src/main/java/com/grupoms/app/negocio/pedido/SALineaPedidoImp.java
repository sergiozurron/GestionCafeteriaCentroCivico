package com.grupoms.app.negocio.pedido;

import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.pedido.DAOLineaPedido;
import com.grupoms.app.integracion.pedido.DAOPedido;
import com.grupoms.app.integracion.producto.DAOProducto;
import com.grupoms.app.negocio.producto.TProducto;

public class SALineaPedidoImp implements SALineaPedido {

	@Override
	public Integer altaLineaPedido(TLineaPedido linea) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido daoPedido = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido();

			// 1. validar pedido
			TPedido pedido = daoPedido.mostrarPedido(linea.getPedidoId());
			if (pedido == null || !pedido.getActivo()) {
				throw new IllegalArgumentException("Pedido no válido o inactivo");
			}

			// 2. validar producto
			TProducto producto = daoProducto.mostrarProducto(linea.getProductoId());
			if (producto == null || !producto.getActivo()) {
				throw new IllegalArgumentException("Producto no válido o inactivo");
			}

			// 3. validar stock
			if (producto.getStock() < linea.getCantidad()) {
				throw new IllegalArgumentException("Stock insuficiente");
			}

			// 4. actualizar stock
			producto.setStock(producto.getStock() - linea.getCantidad());
			daoProducto.modificarProducto(producto);

			// 5. crear línea (SIEMPRE activa)
			linea.setActivo(true);

			Integer id = daoLinea.altaLineaPedido(linea);

			double totalActual = pedido.getTotal() != null ? pedido.getTotal() : 0.0;
			double precio = producto.getPrecio() != null ? producto.getPrecio() : 0.0;
			double subtotal = precio * linea.getCantidad();
			pedido.setTotal(totalActual + subtotal);

			Boolean actualizado = daoPedido.modificarPedido(pedido);
			if (!actualizado) {
				throw new RuntimeException("No se pudo actualizar el total del pedido");
			}

			t.commit();
			return id;

		} catch (Exception e) {

			try {
				t.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}

			throw new RuntimeException("Error en altaLineaPedido", e);
		}
	}

	@Override
	public Integer bajaLineaPedido(Integer idPedido, Integer idProducto) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido();

			Integer resultado = daoLinea.bajaLineaPedido(idPedido, idProducto);

			if (resultado == null) {
				throw new IllegalArgumentException("La línea no existe");
			}

			t.commit();
			return resultado;

		} catch (Exception e) {

			try {
				t.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}

			throw new RuntimeException("Error en bajaLineaPedido", e);
		}
	}

	@Override
	public List<TLineaPedido> mostrarLineasPorPedido(Integer idPedido) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido();

			List<TLineaPedido> lista = daoLinea.mostrarLineasPorPedido(idPedido);

			t.commit();
			return lista;

		} catch (Exception e) {

			try {
				t.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}

			throw new RuntimeException("Error mostrando líneas del pedido", e);
		}
	}
}