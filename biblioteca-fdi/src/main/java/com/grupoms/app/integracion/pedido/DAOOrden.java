package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TOrden;

public interface DAOOrden {
    Integer altaOrden(TOrden orden) throws Exception;
    void vincularProducto(TOrden orden) throws Exception;
    void desvincularProducto(TOrden orden) throws Exception;
    TOrden mostrarOrden(Integer idOrden) throws Exception;
}
