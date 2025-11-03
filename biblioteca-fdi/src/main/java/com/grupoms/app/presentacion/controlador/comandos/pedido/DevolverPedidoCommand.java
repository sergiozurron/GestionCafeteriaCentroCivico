package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class DevolverPedidoCommand implements Command {

    @Override
    public Context execute(Object data) {
       TPedido pedido = (TPedido) data;
       SAPedido sa = FactoriaSA.getInstancia().creaSAPedido();
       sa.devolverPedido(pedido);
       return new Context(Evento.DEVOLVER_PEDIDO, pedido);
    }
    
}
