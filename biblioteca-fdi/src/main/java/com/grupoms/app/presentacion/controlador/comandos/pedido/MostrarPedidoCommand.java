package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TCarrito;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarPedidoCommand implements Command {

    @Override
    public Context execute(Object data) {

        Integer idPedido = (Integer) data; 
        SAPedido sa = FactoriaSA.getInstance().creaSAPedido();

        try {
            TCarrito carrito = sa.mostrarPedido(idPedido);

            if (carrito != null) {
                return new Context(Evento.MOSTRAR_PEDIDO_OK, carrito);
            } else {
                return new Context(Evento.MOSTRAR_PEDIDO_KO, "No se pudo mostrar el pedido");
            }

        } catch (Exception e) {
            return new Context(Evento.MOSTRAR_PEDIDO_KO, e.getMessage());
        }
    }
}
