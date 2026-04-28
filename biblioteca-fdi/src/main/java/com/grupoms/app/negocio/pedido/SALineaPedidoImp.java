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

		if (linea == null)
			throw new IllegalArgumentException("La línea no puede ser nula");

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido daoPedido = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido();

			// 1. validar pedido
			if (linea.getPedidoId() == null || linea.getPedidoId() <= 0)
				throw new IllegalArgumentException("Pedido no válido");

			TPedido pedido = daoPedido.mostrarPedido(linea.getPedidoId());
			if (pedido == null || !pedido.getActivo())
				throw new IllegalArgumentException("Pedido no existe o inactivo");

			// 2. validar producto
			if (linea.getProductoId() == null || linea.getProductoId() <= 0)
				throw new IllegalArgumentException("Producto no válido");

			TProducto producto = daoProducto.mostrarProducto(linea.getProductoId());
			if (producto == null || !producto.getActivo())
				throw new IllegalArgumentException("Producto no existe o inactivo");

			// 3. validar cantidad
			if (linea.getCantidad() == null || linea.getCantidad() <= 0)
				throw new IllegalArgumentException("Cantidad no válida");

			// 4. control de stock
			if (producto.getStock() < linea.getCantidad())
				throw new IllegalArgumentException("Stock insuficiente");

			// 5. actualizar stock
			producto.setStock(producto.getStock() - linea.getCantidad());
			
			Boolean stockOk = daoProducto.modificarProducto(producto);
			if (!stockOk)
				throw new RuntimeException("No se pudo actualizar el stock");

			// 6. crear línea
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

			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Integer bajaLineaPedido(Integer idPedido, Integer idProducto) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido();

			if (idPedido == null || idProducto == null)
				throw new IllegalArgumentException("Datos inválidos");

			Integer resultado = daoLinea.bajaLineaPedido(idPedido, idProducto);

			if (resultado == null)
				throw new IllegalArgumentException("La línea no existe");

			t.commit();
			return resultado;

		} catch (Exception e) {

			try {
				t.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}

			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<TLineaPedido> mostrarLineasPorPedido(Integer idPedido) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			if (idPedido == null || idPedido <= 0)
				throw new IllegalArgumentException("Pedido no válido");

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

			throw new RuntimeException(e.getMessage(), e);
		}
	}
}