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
			// 1. Leer el pedido actual desde BD con FOR UPDATE 
			TPedido pedidoBD = dao.mostrarPedido(pedido.getId()); 
			if (pedidoBD == null || !pedidoBD.getActivo()) { 
				t.commit(); 
				return false; // no existe o está dado de baja 
			} // 2. Actualizar los campos permitidos 
			pedidoBD.setFecha(pedido.getFecha()); 
			pedidoBD.setEstado(pedido.getEstado()); 
			pedidoBD.setIdEmpleado(pedido.getIdEmpleado()); 
			pedidoBD.setIdMesa(pedido.getIdMesa()); 
			pedidoBD.setTotal(pedido.getTotal()); // si lo permitís modificar 
			// 3. Guardar cambios 
			dao.modificarPedido(pedidoBD); 
			t.commit(); 
			return true; 
			} catch (Exception e) { 
				if (t != null) 
					t.rollback(); 
				e.printStackTrace(); 
				return false;
			}
	}

	@Override
	public Integer altaPedido(TPedido pedido) {

	    Integer id = null;

	    Transaction t = TransactionManager.getInstance().newTransaction();
	    try {
	        t.start();

	        DAOPedido daoPedido = FactoriaDAO.getInstancia().creaDAOPedido();
	        DAOEmpleado daoEmpleado = FactoriaDAO.getInstancia().creaDAOEmpleado();
	        DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

	        TEmpleado empleado = daoEmpleado.mostrarEmpleado(pedido.getIdEmpleado());
	        TMesa mesa = daoMesa.mostrarMesa(pedido.getIdMesa());

	        // 1️ Comprobar empleado
	        if (empleado == null || !empleado.getActivo()) {
	            return -1;
	        }

	        // 2️ Comprobar mesa
	        if (mesa == null || !mesa.getActivo()) {
	            return -2;
	        }

	        // 3️ Inicializar pedido
	        pedido.setActivo(true);
	        pedido.setEstado("ABIERTO");
	        pedido.setTotal(0.0);
	        pedido.setFecha(new java.sql.Date(System.currentTimeMillis()));

	        id = daoPedido.altaPedido(pedido);

	        t.commit();
	        return id;

	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        e.printStackTrace();
	        return -3;
	    }
	}

	@Override
	public TCarrito mostrarPedido(Integer idPedido) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		TCarrito carrito = null;
		try {
			t.start();
			DAOPedido daoPedido = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido();
			
			TPedido pedido = daoPedido.mostrarPedido(idPedido);
			
			if(pedido == null || !pedido.getActivo()) {
				t.commit();
				return null;
			}
			
			List<TLineaPedido> lineas = daoLinea.mostrarLineasPorPedido(idPedido);
			
			carrito = new TCarrito();
			carrito.setPedido(pedido);
			carrito.setLineasPedido(lineas);

			t.commit();
			return carrito;
		}catch(Exception e) {
			if (t != null) 
				t.rollback(); 
			e.printStackTrace(); 
			return null;
		}
	}

	@Override
	public List<TPedido> mostrarListaPedidos() {
		Transaction t = TransactionManager.getInstance().newTransaction();
		List<TPedido> lista = null;
		
		try {
			t.start();
			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();
			
			lista = dao.mostrarListaPedidos();
			t.commit();
			return lista;
		}catch(Exception e) {
			if(t!=null)t.rollback();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TPedido> mostrarPedidosPorMesa(Integer idMesa) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		List<TPedido> lista = null;
		
		try {
			t.start();
			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();
			
			lista = dao.mostrarPedidosPorMesa(idMesa);
			t.commit();
			return lista;
		}catch(Exception e) {
			if(t!=null)t.rollback();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		List<TPedido> lista = null;
		
		try {
			t.start();
			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();
			
			lista = dao.mostrarPedidosPorEmpleado(idEmpleado);
			t.commit();
			return lista;
		}catch(Exception e) {
			if(t!=null)t.rollback();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean devolverPedido(Integer idPedido) {
		Transaction t = TransactionManager.getInstance().newTransaction(); 
		try { 
			t.start(); 
			DAOPedido daoPedido = FactoriaDAO.getInstancia().creaDAOPedido(); 
			DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido(); // 1. Leer el pedido con FOR UPDATE 
			TPedido pedido = daoPedido.mostrarPedido(idPedido); // este método debe llevar FOR UPDATE 
			if (pedido == null || !pedido.getActivo()) { 
				t.commit(); 
				return false; // no existe o ya está dado de baja 
			} 
			// 2. Dar de baja lógica al pedido 
			daoPedido.devolverPedido(pedido.getId()); 
			
			t.commit();
			return true;
		} catch (Exception e) { 
			if (t != null) 
				t.rollback(); 
			e.printStackTrace(); 
			return false; 
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

	        // 1. Obtener pedido
	        TPedido pedido = daoPedido.mostrarPedido(idPedido);
	        if (pedido == null || !pedido.getActivo()) {
	            t.commit();
	            return null; // pedido no válido
	        }

	        // 2. Obtener líneas activas
	        List<TLineaPedido> lineas = daoLinea.mostrarLineasPorPedido(idPedido);

	        // 3. Calcular total
	        double total = 0;
	        for (TLineaPedido lp : lineas) {
	            TProducto p = daoProducto.mostrarProducto(lp.getProductoId());
	            total += p.getPrecio() * lp.getCantidad();
	        }

	        // 4. Actualizar pedido
	        pedido.setTotal(total);
	        pedido.setEstado("CERRADO");
	        daoPedido.modificarPedido(pedido);

	        t.commit();
	        return pedido;

	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        e.printStackTrace();
	        return null;
	    }
	}


	
}
