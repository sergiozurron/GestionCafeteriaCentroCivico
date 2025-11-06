package com.grupoms.app.presentacion.controlador.comandos.producto;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.producto.SAProducto;
import com.grupoms.app.negocio.producto.TProducto;

import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarListaProductosCommand implements Command {

    @Override
    public Context execute(Object data) {
        SAProducto saProducto = FactoriaSA.getInstance().creaSAProducto();
        try {
            List<TProducto> productos = saProducto.mostrarListaProductos();
            return new Context(Evento.MOSTRAR_LISTA_PRODUCTO_OK, productos);
        } catch (Exception e) {
            return new Context(Evento.MOSTRAR_LISTA_PRODUCTO_KO, null);
        }
    }
    
}
