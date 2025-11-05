package com.grupoms.app.presentacion.controlador.comandos.ingrediente;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;
import com.grupoms.app.negocio.ingrediente.TIngrediente;

import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarIngredientesPorProductoCommand implements Command{

    @Override
    public Context execute(Object data) {
        SAIngrediente saProducto = FactoriaSA.getInstance().creaSAIngrediente();
        try {
            List<TIngrediente> productos = saProducto.mostrarListaIngredientes();
            return new Context(Evento.MOSTRAR_INGREDIENTES_OK, productos);
        } catch (Exception e) {
            return new Context(Evento.MOSTRAR_INGREDIENTES_KO, null);
        }
    }
    
}
