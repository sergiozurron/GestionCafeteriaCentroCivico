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
        try{
            FactoriaSA.getInstance().creaSAPedido().confirmarPedido(pedido);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return new Context(Evento.DEVOLVER_PEDIDO_KO,null);
        }
        return new Context(Evento.DEVOLVER_PEDIDO_OK,null);
    }
    
}
