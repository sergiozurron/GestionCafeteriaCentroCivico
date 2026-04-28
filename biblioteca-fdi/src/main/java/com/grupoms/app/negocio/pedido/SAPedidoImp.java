package com.grupoms.app.negocio.pedido;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.*;
import com.grupoms.app.integracion.empleado.DAOEmpleado;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.mesa.DAOMesa;
import com.grupoms.app.integracion.pedido.*;
import com.grupoms.app.integracion.producto.*;
import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.producto.TProducto;

public class SAPedidoImp implements SAPedido {
	@Override
	public Boolean modificarPedido(TPedido pedido) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOEmpleado daoEmpleado = FactoriaDAO.getInstancia().creaDAOEmpleado();
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

			TPedido pedidoBD = dao.mostrarPedido(pedido.getId());

			if (pedidoBD == null || !pedidoBD.getActivo()) {
				throw new IllegalArgumentException("Pedido no válido");
			}
			pedido.setActivo(pedidoBD.getActivo());

			if (pedido.getIdEmpleado() != null) {
				TEmpleado emp = daoEmpleado.mostrarEmpleado(pedido.getIdEmpleado());
				if (emp == null || !emp.getActivo()) {
					throw new IllegalArgumentException("Empleado no válido");
				}
			}

			if (pedido.getIdMesa() != null) {
				TMesa mesa = daoMesa.mostrarMesa(pedido.getIdMesa());
				if (mesa == null || !mesa.getActivo()) {
					throw new IllegalArgumentException("Mesa no válida");
				}
			}

			if (pedido.getFecha() != null)
				pedidoBD.setFecha(pedido.getFecha());
			if (pedido.getEstado() != null)
				pedidoBD.setEstado(pedido.getEstado());
			if (pedido.getIdEmpleado() != null)
				pedidoBD.setIdEmpleado(pedido.getIdEmpleado());
			if (pedido.getIdMesa() != null)
				pedidoBD.setIdMesa(pedido.getIdMesa());
			if (pedido.getTotal() != null)
				pedidoBD.setTotal(pedido.getTotal());
			if (pedido.getActivo() != null)
				pedidoBD.setActivo(pedido.getActivo());

			Boolean ok = dao.modificarPedido(pedidoBD);

			if (!ok) {
				throw new RuntimeException("No se pudo modificar el pedido");
			}

			t.commit();
			return true;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException("Error modificando pedido", e);
		}
	}

	@Override
	public Integer altaPedido(TPedido pedido) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOEmpleado daoEmpleado = FactoriaDAO.getInstancia().creaDAOEmpleado();
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

			if (pedido.getIdEmpleado() == null || pedido.getIdEmpleado() <= 0)
			    throw new IllegalArgumentException("Empleado no válido");

			TEmpleado emp = daoEmpleado.mostrarEmpleado(pedido.getIdEmpleado());
			if (emp == null || !emp.getActivo())
			    throw new IllegalArgumentException("Empleado no existe o inactivo");

			if (pedido.getIdMesa() == null || pedido.getIdMesa() <= 0)
			    throw new IllegalArgumentException("Mesa no válida");

			TMesa mesa = daoMesa.mostrarMesa(pedido.getIdMesa());
			if (mesa == null || !mesa.getActivo())
			    throw new IllegalArgumentException("Mesa no existe o inactiva");

			pedido.setActivo(true);
			pedido.setEstado("ABIERTO");
			pedido.setTotal(0.0);
			pedido.setFecha(new java.sql.Date(System.currentTimeMillis()));

			Integer id = dao.altaPedido(pedido);

			if (id == null) {
				throw new RuntimeException("No se pudo crear el pedido");
			}

			t.commit();
			return id;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public TCarrito mostrarPedido(Integer idPedido) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido daoPedido = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido();
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

			TPedido pedido = daoPedido.mostrarPedido(idPedido);

			if (pedido == null) {
				throw new IllegalArgumentException("Pedido no existe");
			}

			List<TLineaPedido> lineas = daoLinea.mostrarLineasPorPedido(idPedido);
			TMesa mesa = daoMesa.mostrarMesa(pedido.getIdMesa());

			TCarrito carrito = new TCarrito();
			carrito.setPedido(pedido);
			carrito.setLineasPedido(lineas);
			carrito.setMesa(mesa);

			t.commit();
			return carrito;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TPedido> mostrarListaPedidos() {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOMesa daoM = FactoriaDAO.getInstancia().creaDAOMesa();
			DAOEmpleado daoE = FactoriaDAO.getInstancia().creaDAOEmpleado();

			List<TPedido> lista = dao.mostrarListaPedidos();

			List<TPedido> validos = new ArrayList<>();

			for (TPedido p : lista) {
				TMesa m = daoM.mostrarMesa(p.getIdMesa());
				TEmpleado e = daoE.mostrarEmpleado(p.getIdEmpleado());

				if (m != null && e != null && m.getActivo() && e.getActivo()) {
					validos.add(p);
				}
			}

			t.commit();
			return validos;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TPedido> mostrarPedidosPorMesa(Integer idMesa) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

			TMesa mesa = daoMesa.mostrarMesa(idMesa);

			if (mesa == null || !mesa.getActivo()) {
				throw new IllegalArgumentException("Mesa no válida");
			}

			List<TPedido> lista = dao.mostrarPedidosPorMesa(idMesa);

			t.commit();
			return lista;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();

			List<TPedido> lista = dao.mostrarPedidosPorEmpleado(idEmpleado);

			t.commit();
			return lista;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean devolverPedido(Integer idPedido) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();

			TPedido pedido = dao.mostrarPedido(idPedido);

			if (pedido == null || !pedido.getActivo() || "ABIERTO".equals(pedido.getEstado())) {
				throw new IllegalArgumentException("Pedido no válido");
			}

			pedido.setEstado("DEVUELTO");
			pedido.setActivo(false);

			Boolean ok = dao.devolverPedido(idPedido);

			if (!ok) {
				throw new RuntimeException("No se pudo devolver pedido");
			}

			t.commit();
			return true;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public TPedido cerrarPedido(Integer idPedido) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOPedido daoPedido = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido();
			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();

			TPedido pedido = daoPedido.mostrarPedido(idPedido);

			if (pedido == null || !pedido.getActivo() || "DEVUELTO".equals(pedido.getEstado())
					|| "CERRADO".equals(pedido.getEstado())) {
				throw new IllegalStateException("Pedido no válido");
			}

			List<TLineaPedido> lineas = daoLinea.mostrarLineasPorPedido(idPedido);

			double total = 0;

			for (TLineaPedido lp : lineas) {
				TProducto p = daoProducto.mostrarProducto(lp.getProductoId());
				total += p.getPrecio() * lp.getCantidad();
			}

			pedido.setTotal(total);
			pedido.setEstado("CERRADO");

			Boolean ok = daoPedido.modificarPedido(pedido);

			if (!ok) {
				throw new RuntimeException("No se pudo cerrar pedido");
			}

			t.commit();
			return pedido;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}
}
