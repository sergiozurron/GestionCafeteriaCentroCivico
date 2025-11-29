package com.grupoms.app.presentacion.factoria;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.ejemplarJPA.GUI_AltaEjemplar;
import com.grupoms.app.presentacion.ejemplarJPA.GUI_BajaEjemplar;
import com.grupoms.app.presentacion.ejemplarJPA.GUI_ModificarEjemplar;
import com.grupoms.app.presentacion.empleado.*;
import com.grupoms.app.presentacion.ingrediente.*;
import com.grupoms.app.presentacion.materialJPA.*;
import com.grupoms.app.presentacion.mesa.*;
import com.grupoms.app.presentacion.pedido.*;
import com.grupoms.app.presentacion.producto.*;
import com.grupoms.app.presentacion.proveedor.*;
import com.grupoms.app.presentacion.promocionJPA.*;
import com.grupoms.app.presentacion.claseJPA.*;

public class FactoriaVistas {

    // Constantes de nombres de vistas
    public static final String GUI_ALTA_PEDIDO = "GUI_AltaPedido";
    public static final String GUI_MOSTRAR_PEDIDO = "GUI_MostrarPedido";
    public static final String GUI_DEVOLVER_PEDIDO = "GUI_DevolverPedido";
    public static final String GUI_MODIFICAR_PEDIDO = "GUI_ModificarPedido";
    public static final String GUI_LISTAR_PEDIDO = "GUI_ListarPedido";

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
    public static final String GUI_LISTAR_EMPLEADOS = "GUI_ListarEmpleado";
    public static final String GUI_MODIFICAR_EMPLEADO = "GUI_ModificarEmpleado";
    public static final String GUI_ALTA_ORDEN = "GUI_AnyadirProducto";
    
    // JPA

	public static final String GUI_ALTA_MATERIAL = "GUI_AltaMaterial";
	public static final String GUI_LISTAR_MATERIAL = "GUI_ListarMaterial";
	public static final String GUI_MOSTRAR_MATERIAL = "GUI_MostrarMaterial";
	public static final String GUI_MODIFICAR_MATERIAL = "GUI_ModificarMaterial";

    public static final String GUI_ALTA_PROMOCION = "GUI_AltaPromocion";
    public static final String GUI_BAJA_PROMOCION = "GUI_BajaPromocion";
    public static final String GUI_MODIFICAR_PROMOCION = "GUI_ModificarPromocion";
    public static final String GUI_LISTAR_PROMOCION = "GUI_ListarPromocion";
    public static final String GUI_MOSTRAR_PROMOCION = "GUI_MostrarPromocion";
    public static final String GUI_VER_PROMOCIONES_POR_SOCIO = "GUI_VerPromocionesPorSocio";
    
	public static final String GUI_ALTA_EJEMPLAR = "GUI_AltaEjemplar";
	public static final String GUI_BAJA_EJEMPLAR = "GUI_BajaEjemplar";
	public static final String GUI_LISTAR_EJEMPLAR = "GUI_ListarEjemplar";
	public static final String GUI_MOSTRAR_EJEMPLAR = "GUI_MostrarEjemplar";
	public static final String GUI_MODIFICAR_EJEMPLAR = "GUI_ModificarEjemplar";

    public static final String GUI_ALTA_CLASE = "GUI_AltaClase";
	public static final String GUI_BAJA_CLASE = "GUI_BajaClase";
	public static final String GUI_LISTAR_CLASE = "GUI_ListarClase";
	public static final String GUI_MOSTRAR_CLASE = "GUI_MostrarClase";
	public static final String GUI_MODIFICAR_CLASE = "GUI_ModificarClase";

	public static final String GUI_VINCULAR_EJEMPLAR_CLASE = "GUI_VincularEjemplarClase";
	public static final String GUI_DESVINCULAR_EJEMPLAR_CLASE = "GUI_DesvincularEjemplarClase";


    // Singleton
    private static FactoriaVistas instance;
    private final Map<String, Supplier<IGUI>> vistas;

    private FactoriaVistas() {
        vistas = new HashMap<>();

        // Pedidos
        vistas.put(GUI_ALTA_PEDIDO, GUI_AltaPedido::new);
        vistas.put(GUI_MOSTRAR_PEDIDO, GUI_MostrarPedido::new);
        vistas.put(GUI_DEVOLVER_PEDIDO, GUI_DevolverPedido::new);
        vistas.put(GUI_MODIFICAR_PEDIDO, GUI_ModificarPedido::new);
        vistas.put(GUI_LISTAR_PEDIDO, GUI_ListarPedido::new);
        vistas.put(GUI_ALTA_ORDEN, GUI_AnyadirProducto::new);


        // Proveedores
        vistas.put(GUI_ALTA_PROVEEDOR, GUI_AltaProveedor::new);
        vistas.put(GUI_BAJA_PROVEEDOR, GUI_BajaProveedor::new);
        vistas.put(GUI_MOSTRAR_PROVEEDOR, GUI_MostrarProveedor::new);
        vistas.put(GUI_LISTAR_PROVEEDORES, GUI_ListarProveedores::new);
        vistas.put(GUI_MODIFICAR_PROVEEDOR, GUI_ModificarProveedor::new);

        // Ingredientes
        vistas.put(GUI_ALTA_INGREDIENTE, GUI_AltaIngrediente::new);
        vistas.put(GUI_BAJA_INGREDIENTE, GUI_BajaIngrediente::new);
        vistas.put(GUI_MOSTRAR_INGREDIENTE, GUI_MostrarIngrediente::new);
        vistas.put(GUI_LISTAR_INGREDIENTES, GUI_ListarIngrediente::new);
        vistas.put(GUI_MOSTRAR_INGREDIENTES_PROVEEDOR, GUI_ListarIngredienteProveedor::new);
        vistas.put(GUI_MOSTRAR_INGREDIENTES_PRODUCTO, GUI_ListarIngredienteProducto::new);
        vistas.put(GUI_MODIFICAR_INGREDIENTE, GUI_ModificarIngrediente::new);

        // Mesas
        vistas.put(GUI_ALTA_MESA, GUI_AltaMesa::new);
        vistas.put(GUI_BAJA_MESA, GUI_BajaMesa::new);
        vistas.put(GUI_MOSTRAR_MESA, GUI_MostrarMesa::new);
        vistas.put(GUI_LISTAR_MESAS, GUI_ListarMesa::new);
        vistas.put(GUI_MODIFICAR_MESA, GUI_ModificarMesa::new);

        // Productos
        vistas.put(GUI_ALTA_PRODUCTO, GUI_AltaProducto::new);
        vistas.put(GUI_BAJA_PRODUCTO, GUI_BajaProducto::new);
        vistas.put(GUI_MOSTRAR_PRODUCTO, GUI_MostrarProducto::new);
        vistas.put(GUI_LISTAR_PRODUCTOS, GUI_ListarProducto::new);
        vistas.put(GUI_MODIFICAR_PRODUCTO, GUI_ModificarProducto::new);

        // Empleados
        vistas.put(GUI_ALTA_EMPLEADO, GUI_AltaEmpleado::new);
        vistas.put(GUI_BAJA_EMPLEADO, GUI_BajaEmpleado::new);
        vistas.put(GUI_MOSTRAR_EMPLEADO, GUI_MostrarEmpleado::new);
        vistas.put(GUI_LISTAR_EMPLEADOS, GUI_ListarEmpleado::new);
        vistas.put(GUI_MODIFICAR_EMPLEADO, GUI_ModificarEmpleado::new);
        
        
        
        
        //----------------------------------JPA-------------------------------------------
        
        
        vistas.put(GUI_ALTA_MATERIAL, GUI_AltaMaterial::new);
        vistas.put(GUI_LISTAR_MATERIAL,GUI_ListarMaterial::new);
        vistas.put(GUI_MOSTRAR_MATERIAL,GUI_MostrarMaterial::new);
        vistas.put(GUI_MODIFICAR_MATERIAL,GUI_ModificarMaterial::new);

        vistas.put(GUI_ALTA_PROMOCION, GUI_AltaPromocion::new);
        vistas.put(GUI_BAJA_PROMOCION, GUI_BajaPromocion::new);
        vistas.put(GUI_MODIFICAR_PROMOCION, GUI_ModificarPromocion::new);
        vistas.put(GUI_LISTAR_PROMOCION, GUI_ListarPromocion::new);
        vistas.put(GUI_MOSTRAR_PROMOCION, GUI_MostrarPromocion::new);
        vistas.put(GUI_VER_PROMOCIONES_POR_SOCIO, GUI_VerPromocionesPorSocio::new);
        
        vistas.put(GUI_ALTA_EJEMPLAR, GUI_AltaEjemplar::new);
        vistas.put(GUI_BAJA_EJEMPLAR, GUI_BajaEjemplar::new);
        vistas.put(GUI_MODIFICAR_EJEMPLAR, GUI_ModificarEjemplar::new);

        vistas.put(GUI_ALTA_CLASE, GUI_AltaClase::new);
        vistas.put(GUI_BAJA_CLASE, GUI_BajaClase::new);
        vistas.put(GUI_MODIFICAR_CLASE, GUI_ModificarClase::new);
        vistas.put(GUI_LISTAR_CLASE, GUI_ListarClase::new);
        vistas.put(GUI_MOSTRAR_CLASE, GUI_MostrarClase::new);
        vistas.put(GUI_VINCULAR_EJEMPLAR_CLASE, GUI_VincularEjemplarClase::new);
        vistas.put(GUI_DESVINCULAR_EJEMPLAR_CLASE, GUI_DesvincularEjemplarClase::new);
    }

    public static synchronized FactoriaVistas getInstance() {
        if (instance == null) instance = new FactoriaVistas();
        return instance;
    }

    /**
     * Crea y devuelve una nueva instancia de la vista solicitada.
     * No mantiene referencias, para evitar efectos colaterales o bucles infinitos.
     */
    public IGUI creaVista(String nombreVista) {
        Supplier<IGUI> constructor = vistas.get(nombreVista);
        if (constructor == null) return null;
        return constructor.get(); // crea una nueva instancia cada vez
    }
}
