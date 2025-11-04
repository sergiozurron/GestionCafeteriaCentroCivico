package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaPedidoCommand implements Command{

    @Override
    public Context execute(Object data) {
        if (!(data instanceof TPedido)) {
            return new Context(Evento.ALTA_PEDIDO_KO, null);
        }

        TPedido pedido = (TPedido) data;
        SAPedido sa = FactoriaSA.getInstance().creaSAPedido();

        try {
            sa.altaPedido(pedido);
            return new Context(Evento.ALTA_PEDIDO_OK, pedido);
        } catch (IllegalArgumentException e) {
            return new Context(Evento.ALTA_PEDIDO_KO, null);
        }
    }
    
}
