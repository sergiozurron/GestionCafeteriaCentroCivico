package com.grupoms.app.integracion.pedido;
import java.util.Set;

import com.grupoms.app.negocio.pedido.TOrden;

public interface DAOOrden {
    public Integer crearOrden(TOrden orden);
    
    public boolean bajaOrden(Integer id);
    
    public TOrden mostrarOrden(Integer id);
    
    public Set<TOrden> listarOrdenesPorPedido(Integer pedidoID);
}
