package com.grupoms.app.negocio.pedido;
import java.util.Set;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.pedido.*;

public class SAPedidoImp implements SAPedido{

    private DAOPedido dao = new DAOPedidoImp();

    @Override
    public Integer altaPedido(TPedido pedido) {
        Transaction t = null;
        Integer idGenerado = null;

        try {
            if (pedido == null)
                throw new IllegalArgumentException("El pedido no puede ser nulo.");

            t = TransactionManager.getInstance().newTransaction();
            t.start();

            pedido.setEstado("ABIERTO");
            pedido.setTotal(0.0);
            pedido.setActivo(true);
            pedido.setFecha(new java.sql.Date(System.currentTimeMillis()));

            idGenerado = dao.altaPedido(pedido);

            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al crear el pedido", e);
        }

        return idGenerado;
    }

    @Override
    public Boolean confirmarPedido(TPedido pedido) {
       Transaction t = null;
       Boolean exito = false;
       try{
         if (pedido == null || pedido.getId() == null)
            throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener ID.");

        t = TransactionManager.getInstance().getTransaction();
        t.start();

        pedido.setEstado("EN PREPARACION");

        dao.modificarPedido(pedido);  
        t.commit();
        exito = true;
       }catch (Exception e) {
            e.printStackTrace();
            try {
                if (t != null) t.rollback(); // rollback si falla algo
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new IllegalArgumentException("Error al confirmar el pedido.", e);
        }
        return exito;
    }

    @Override
    public TPedido mostrarPedido(Integer idPedido) {
        if (idPedido == null || idPedido <= 0)
            throw new IllegalArgumentException("El ID del pedido no es válido.");
        
            TPedido pedido = null;
        Transaction t = null;
        try{
            t = TransactionManager.getInstance().getTransaction();
            t.start();
            pedido = dao.mostrarPedido(idPedido);
            t.commit();
        }catch(Exception e){
          e.printStackTrace();
            try {
                if (t != null) t.rollback(); // rollback si falla algo
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new IllegalArgumentException("Error al mostrar el pedido con ID: " + idPedido, e);

        }
        return pedido;
    }

    @Override
    public void devolverPedido(TPedido pedido) {
        Transaction t = TransactionManager.getInstance().getTransaction();
        try {
            if (pedido == null || pedido.getId() == null)
                throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener ID.");
            t.start();

            // Llamada al DAO para cambiar estado y activo
            pedido.setActivo(false);
            pedido.setEstado("DEVUELTO");
            dao.modificarPedido(pedido);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (t != null) t.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new IllegalArgumentException("Error al devolver el pedido.", e);

        }
    }

    @Override
    public Integer modificarPedido(TPedido pedido) {
        if (pedido == null || pedido.getId() == null)
            throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener ID.");

        Transaction t = null;
        Integer resultado = -1;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // verificar que el pedido existe
            TPedido pedidoExistente = dao.mostrarPedido(pedido.getId());
            if (pedidoExistente == null)
                throw new IllegalArgumentException("El pedido con ID " + pedido.getId() + " no existe.");

            Boolean exito = dao.modificarPedido(pedido);
            if (exito) {
                resultado = pedido.getId();
            }

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al modificar el pedido.", e);
        }
        return resultado;
    }


    @Override
    public Integer vincularProducto(Integer idPedido, Integer idProducto, Integer cantidad) {
        if (idPedido == null || idPedido <= 0)
            throw new IllegalArgumentException("El ID del pedido no es válido.");
        if (idProducto == null || idProducto <= 0)
            throw new IllegalArgumentException("El ID del producto no es válido.");
        if (cantidad == null || cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");

        Transaction t = null;
        Integer idOrden = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // verificar que el pedido existe
            TPedido pedido = dao.mostrarPedido(idPedido);
            if (pedido == null)
                throw new IllegalArgumentException("El pedido con ID " + idPedido + " no existe.");

            // y vincular el producto al pedido
            idOrden = dao.vincularProducto(idPedido, idProducto, cantidad);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al vincular producto al pedido.", e);
        }
        return idOrden;
    }

    @Override
    public Integer desvincularProducto(Integer idPedido, Integer idProducto) {
        if (idPedido == null || idPedido <= 0)
            throw new IllegalArgumentException("El ID del pedido no es válido.");
        if (idProducto == null || idProducto <= 0)
            throw new IllegalArgumentException("El ID del producto no es válido.");

        Transaction t = null;
        Integer filasAfectadas = 0;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();

            // verificar que el pedido existe
            TPedido pedido = dao.mostrarPedido(idPedido);
            if (pedido == null)
                throw new IllegalArgumentException("El pedido con ID " + idPedido + " no existe.");

            // y desvincular el producto del pedido
            filasAfectadas = dao.desvincularProducto(idPedido, idProducto);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al desvincular producto del pedido.", e);
        }
        return filasAfectadas;
    }

    @Override
    public Set<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado) {
        if (idEmpleado == null || idEmpleado <= 0)
            throw new IllegalArgumentException("El ID del empleado no es válido.");

        Set<TPedido> listaPedidos = null;
        Transaction t = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            listaPedidos = dao.mostrarPedidosPorEmpleado(idEmpleado);
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al mostrar pedidos del empleado con ID: " + idEmpleado, e);
        }
        return listaPedidos;
    }

    @Override
    public Set<TPedido> mostrarPedidosPorMesa(Integer idMesa) {
        if (idMesa == null || idMesa <= 0)
            throw new IllegalArgumentException("El ID de la mesa no es válido.");

        Set<TPedido> listaPedidos = null;
        Transaction t = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            listaPedidos = dao.mostrarPedidosPorMesa(idMesa);
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al mostrar pedidos de la mesa con ID: " + idMesa, e);
        }
        return listaPedidos;
    }

     @Override
    public Set<TPedido> mostrarPedidos() {
        Set<TPedido> listaPedidos = null;
        Transaction t = null;
        try {
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            listaPedidos = dao.mostrarPedidos();
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
            throw new IllegalArgumentException("Error al mostrar la lista de pedidos.", e);
        }
        return listaPedidos;
    }

    
}
