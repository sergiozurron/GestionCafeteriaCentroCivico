package com.grupoms.app.presentacion.factoria;

import java.util.HashMap;
import java.util.Map;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.claseJPA.*;
import com.grupoms.app.presentacion.ejemplarJPA.*;
import com.grupoms.app.presentacion.materialJPA.*;
import com.grupoms.app.presentacion.prestamoJPA.*;
import com.grupoms.app.presentacion.promocionJPA.*;
import com.grupoms.app.presentacion.salaJPA.*;
import com.grupoms.app.presentacion.socioJPA.*;

import com.grupoms.app.presentacion.pedido.*;
import com.grupoms.app.presentacion.mesa.*;
import com.grupoms.app.presentacion.proveedor.*;
import com.grupoms.app.presentacion.ingrediente.*;
import com.grupoms.app.presentacion.producto.*;
import com.grupoms.app.presentacion.empleado.*;

public class FactoriaVistas {

	public static final String GUI_ALTA_PEDIDO = "GUI_AltaPedido";
	public static final String GUI_MOSTRAR_PEDIDO = "GUI_MostrarPedido";
	public static final String GUI_CERRAR_PEDIDO = "GUI_CerrarPedido";
	public static final String GUI_DEVOLVER_PEDIDO = "GUI_DevolverPedido";
	public static final String GUI_MODIFICAR_PEDIDO = "GUI_ModificarPedido";
	public static final String GUI_MOSTRAR_LISTA_PEDIDOS = "GUI_MostrarListaPedidos";
	public static final String GUI_MOSTRAR_PEDIDOS_EMPLEADO = "GUI_MostrarPedidosEmpleado";
	public static final String GUI_MOSTRAR_PEDIDOS_MESA = "GUI_MostrarPedidosMesa";
	public static final String GUI_VINCULAR_PRODUCTO_PEDIDO = "GUI_VincularProductoPedido";
	public static final String GUI_DESVINCULAR_PRODUCTO_PEDIDO = "GUI_DesvincularProductoPedido";


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
	public static final String GUI_PRODUCTOS_POR_PROVEEDOR = "GUI_ProductosPorProveedor";

	public static final String GUI_ALTA_EMPLEADO = "GUI_AltaEmpleado";
	public static final String GUI_BAJA_EMPLEADO = "GUI_BajaEmpleado";
	public static final String GUI_MOSTRAR_EMPLEADO = "GUI_VerEmpleado";
	public static final String GUI_LISTAR_EMPLEADOS = "GUI_ListarEmpleado";
	public static final String GUI_MODIFICAR_EMPLEADO = "GUI_ModificarEmpleado";
	public static final String GUI_ALTA_ORDEN = "GUI_AnyadirProducto";

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

	public static final String GUI_ALTA_SALA = "GUI_AltaSala";
	public static final String GUI_BAJA_SALA = "GUI_BajaSala";
	public static final String GUI_LISTAR_SALA = "GUI_ListarSala";
	public static final String GUI_MOSTRAR_SALA = "GUI_MostrarSala";
	public static final String GUI_MODIFICAR_SALA = "GUI_ModificarSala";
	public static final String GUI_MOSTRAR_CLASES_POR_SALA = "GUI_MostrarClasesPorSala";


	public static final String GUI_LISTAR_EJEMPLARESMATERIAL = "GUI_ListarEjemplaresMaterial";
	public static final String GUI_BAJA_MATERIAL = "GUI_BajaMaterial";

	public static final String GUI_ALTA_SOCIO = "GUI_AltaSocio";
	public static final String GUI_BAJA_SOCIO = "GUI_BajaSocio";
	public static final String GUI_MOSTRAR_SOCIO = "GUI_MostrarSocio";
	public static final String GUI_LISTAR_SOCIOS = "GUI_ListarSocios";
	public static final String GUI_MODIFICAR_SOCIO = "GUI_ModificarSocio";

	public static final String GUI_MOSTRAR_SOCIOS_POR_PROMOCION = "GUI_MostrarSociosPorPromocion";

	public static final String GUI_VINCULAR_PROMOCION = "GUI_VincularPromocionASocio";
	public static final String GUI_DESVINCULAR_PROMOCION = "GUI_DesvincularPromocionASocio";

	public static final String GUI_ALTA_PRESTAMO = "GUI_AltaPrestamo";
	public static final String GUI_BAJA_PRESTAMO = "GUI_BajaPrestamo";
	public static final String GUI_LISTAR_PRESTAMO = "GUI_ListarPrestamo";
	public static final String GUI_MOSTRAR_PRESTAMO = "GUI_MostrarPrestamo";
	public static final String GUI_MODIFICAR_PRESTAMO = "GUI_ModificarPrestamo";

	private static FactoriaVistas instance;
	private final Map<String, IGUI> vistas;

	private FactoriaVistas() {
		vistas = new HashMap<>();

		vistas.put(GUI_ALTA_MATERIAL, new GUI_AltaMaterial());
		vistas.put(GUI_LISTAR_MATERIAL, new GUI_ListarMaterial());
		vistas.put(GUI_MOSTRAR_MATERIAL, new GUI_MostrarMaterial());
		vistas.put(GUI_MODIFICAR_MATERIAL, new GUI_ModificarMaterial());
		vistas.put(GUI_LISTAR_EJEMPLARESMATERIAL, new GUI_ListarEjemplaresMaterial());
		vistas.put(GUI_BAJA_MATERIAL, new GUI_BajaMaterial());

		vistas.put(GUI_ALTA_PROMOCION, new GUI_AltaPromocion());
		vistas.put(GUI_BAJA_PROMOCION, new GUI_BajaPromocion());
		vistas.put(GUI_MODIFICAR_PROMOCION, new GUI_ModificarPromocion());
		vistas.put(GUI_LISTAR_PROMOCION, new GUI_ListarPromocion());
		vistas.put(GUI_MOSTRAR_PROMOCION, new GUI_MostrarPromocion());
		vistas.put(GUI_VER_PROMOCIONES_POR_SOCIO, new GUI_VerPromocionesPorSocio());

		vistas.put(GUI_ALTA_EJEMPLAR, new GUI_AltaEjemplar());
		vistas.put(GUI_BAJA_EJEMPLAR, new GUI_BajaEjemplar());
		vistas.put(GUI_MODIFICAR_EJEMPLAR, new GUI_ModificarEjemplar());
		vistas.put(GUI_MOSTRAR_EJEMPLAR, new GUI_MostrarEjemplar());
		vistas.put(GUI_LISTAR_EJEMPLAR, new GUI_ListarEjemplar());

		vistas.put(GUI_ALTA_CLASE, new GUI_AltaClase());
		vistas.put(GUI_BAJA_CLASE, new GUI_BajaClase());
		vistas.put(GUI_MODIFICAR_CLASE, new GUI_ModificarClase());
		vistas.put(GUI_LISTAR_CLASE, new GUI_ListarClase());
		vistas.put(GUI_MOSTRAR_CLASE, new GUI_MostrarClase());
		vistas.put(GUI_VINCULAR_EJEMPLAR_CLASE, new GUI_VincularEjemplarClase());
		vistas.put(GUI_DESVINCULAR_EJEMPLAR_CLASE, new GUI_DesvincularEjemplarClase());

		vistas.put(GUI_ALTA_SALA, new GUI_AltaSala());
		vistas.put(GUI_BAJA_SALA, new GUI_BajaSala());
		vistas.put(GUI_MODIFICAR_SALA, new GUI_ModificarSala());
		vistas.put(GUI_LISTAR_SALA, new GUI_ListarSala());
		vistas.put(GUI_MOSTRAR_SALA, new GUI_MostrarSala());
		vistas.put(GUI_MOSTRAR_CLASES_POR_SALA, new GUI_MostrarClasesPorSala());

		vistas.put(GUI_ALTA_SOCIO, new GUI_AltaSocio());
		vistas.put(GUI_BAJA_SOCIO, new GUI_BajaSocio());
		vistas.put(GUI_MOSTRAR_SOCIO, new GUI_MostrarSocio());
		vistas.put(GUI_LISTAR_SOCIOS, new GUI_ListarSocios());
		vistas.put(GUI_MODIFICAR_SOCIO, new GUI_ModificarSocio());
		vistas.put(GUI_MOSTRAR_SOCIOS_POR_PROMOCION, new GUI_MostrarSociosPorPromocion());
		vistas.put(GUI_VINCULAR_PROMOCION, new GUI_VincularPromocionASocio());
		vistas.put(GUI_DESVINCULAR_PROMOCION, new GUI_DesvincularPromocionASocio());

		vistas.put(GUI_ALTA_PRESTAMO, new GUI_AltaPrestamo());
		vistas.put(GUI_BAJA_PRESTAMO, new GUI_BajaPrestamo());
		vistas.put(GUI_LISTAR_PRESTAMO, new GUI_ListarPrestamo());
		vistas.put(GUI_MOSTRAR_PRESTAMO, new GUI_MostrarPrestamo());
		vistas.put(GUI_MODIFICAR_PRESTAMO, new GUI_ModificarPrestamo());

		vistas.put(GUI_ALTA_PEDIDO, new GUI_AltaPedido());
		vistas.put(GUI_CERRAR_PEDIDO, new GUI_CerrarPedido());
		vistas.put(GUI_MOSTRAR_PEDIDOS_EMPLEADO, new GUI_MostrarPedidosEmpleado());
		vistas.put(GUI_MOSTRAR_PEDIDOS_MESA, new GUI_MostrarPedidosMesa());
		vistas.put(GUI_VINCULAR_PRODUCTO_PEDIDO, new GUI_VincularProductoPedido());
		vistas.put(GUI_MOSTRAR_PEDIDO, new GUI_MostrarPedido());
		vistas.put(GUI_DEVOLVER_PEDIDO, new GUI_DevolverPedido());
		vistas.put(GUI_MODIFICAR_PEDIDO, new GUI_ModificarPedido());
		vistas.put(GUI_MOSTRAR_LISTA_PEDIDOS, new GUI_MostrarListaPedidos());
		vistas.put(GUI_DESVINCULAR_PRODUCTO_PEDIDO, new GUI_DesvincularProductoPedido());

		vistas.put(GUI_ALTA_PROVEEDOR, new GUI_AltaProveedor());
		vistas.put(GUI_BAJA_PROVEEDOR, new GUI_BajaProveedor());
		vistas.put(GUI_MOSTRAR_PROVEEDOR, new GUI_MostrarProveedor());
		vistas.put(GUI_LISTAR_PROVEEDORES, new GUI_ListarProveedores());
		vistas.put(GUI_MODIFICAR_PROVEEDOR, new GUI_ModificarProveedor());

		vistas.put(GUI_ALTA_INGREDIENTE, new GUI_AltaIngrediente());
		vistas.put(GUI_BAJA_INGREDIENTE, new GUI_BajaIngrediente());
		vistas.put(GUI_MOSTRAR_INGREDIENTE, new GUI_MostrarIngrediente());
		vistas.put(GUI_LISTAR_INGREDIENTES, new GUI_ListarIngrediente());
		vistas.put(GUI_MODIFICAR_INGREDIENTE, new GUI_ModificarIngrediente());

		vistas.put(GUI_ALTA_MESA, new GUI_AltaMesa());
		vistas.put(GUI_BAJA_MESA, new GUI_BajaMesa());
		vistas.put(GUI_MOSTRAR_MESA, new GUI_MostrarMesa());
		vistas.put(GUI_LISTAR_MESAS, new GUI_ListarMesa());
		vistas.put(GUI_MODIFICAR_MESA, new GUI_ModificarMesa());

		vistas.put(GUI_ALTA_PRODUCTO, new GUI_AltaProducto());
		vistas.put(GUI_BAJA_PRODUCTO, new GUI_BajaProducto());
		vistas.put(GUI_MOSTRAR_PRODUCTO, new GUI_MostrarProducto());
		vistas.put(GUI_LISTAR_PRODUCTOS, new GUI_ListarProducto());
		vistas.put(GUI_MODIFICAR_PRODUCTO, new GUI_ModificarProducto());
		vistas.put(GUI_PRODUCTOS_POR_PROVEEDOR, new GUI_ProductosPorProveedor());

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
