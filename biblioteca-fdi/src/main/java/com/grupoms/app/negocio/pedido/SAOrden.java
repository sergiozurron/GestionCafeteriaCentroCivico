package com.grupoms.app.negocio.pedido;

public interface SAOrden {
    Integer altaOrden(TOrden orden);
    void vincularProducto(TOrden orden);
    TOrden mostrarOrden(Integer idOrden);
    Boolean bajaOrden(TOrden orden);
}
