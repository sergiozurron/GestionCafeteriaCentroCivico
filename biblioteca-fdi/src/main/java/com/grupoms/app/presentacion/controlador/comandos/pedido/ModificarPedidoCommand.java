package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarPedidoCommand implements Command{

    @Override
    public Context execute(Object data) {
         if (!(data instanceof TPedido)) {
            return new Context(Evento.MODIFICAR_PEDIDO_KO, null);
        }

        TPedido pedido = (TPedido) data;
        SAPedido saPedido = FactoriaSA.getInstance().creaSAPedido();

        try {
            Integer idPedido = saPedido.modificarPedido(pedido);
            pedido.setId(idPedido);


            return new Context(Evento.MODIFICAR_PEDIDO_OK, pedido);
        } catch (Exception e) {
            return new Context(Evento.MODIFICAR_PEDIDO_KO, null);
        }
    }
    
}
