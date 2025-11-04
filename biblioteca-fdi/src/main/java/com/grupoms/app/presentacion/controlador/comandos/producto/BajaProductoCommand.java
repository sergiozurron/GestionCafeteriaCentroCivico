package com.grupoms.app.presentacion.controlador.comandos.producto;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.producto.SAProducto;

import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaProductoCommand implements Command {

    @Override
    public Context execute(Object data) {
        if (!(data instanceof Integer)) {
            return new Context(Evento.BAJA_PRODUCTO_KO, null);
        }

        int productoId = (Integer) data;
        SAProducto saProducto = FactoriaSA.getInstance().creaSAProducto();
        try {
            saProducto.bajaProducto(productoId);
            return new Context(Evento.BAJA_PRODUCTO_OK, productoId);
        } catch (Exception e) {
            return new Context(Evento.BAJA_PRODUCTO_KO, null);
        }
    }
    
}
