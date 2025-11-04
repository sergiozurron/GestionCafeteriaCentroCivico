package com.grupoms.app.negocio.pedido;

public interface SAOrden {
    Integer altaOrden(TOrden orden);
    void vincularProducto(TOrden orden);
    void desvincularProducto(TOrden orden);
    TOrden mostrarOrden(Integer idOrden);
}
