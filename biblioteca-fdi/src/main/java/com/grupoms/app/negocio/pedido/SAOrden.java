package com.grupoms.app.negocio.pedido;

import java.util.Set;

public interface SAOrden {
    public Integer crearOrden(TOrden orden);
    
    public boolean eliminarOrden(Integer id);
    
    public TOrden mostrarOrden(Integer id);
    
    public Set<TOrden> listarOrdenesPorPedido(Integer pedidoID);
}
