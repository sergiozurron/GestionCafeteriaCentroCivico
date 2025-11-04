package com.grupoms.app.presentacion.controlador.comandos;

import java.util.HashMap;
import java.util.Map;

import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.mesa.AltaMesaCommand;
import com.grupoms.app.presentacion.controlador.comandos.mesa.BajaMesaCommand;
import com.grupoms.app.presentacion.controlador.comandos.mesa.ModificarMesaCommand;
import com.grupoms.app.presentacion.controlador.comandos.mesa.MostrarMesaCommand;
import com.grupoms.app.presentacion.controlador.comandos.mesa.MostrarMesasCommand;

public class FactoryCommandImp extends FactoryCommand {
    private Map<Integer, Command> commands = new HashMap<>();
	private Map<Integer, String> views = new HashMap<>();

    public FactoryCommandImp(){
        //cada comando que añadamos hay que añadirlo aqui
    	
    	//Mesa
        commands.put(Evento.ALTA_MESA, new AltaMesaCommand());
        commands.put(Evento.BAJA_MESA, new BajaMesaCommand());
        commands.put(Evento.MODIFICAR_MESA, new ModificarMesaCommand());
        commands.put(Evento.MOSTRAR_MESA, new MostrarMesaCommand());
        commands.put(Evento.MOSTRAR_LISTA_MESA, new MostrarMesasCommand());


        //views.put(Evento.ALTA_MESA, FactoriaVistas.GUI_ALTA_MESA);
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
