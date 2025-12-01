package com.grupoms.app.presentacion.controlador.comandos;

import java.util.HashMap;
import java.util.Map;

import com.grupoms.app.presentacion.comandos.ListarEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.AltaEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.BajaEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.ModificarEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.MostrarEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.MostrarEjemplaresPorMaterialCommand;
import com.grupoms.app.presentacion.comandos.materialJPA.*;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.ingrediente.*;
import com.grupoms.app.presentacion.controlador.comandos.mesa.*;
import com.grupoms.app.presentacion.controlador.comandos.pedido.*;
import com.grupoms.app.presentacion.controlador.comandos.empleado.*;
import com.grupoms.app.presentacion.controlador.comandos.producto.*;
import com.grupoms.app.presentacion.controlador.comandos.proveedor.*;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class FactoryCommandImp extends FactoryCommand {
    private Map<Integer, Command> commands = new HashMap<>();
	private Map<Integer, String> views = new HashMap<>();

    protected FactoryCommandImp(){
        //cada comando que añadamos hay que añadirlo aqui
    	//COMANDOS
        //Orden
        commands.put(Evento.ALTA_ORDEN, new AltaOrdenCommand());
        commands.put(Evento.BAJA_ORDEN, new AltaOrdenCommand());

    	//Mesa
        commands.put(Evento.ALTA_MESA, new AltaMesaCommand());
        commands.put(Evento.BAJA_MESA, new BajaMesaCommand());
        commands.put(Evento.MODIFICAR_MESA, new ModificarMesaCommand());
        commands.put(Evento.MOSTRAR_MESA, new MostrarMesaCommand());
        commands.put(Evento.MOSTRAR_LISTA_MESA, new MostrarListaMesasCommand());

        //Pedido
        commands.put(Evento.MOSTRAR_PEDIDO, new MostrarPedidoCommand());
        commands.put(Evento.ALTA_PEDIDO, new AltaPedidoCommand());
        commands.put(Evento.DEVOLVER_PEDIDO, new DevolverPedidoCommand());
        commands.put(Evento.CONFIRMAR_PEDIDO, new ConfirmarPedidoCommand());
        commands.put(Evento.MODIFICAR_PEDIDO, new ModificarPedidoCommand());
        commands.put(Evento.MOSTRAR_PEDIDOS, new ListarPedidoCommand());

        //ingrediente
        commands.put(Evento.ALTA_INGREDIENTE, new AltaIngredienteCommand());
        commands.put(Evento.BAJA_INGREDIENTE, new BajaIngredienteCommand());
        commands.put(Evento.MODIFICAR_INGREDIENTE, new ModificarIngredienteCommand());
        commands.put(Evento.MOSTRAR_INGREDIENTE, new MostrarIngredienteCommand());
        commands.put(Evento.MOSTRAR_INGREDIENTES, new MostrarListaIngredientes());
        commands.put(Evento.LISTAR_INGREDIENTES_POR_PRODUCTO, new ListarIngredientesPorProductoCommand());
        commands.put(Evento.LISTAR_INGREDIENTES_POR_PROVEEDOR, new ListarIngredientesPorProveedorCommand());
        

        // Empleado
        commands.put(Evento.ALTA_EMPLEADO, new AltaEmpleadoCommand());
        commands.put(Evento.BAJA_EMPLEADO, new BajaEmpleadoCommand());
        commands.put(Evento.MODIFICAR_EMPLEADO, new ModificarEmpleadoCommand());
        commands.put(Evento.MOSTRAR_EMPLEADO, new MostrarEmpleadoCommand());
        commands.put(Evento.MOSTRAR_EMPLEADOS, new MostrarListaEmpleadosCommand());
        
        // Producto
        commands.put(Evento.ALTA_PRODUCTO, new AltaProductoCommand());
        commands.put(Evento.BAJA_PRODUCTO, new BajaProductoCommand());
        commands.put(Evento.MODIFICAR_PRODUCTO, new ModificarProductoCommand());
        commands.put(Evento.MOSTRAR_PRODUCTO, new MostrarProductoCommand());
        commands.put(Evento.MOSTRAR_LISTA_PRODUCTO, new MostrarListaProductosCommand());
        
        // Proveedor
        commands.put(Evento.ALTA_PROVEEDOR, new AltaProveedorCommand());
        commands.put(Evento.BAJA_PROVEEDOR, new BajaProveedorCommand());
        commands.put(Evento.MODIFICAR_PROVEEDOR, new ModificarProveedorCommand());
        commands.put(Evento.MOSTRAR_PROVEEDOR, new MostrarProveedorCommand());
        commands.put(Evento.MOSTRAR_LISTA_PROVEEDOR, new MostrarListaProveedoresCommand());
        
        
        //Material
        commands.put(Evento.ALTA_MATERIAL,new AltaMaterialCommand());
        commands.put(Evento.LISTAR_MATERIAL,new ListarMaterialCommand());
        commands.put(Evento.MOSTRAR_MATERIAL, new MostrarMaterialCommand());
        commands.put(Evento.MODIFICAR_MATERIAL, new ModificarMaterialCommand());
        commands.put(Evento.MOSTRAR_EJEMPLARMATERIAL, new MostrarEjemplaresPorMaterialCommand());
        commands.put(Evento.BAJA_MATERIAL, new BajaMaterialCommand());
        
        // Ejemplar
        commands.put(Evento.ALTA_EJEMPLAR, new AltaEjemplarCommand());
        commands.put(Evento.BAJA_EJEMPLAR, new BajaEjemplarCommand());
        commands.put(Evento.MODIFICAR_EJEMPLAR, new ModificarEjemplarCommand());
        commands.put(Evento.MOSTRAR_EJEMPLAR, new MostrarEjemplarCommand());
        commands.put(Evento.LISTAR_EJEMPLARES, new ListarEjemplarCommand());

        
        
        //VISTAS
        views.put(Evento.MOSTRAR_PEDIDO, FactoriaVistas.GUI_MOSTRAR_PEDIDO);
        views.put(Evento.MODIFICAR_PEDIDO, FactoriaVistas.GUI_MODIFICAR_PEDIDO);
        views.put(Evento.ALTA_PEDIDO, FactoriaVistas.GUI_ALTA_PEDIDO);
        views.put(Evento.DEVOLVER_PEDIDO, FactoriaVistas.GUI_DEVOLVER_PEDIDO);
        views.put(Evento.MOSTRAR_PEDIDOS, FactoriaVistas.GUI_LISTAR_PEDIDO);

        views.put(Evento.ALTA_INGREDIENTE, FactoriaVistas.GUI_ALTA_INGREDIENTE);
        views.put(Evento.MODIFICAR_INGREDIENTE, FactoriaVistas.GUI_MODIFICAR_INGREDIENTE);
        views.put(Evento.MOSTRAR_INGREDIENTE, FactoriaVistas.GUI_MOSTRAR_INGREDIENTE);
        views.put(Evento.MOSTRAR_INGREDIENTES, FactoriaVistas.GUI_LISTAR_INGREDIENTES);
        views.put(Evento.LISTAR_INGREDIENTES_POR_PROVEEDOR, FactoriaVistas.GUI_MOSTRAR_INGREDIENTES_PROVEEDOR);
        views.put(Evento.LISTAR_INGREDIENTES_POR_PRODUCTO, FactoriaVistas.GUI_MOSTRAR_INGREDIENTES_PRODUCTO);

        views.put(Evento.ALTA_EMPLEADO, FactoriaVistas.GUI_ALTA_EMPLEADO);
        views.put(Evento.BAJA_EMPLEADO, FactoriaVistas.GUI_BAJA_EMPLEADO);
        views.put(Evento.MODIFICAR_EMPLEADO, FactoriaVistas.GUI_MODIFICAR_EMPLEADO);
        views.put(Evento.MOSTRAR_EMPLEADO, FactoriaVistas.GUI_MOSTRAR_EMPLEADO);
        views.put(Evento.MOSTRAR_EMPLEADOS, FactoriaVistas.GUI_LISTAR_EMPLEADOS);
        
        views.put(Evento.ALTA_MESA, FactoriaVistas.GUI_ALTA_MESA);
        views.put(Evento.BAJA_MESA, FactoriaVistas.GUI_BAJA_MESA);
        views.put(Evento.MODIFICAR_MESA, FactoriaVistas.GUI_MODIFICAR_MESA);
        views.put(Evento.MOSTRAR_MESA, FactoriaVistas.GUI_MOSTRAR_MESA);
        views.put(Evento.MOSTRAR_LISTA_MESA, FactoriaVistas.GUI_LISTAR_MESAS);
        
        views.put(Evento.ALTA_PRODUCTO, FactoriaVistas.GUI_ALTA_PRODUCTO);
        views.put(Evento.BAJA_PRODUCTO, FactoriaVistas.GUI_BAJA_PRODUCTO);
        views.put(Evento.MODIFICAR_PRODUCTO, FactoriaVistas.GUI_MODIFICAR_PRODUCTO);
        views.put(Evento.MOSTRAR_PRODUCTO, FactoriaVistas.GUI_MOSTRAR_PRODUCTO);
        views.put(Evento.MOSTRAR_LISTA_PRODUCTO, FactoriaVistas.GUI_LISTAR_PRODUCTOS);
        
        views.put(Evento.ALTA_PROVEEDOR, FactoriaVistas.GUI_ALTA_PROVEEDOR);
        views.put(Evento.BAJA_PROVEEDOR, FactoriaVistas.GUI_BAJA_PROVEEDOR);
        views.put(Evento.MODIFICAR_PROVEEDOR, FactoriaVistas.GUI_MODIFICAR_PROVEEDOR);
        views.put(Evento.MOSTRAR_PROVEEDOR, FactoriaVistas.GUI_MOSTRAR_PROVEEDOR);
        views.put(Evento.MOSTRAR_LISTA_PROVEEDOR, FactoriaVistas.GUI_LISTAR_PROVEEDORES);
        
        views.put(Evento.ALTA_MATERIAL,FactoriaVistas.GUI_ALTA_MATERIAL);
        views.put(Evento.LISTAR_MATERIAL, FactoriaVistas.GUI_LISTAR_MATERIAL);
        views.put(Evento.MOSTRAR_MATERIAL, FactoriaVistas.GUI_MOSTRAR_MATERIAL);
        views.put(Evento.MODIFICAR_MATERIAL, FactoriaVistas.GUI_MODIFICAR_MATERIAL);
        views.put(Evento.MOSTRAR_EJEMPLARMATERIAL, FactoriaVistas.GUI_LISTAR_EJEMPLARESMATERIAL);
        views.put(Evento.BAJA_MATERIAL, FactoriaVistas.GUI_BAJA_MATERIAL);


        views.put(Evento.ALTA_PROMOCION, FactoriaVistas.GUI_ALTA_PROMOCION);
        views.put(Evento.BAJA_PROMOCION, FactoriaVistas.GUI_BAJA_PROMOCION);
        views.put(Evento.MODIFICAR_PROMOCION, FactoriaVistas.GUI_MODIFICAR_PROMOCION);
        views.put(Evento.LISTAR_PROMOCION, FactoriaVistas.GUI_LISTAR_PROMOCION);
        views.put(Evento.MOSTRAR_PROMOCION, FactoriaVistas.GUI_MOSTRAR_PROMOCION);
        views.put(Evento.VER_PROMOCIONES_POR_SOCIO, FactoriaVistas.GUI_VER_PROMOCIONES_POR_SOCIO);
        
        views.put(Evento.ALTA_EJEMPLAR, FactoriaVistas.GUI_ALTA_EJEMPLAR);
        views.put(Evento.BAJA_EJEMPLAR, FactoriaVistas.GUI_BAJA_EJEMPLAR);
        views.put(Evento.MODIFICAR_EJEMPLAR, FactoriaVistas.GUI_MODIFICAR_EJEMPLAR);
        views.put(Evento.MOSTRAR_EJEMPLAR, FactoriaVistas.GUI_MOSTRAR_EJEMPLAR);
        views.put(Evento.LISTAR_EJEMPLARES, FactoriaVistas.GUI_LISTAR_EJEMPLAR);
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
