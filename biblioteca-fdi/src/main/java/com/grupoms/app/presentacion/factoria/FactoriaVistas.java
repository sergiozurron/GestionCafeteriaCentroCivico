package com.grupoms.app.presentacion.factoria;

import java.util.HashMap;
import java.util.Map;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.empleado.GUI_AltaEmpleado;
import com.grupoms.app.presentacion.empleado.GUI_BajaEmpleado;
import com.grupoms.app.presentacion.empleado.GUI_ListarEmpleado;
import com.grupoms.app.presentacion.empleado.GUI_ModificarEmpleado;
import com.grupoms.app.presentacion.empleado.GUI_MostrarEmpleado;
import com.grupoms.app.presentacion.ingrediente.GUI_AltaIngrediente;
import com.grupoms.app.presentacion.ingrediente.GUI_BajaIngrediente;
import com.grupoms.app.presentacion.ingrediente.GUI_ListarIngrediente;
import com.grupoms.app.presentacion.ingrediente.GUI_ListarIngredienteProducto;
import com.grupoms.app.presentacion.ingrediente.GUI_ListarIngredienteProveedor;
import com.grupoms.app.presentacion.ingrediente.GUI_ModificarIngrediente;
import com.grupoms.app.presentacion.ingrediente.GUI_MostrarIngrediente;
import com.grupoms.app.presentacion.mesa.GUI_AltaMesa;
import com.grupoms.app.presentacion.mesa.GUI_BajaMesa;
import com.grupoms.app.presentacion.mesa.GUI_ListarMesa;
import com.grupoms.app.presentacion.mesa.GUI_ModificarMesa;
import com.grupoms.app.presentacion.mesa.GUI_MostrarMesa;
import com.grupoms.app.presentacion.pedido.GUI_AltaPedido;
import com.grupoms.app.presentacion.pedido.GUI_DevolverPedido;
import com.grupoms.app.presentacion.pedido.GUI_ListarPedido;
import com.grupoms.app.presentacion.pedido.GUI_ModificarPedido;
import com.grupoms.app.presentacion.pedido.GUI_MostrarPedido;
import com.grupoms.app.presentacion.producto.GUI_AltaProducto;
import com.grupoms.app.presentacion.producto.GUI_BajaProducto;
import com.grupoms.app.presentacion.producto.GUI_ListarProducto;
import com.grupoms.app.presentacion.producto.GUI_ModificarProducto;
import com.grupoms.app.presentacion.producto.GUI_MostrarProducto;
import com.grupoms.app.presentacion.proveedor.GUI_AltaProveedor;
import com.grupoms.app.presentacion.proveedor.GUI_BajaProveedor;
import com.grupoms.app.presentacion.proveedor.GUI_ListarProveedores;
import com.grupoms.app.presentacion.proveedor.GUI_ModificarProveedor;
import com.grupoms.app.presentacion.proveedor.GUI_MostrarProveedor;

public class FactoriaVistas {
	
	public static final String GUI_ALTA_PEDIDO = "GUI_AltaPedido";
	public static final String GUI_MOSTRAR_PEDIDO = "GUI_MostrarPedido";
	public static final String GUI_DEVOLVER_PEDIDO = "GUI_DevolverPedido";
	public static final String GUI_CONFIRMAR_PEDIDO = "GUI_ConfirmarPedido";
	public static final String GUI_MODIFICAR_PEDIDO = "GUI_ModificarPedido";
	public static final String GUI_LISTAR_PEDIDOS = "GUI_ListarPedidos";

	public static final String GUI_ALTA_PROVEEDOR = "GUI_AltaProveedor";
	public static final String GUI_BAJA_PROVEEDOR = "GUI_BajaProveedor";
	public static final String GUI_MOSTRAR_PROVEEDOR = "GUI_VerProveedor";
	public static final String GUI_LISTAR_PROVEEDORES = "GUI_ListarProveedores";
	public static final String GUI_MODIFICAR_PROVEEDOR = "GUI_ModificarProveedor";
	
	public static final String GUI_ALTA_INGREDIENTE = "GUI_AltaIngrediente";
	public static final String GUI_BAJA_INGREDIENTE = "GUI_BajaIngrediente";
	public static final String GUI_MOSTRAR_INGREDIENTE = "GUI_VerIngrediente";
	public static final String GUI_LISTAR_INGREDIENTES = "GUI_ListarIngredientes";
	public static final String GUI_MOSTRAR_INGREDIENTES_PROVEEDOR = "GUI_MostrarIngredientesProveedor";
	public static final String GUI_MOSTRAR_INGREDIENTES_PRODUCTO = "GUI_MostrarIngredientesProducto";
	public static final String GUI_MODIFICAR_INGREDIENTE = "GUI_ModificarIngrediente";
	
	public static final String GUI_ALTA_MESA = "GUI_AltaMesa";
	public static final String GUI_BAJA_MESA = "GUI_BajaMesa";
	public static final String GUI_MOSTRAR_MESA = "GUI_VerMesa";
	public static final String GUI_LISTAR_MESAS = "GUI_ListarMesas";
	public static final String GUI_MODIFICAR_MESA = "GUI_ModificarMesa";
	
	public static final String GUI_ALTA_PRODUCTO = "GUI_AltaProducto";
	public static final String GUI_BAJA_PRODUCTO = "GUI_BajaProducto";
	public static final String GUI_MOSTRAR_PRODUCTO = "GUI_VerProducto";
	public static final String GUI_LISTAR_PRODUCTOS = "GUI_ListarProductos";
	public static final String GUI_MODIFICAR_PRODUCTO = "GUI_ModificarProducto";
	
	public static final String GUI_ALTA_EMPLEADO = "GUI_AltaEmpleado";
	public static final String GUI_BAJA_EMPLEADO = "GUI_BajaEmpleado";
	public static final String GUI_MOSTRAR_EMPLEADO = "GUI_VerEmpleado";
	public static final String GUI_LISTAR_EMPLEADOS = "GUI_ListarEmpleados";
	public static final String GUI_MODIFICAR_EMPLEADO = "GUI_ModificarEmpleado";
	
    private static FactoriaVistas instance;
    private Map<String, IGUI> vistas;

    private FactoriaVistas() {
        vistas = new HashMap<>();
        
        vistas.put(GUI_ALTA_PEDIDO, new GUI_AltaPedido());
        vistas.put(GUI_MOSTRAR_PEDIDO, new GUI_MostrarPedido());
        vistas.put(GUI_DEVOLVER_PEDIDO, new GUI_DevolverPedido());
        vistas.put(GUI_MODIFICAR_PEDIDO, new GUI_ModificarPedido());
        vistas.put(GUI_LISTAR_PEDIDOS, new GUI_ListarPedido());
        
        vistas.put(GUI_ALTA_PROVEEDOR, new GUI_AltaProveedor());
        vistas.put(GUI_BAJA_PROVEEDOR, new GUI_BajaProveedor());
        vistas.put(GUI_MOSTRAR_PROVEEDOR, new GUI_MostrarProveedor());
        vistas.put(GUI_LISTAR_PROVEEDORES, new GUI_ListarProveedores());
        vistas.put(GUI_MODIFICAR_PROVEEDOR, new GUI_ModificarProveedor());
        
        vistas.put(GUI_ALTA_INGREDIENTE, new GUI_AltaIngrediente());
        vistas.put(GUI_BAJA_INGREDIENTE, new GUI_BajaIngrediente());
        vistas.put(GUI_MOSTRAR_INGREDIENTE, new GUI_MostrarIngrediente());
        vistas.put(GUI_LISTAR_INGREDIENTES, new GUI_ListarIngrediente());
        vistas.put(GUI_MOSTRAR_INGREDIENTES_PROVEEDOR, new GUI_ListarIngredienteProveedor());
        vistas.put(GUI_MOSTRAR_INGREDIENTES_PRODUCTO, new GUI_ListarIngredienteProducto());
        vistas.put(GUI_MODIFICAR_INGREDIENTE, new GUI_ModificarIngrediente());
        
        // TODO Descomentar cuando se implemente la funcionalidad de mesa
//        vistas.put(GUI_ALTA_MESA, new GUI_AltaMesa());
//        vistas.put(GUI_BAJA_MESA, new GUI_BajaMesa());
//        vistas.put(GUI_MOSTRAR_MESA, new GUI_MostrarMesa());
//        vistas.put(GUI_LISTAR_MESAS, new GUI_ListarMesa());
//        vistas.put(GUI_MODIFICAR_MESA, new GUI_ModificarMesa());
        
        vistas.put(GUI_ALTA_PRODUCTO, new GUI_AltaProducto());
        vistas.put(GUI_BAJA_PRODUCTO, new GUI_BajaProducto());
        vistas.put(GUI_MOSTRAR_PRODUCTO, new GUI_MostrarProducto());
        vistas.put(GUI_LISTAR_PRODUCTOS, new GUI_ListarProducto());
        vistas.put(GUI_MODIFICAR_PRODUCTO, new GUI_ModificarProducto());
        
        vistas.put(GUI_ALTA_EMPLEADO, new GUI_AltaEmpleado());
        vistas.put(GUI_BAJA_EMPLEADO, new GUI_BajaEmpleado());
        vistas.put(GUI_MOSTRAR_EMPLEADO, new GUI_MostrarEmpleado());
        vistas.put(GUI_LISTAR_EMPLEADOS, new GUI_ListarEmpleado());
        vistas.put(GUI_MODIFICAR_EMPLEADO, new GUI_ModificarEmpleado());
    }

    public static FactoriaVistas getInstance() {
        if (instance == null)
            instance = new FactoriaVistas();
        return instance;
    }

    public IGUI creaVista(String nombreVista) {
    	return vistas.get(nombreVista);
    }
}
