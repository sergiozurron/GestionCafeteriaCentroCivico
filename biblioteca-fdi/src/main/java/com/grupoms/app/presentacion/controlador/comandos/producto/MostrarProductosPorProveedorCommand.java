package com.grupoms.app.presentacion.controlador.comandos.producto;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.producto.SAProducto;
import com.grupoms.app.negocio.producto.TProducto;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarProductosPorProveedorCommand implements Command {

    @Override
    public Context execute(Object data) {
        if (!(data instanceof Integer)) {
            return new Context(Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR_KO, null);
        }

        Integer idProveedor = (Integer) data;
        SAProducto saProducto = FactoriaSA.getInstance().creaSAProducto();

        try {
            List<TProducto> productos = saProducto.mostrarProductosPorProveedor(idProveedor);
            if (productos != null && !productos.isEmpty()) {
                return new Context(Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR_OK, productos);
            } else {
                return new Context(Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR_OK, productos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Context(Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR_KO, null);
        }
    }
}