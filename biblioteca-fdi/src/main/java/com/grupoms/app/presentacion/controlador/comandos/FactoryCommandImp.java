package com.grupoms.app.presentacion.controlador.comandos;

import java.util.HashMap;
import java.util.Map;

import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.ingrediente.*;
import com.grupoms.app.presentacion.controlador.comandos.mesa.*;
import com.grupoms.app.presentacion.controlador.comandos.pedido.*;

public class FactoryCommandImp extends FactoryCommand {
    private Map<Integer, Command> commands = new HashMap<>();
	private Map<Integer, String> views = new HashMap<>();

    public FactoryCommandImp(){
        //cada comando que añadamos hay que añadirlo aqui
    	//COMANDOS
    	//Mesa
        commands.put(Evento.ALTA_MESA, new AltaMesaCommand());
        commands.put(Evento.BAJA_MESA, new BajaMesaCommand());
        commands.put(Evento.MODIFICAR_MESA, new ModificarMesaCommand());
        commands.put(Evento.MOSTRAR_MESA, new MostrarMesaCommand());
        commands.put(Evento.MOSTRAR_LISTA_MESA, new MostrarMesasCommand());

        //Pedido
        commands.put(Evento.MOSTRAR_PEDIDO, new MostrarPedidoCommand());
        commands.put(Evento.ALTA_PEDIDO, new AltaPedidoCommand());
        commands.put(Evento.DEVOLVER_PEDIDO, new DevolverPedidoCommand());
        commands.put(Evento.CONFIRMAR_PEDIDO, new ConfirmarPedidoCommand());
        commands.put(Evento.MODIFICAR_PEDIDO, new ModificarPedidoCommand());

        //ingrediente
        commands.put(Evento.ALTA_INGREDIENTE, new AltaIngredienteCommand());
        commands.put(Evento.BAJA_INGREDIENTE, new BajaIngredienteCommand());
        commands.put(Evento.MODIFICAR_INGREDIENTE, new ModificarIngredienteCommand());
        commands.put(Evento.MOSTRAR_INGREDIENTE, new MostrarIngredienteCommand());
        commands.put(Evento.MOSTRAR_INGREDIENTES, new MostrarListaIngredientes());




        //VISTAS
        views.put(Evento.MOSTRAR_PEDIDO, "GUI_MOSTRAR_PEDIDO");
        views.put(Evento.MOSTRAR_PEDIDO_OK, "GUI_MOSTRAR_PEDIDO");
        views.put(Evento.MOSTRAR_PEDIDO_KO, "GUI_MOSTRAR_PEDIDO");

        views.put(Evento.ALTA_INGREDIENTE, "GUI_ALTA_INGREDIENTE");
        views.put(Evento.ALTA_INGREDIENTE_OK, "GUI_ALTA_INGREDIENTE");
        views.put(Evento.ALTA_INGREDIENTE_KO, "GUI_ALTA_INGREDIENTE");

        views.put(Evento.MODIFICAR_INGREDIENTE, "GUI_MODIFICAR_INGREDIENTE");
        views.put(Evento.MODIFICAR_INGREDIENTE_OK, "GUI_MODIFICAR_INGREDIENTE");
        views.put(Evento.MODIFICAR_INGREDIENTE_KO, "GUI_MODIFICAR_INGREDIENTE");

        views.put(Evento.ALTA_MESA, "GUI_ALTA_MESA");
        views.put(Evento.ALTA_MESA_OK, "GUI_ALTA_MESA");
        views.put(Evento.ALTA_MESA_KO, "GUI_ALTA_MESA");

        views.put(Evento.MODIFICAR_PEDIDO, "GUI_MODIFICAR_PEDIDO");
        views.put(Evento.MODIFICAR_PEDIDO_OK, "GUI_MODIFICAR_PEDIDO");
        views.put(Evento.MODIFICAR_PEDIDO_KO, "GUI_MODIFICAR_PEDIDO");

        views.put(Evento.BAJA_MESA, "GUI_BAJA_MESA");
        views.put(Evento.BAJA_MESA_KO, "GUI_BAJA_MESA");
        views.put(Evento.BAJA_MESA_OK, "GUI_BAJA_MESA");


        views.put(Evento.MODIFICAR_MESA, "GUI_MODIFICAR_MESA");
        views.put(Evento.MODIFICAR_MESA_KO, "GUI_MODIFICAR_MESA");
        views.put(Evento.MODIFICAR_MESA_OK, "GUI_MODIFICAR_MESA");

        views.put(Evento.MOSTRAR_INGREDIENTE, "GUI_MOSTRAR_INGREDIENTE");
        views.put(Evento.MOSTRAR_INGREDIENTE_KO, "GUI_MOSTRAR_INGREDIENTE");
        views.put(Evento.MOSTRAR_INGREDIENTE_OK, "GUI_MOSTRAR_INGREDIENTE");

        views.put(Evento.MOSTRAR_INGREDIENTES, "GUI_MOSTRAR_INGREDIENTE");
        views.put(Evento.MOSTRAR_INGREDIENTES_OK, "GUI_LISTAR_INGREDIENTES");
        views.put(Evento.MOSTRAR_INGREDIENTES_KO, "GUI_MOSTRAR_INGREDIENTE");
    }
    @Override
    public Command getCommand(Integer event) {
        return commands.get(event);
    }

    @Override
    public String getView(Integer event) {
       return views.get(event);
    }
}
