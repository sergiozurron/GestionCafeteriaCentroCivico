package com.grupoms.app.presentacion.controlador.comandos.ingrediente;

import java.util.HashSet;
import java.util.Set;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarIngredientesPorProductoCommand implements Command{

    @Override
    public Context execute(Object data) {
        Set<TIngrediente> res = new HashSet<>();
            // Llamamos al SA pasando el ID del producto
        res = FactoriaSA.getInstance().creaSAIngrediente().listarIngredientesPorProducto((Integer) data);

        if(res==null || res.isEmpty())return new Context(Evento.LISTAR_INGREDIENTES_POR_PRODUCTO_KO, null);
        else return new Context(Evento.ALTA_INGREDIENTE_OK,res);
    }
    
}
