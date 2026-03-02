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

	        // 1. Comprobar pedido
	        TPedido pedido = daoPedido.mostrarPedido(linea.getPedidoId());
	        if (pedido == null || !pedido.getActivo()) {
	            t.commit();
	            return -1; // pedido no válido
	        }

	        // 2. Comprobar producto
	        TProducto producto = daoProducto.mostrarProducto(linea.getProductoId());
	        if (producto == null || !producto.getActivo()) {
	            t.commit();
	            return -2; // producto no válido
	        }

	        // 3. Comprobar stock
	        if (producto.getStock() < linea.getCantidad()) {
	            t.commit();
	            return -4; // cantidad inválida (sin stock)
	        }

	        // 4. Restar stock
	        producto.setStock(producto.getStock() - linea.getCantidad());
	        daoProducto.modificarProducto(producto);

	        // 5. Crear línea
	        linea.setActivo(true);
	        Integer id = daoLinea.altaLineaPedido(linea);

	        t.commit();
	        return id;

	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        e.printStackTrace();
	        return -99;
	    }
	}


	@Override
	public Integer bajaLineaPedido(Integer idPedido,Integer idProducto) {

	    Transaction t = TransactionManager.getInstance().newTransaction();

	    try {
	        t.start();

	        DAOLineaPedido daoLinea = FactoriaDAO.getInstancia().creaDAOLineaPedido();
	        
	        
	        daoLinea.bajaLineaPedido(idPedido, idProducto);

	        t.commit();
	        return 1;

	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        e.printStackTrace();
	        return -99;
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
            if (t != null) t.rollback();
            e.printStackTrace();
            return null;
        }
    }
}
