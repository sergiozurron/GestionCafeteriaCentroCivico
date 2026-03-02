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

public class SAPedidoImp implements SAPedido {

	@Override
	public Boolean modificarPedido(TPedido pedido) {
		// TODO Auto-generated method stub
		return null;
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
			carrito.setLineasVenta(lineas);
			
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TPedido> mostrarPedidosPorMesa(Integer idMesa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean devolverPedido(Integer idPedido) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public TPedido cerrarPedido(Integer idPedido) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
