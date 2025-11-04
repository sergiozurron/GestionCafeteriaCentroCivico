package com.grupoms.app.presentacion.controlador.comandos.producto;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.producto.SAProducto;
import com.grupoms.app.negocio.producto.TProducto;

import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaProductoCommand implements Command {

    @Override
    public Context execute(Object data) {
        if (!(data instanceof TProducto)) {
            return new Context(Evento.ALTA_PRODUCTO_KO, null);
        }

        TProducto producto = (TProducto) data;
        SAProducto saProducto = FactoriaSA.getInstance().creaSAProducto();
        try {
            saProducto.altaProducto(producto);
            return new Context(Evento.ALTA_PRODUCTO_OK, producto);
        } catch (Exception e) {
            return new Context(Evento.ALTA_PRODUCTO_KO, null);
        }
    }
}
