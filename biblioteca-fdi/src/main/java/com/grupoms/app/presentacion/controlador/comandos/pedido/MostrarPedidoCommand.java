package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarPedidoCommand implements Command {

    @Override
    public Context execute(Object data) {
        int id = (int) data;
        SAPedido saPedido = FactoriaSA.getInstance().creaSAPedido();

        TPedido pedido = saPedido.mostrarPedido(id);

        if (pedido != null)
            return new Context(Evento.MOSTRAR_PEDIDO_OK, pedido);
        else
            return new Context(Evento.MOSTRAR_PEDIDO_KO, null);
    }
    
}
