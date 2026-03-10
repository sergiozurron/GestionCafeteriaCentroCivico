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

	        // 1. Leer el pedido original
	        TPedido pedidoBD = dao.mostrarPedido(pedido.getId()); 
	        if (pedidoBD == null || !pedidoBD.getActivo()) { 
	            t.rollback(); 
	            return false;
	        }

	        // 2. Validar empleado SOLO si el usuario quiere cambiarlo
	        if (pedido.getIdEmpleado() != null) {
	            TEmpleado empleado = daoEmpleado.mostrarEmpleado(pedido.getIdEmpleado());
	            if (empleado == null || !empleado.getActivo()) {
	                t.rollback();
	                return false;
	            }
	        }

	        // 3. Validar mesa SOLO si el usuario quiere cambiarla
	        if (pedido.getIdMesa() != null) {
	            TMesa mesa = daoMesa.mostrarMesa(pedido.getIdMesa());
	            if (mesa == null || !mesa.getActivo()) {
	                t.rollback();
	                return false;
	            }
	        }

	        // 4. Actualizar solo los campos rellenados
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

	        // 5. Guardar cambios
	        dao.modificarPedido(pedidoBD);
	        t.commit(); 
	        return true; 

	    } catch (Exception e) { 
	        if (t != null) t.rollback(); 
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

	        if (empleado == null || !empleado.getActivo()) {
	            t.rollback();
	            return -1;
	        }

	        if (mesa == null || !mesa.getActivo()) {
	            t.rollback();
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
			
			if(pedido == null) {
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
		List<TPedido> listaV = new ArrayList<>();
		
		try {
			t.start();
			DAOPedido dao = FactoriaDAO.getInstancia().creaDAOPedido();
			DAOMesa daoM = FactoriaDAO.getInstancia().creaDAOMesa();
			DAOEmpleado daoE = FactoriaDAO.getInstancia().creaDAOEmpleado();
			List<TPedido>lista = dao.mostrarListaPedidos();
			
			for(TPedido p : lista) {
				TMesa m = daoM.mostrarMesa(p.getIdMesa());
				TEmpleado e = daoE.mostrarEmpleado(p.getIdEmpleado());
				if(m.getActivo() && e.getActivo()) {
					listaV.add(p);
				}
			}
			t.commit();
			return listaV;
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
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
			TMesa mesa = daoMesa.mostrarMesa(idMesa);
			if(mesa.getActivo() && mesa !=null) {
				lista = dao.mostrarPedidosPorMesa(idMesa);
			}
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

	        // 1. BUSCAR
	        TPedido pedido = daoPedido.mostrarPedido(idPedido);
	        if (pedido == null || !pedido.getActivo() || "ABIERTO".equals(pedido.getEstado())) {
	            t.rollback();
	            return false;
	        }
	        
	        // 2.ESTADO Y BAJA
	        pedido.setEstado("DEVUELTO");
	        pedido.setActivo(false);

	        daoPedido.devolverPedido(pedido.getId());

	        t.commit();
	        return true;

	    } catch (Exception e) {
	        if (t != null) t.rollback();
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
	        if (pedido == null || !pedido.getActivo() || "DEVUELTO".equals(pedido.getEstado()) || "CERRADO".equals(pedido.getEstado())) {
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
