package com.grupoms.app.presentacion.controlador.comandos;

import java.util.HashMap;
import java.util.Map;

import com.grupoms.app.presentacion.comandos.claseJPA.AltaClaseCommand;
import com.grupoms.app.presentacion.comandos.claseJPA.BajaClaseCommand;
import com.grupoms.app.presentacion.comandos.claseJPA.DesvincularEjemplarDeClaseCommand;
import com.grupoms.app.presentacion.comandos.claseJPA.ListarClasesCommand;
import com.grupoms.app.presentacion.comandos.claseJPA.ModificarClaseCommand;
import com.grupoms.app.presentacion.comandos.claseJPA.MostrarClaseCommand;
import com.grupoms.app.presentacion.comandos.claseJPA.VincularEjemplarAClaseCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.AltaEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.BajaEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.ListarEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.ListarEjemplarPrestSocioCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.ListarEjemplaresPorClaseCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.ModificarEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.MostrarEjemplarCommand;
import com.grupoms.app.presentacion.comandos.ejemplarJPA.MostrarEjemplaresPorMaterialCommand;
import com.grupoms.app.presentacion.comandos.materialJPA.AltaMaterialCommand;
import com.grupoms.app.presentacion.comandos.materialJPA.BajaMaterialCommand;
import com.grupoms.app.presentacion.comandos.materialJPA.ListarMaterialCommand;
import com.grupoms.app.presentacion.comandos.materialJPA.ModificarMaterialCommand;
import com.grupoms.app.presentacion.comandos.materialJPA.MostrarMaterialCommand;
import com.grupoms.app.presentacion.comandos.prestamoJPA.AltaPrestamoCommand;
import com.grupoms.app.presentacion.comandos.prestamoJPA.CalculoPrecioPromocionCommand;
import com.grupoms.app.presentacion.comandos.prestamoJPA.DevolverPrestamoCommand;
import com.grupoms.app.presentacion.comandos.prestamoJPA.ListarPrestamosCommand;
import com.grupoms.app.presentacion.comandos.prestamoJPA.ModificarPrestamoCommand;
import com.grupoms.app.presentacion.comandos.prestamoJPA.MostrarPrestamoCommand;
import com.grupoms.app.presentacion.comandos.promocionJPA.AltaPromocionCommand;
import com.grupoms.app.presentacion.comandos.promocionJPA.BajaPromocionCommand;
import com.grupoms.app.presentacion.comandos.promocionJPA.ListarPromocionCommand;
import com.grupoms.app.presentacion.comandos.promocionJPA.ModificarPromocionCommand;
import com.grupoms.app.presentacion.comandos.promocionJPA.MostrarPromocionCommand;
import com.grupoms.app.presentacion.comandos.promocionJPA.VerPromocionesPorSocioCommand;
import com.grupoms.app.presentacion.comandos.salaJPA.AltaSalaCommand;
import com.grupoms.app.presentacion.comandos.salaJPA.BajaSalaCommand;
import com.grupoms.app.presentacion.comandos.salaJPA.ListarSalasCommand;
import com.grupoms.app.presentacion.comandos.salaJPA.ModificarSalaCommand;
import com.grupoms.app.presentacion.comandos.salaJPA.MostrarClasesPorSalaCommand;
import com.grupoms.app.presentacion.comandos.salaJPA.MostrarSalaCommand;
import com.grupoms.app.presentacion.comandos.socioJPA.AltaSocioCommand;
import com.grupoms.app.presentacion.comandos.socioJPA.BajaSocioCommand;
import com.grupoms.app.presentacion.comandos.socioJPA.DesvincularPromocionASocioCommand;
import com.grupoms.app.presentacion.comandos.socioJPA.ListarSociosCommand;
import com.grupoms.app.presentacion.comandos.socioJPA.ModificarSocioCommand;
import com.grupoms.app.presentacion.comandos.socioJPA.MostrarSocioCommand;
import com.grupoms.app.presentacion.comandos.socioJPA.MostrarSociosPorPromocionCommand;
import com.grupoms.app.presentacion.comandos.socioJPA.VincularPromocionASocioCommand;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.empleado.AltaEmpleadoCommand;
import com.grupoms.app.presentacion.controlador.comandos.empleado.BajaEmpleadoCommand;
import com.grupoms.app.presentacion.controlador.comandos.empleado.ModificarEmpleadoCommand;
import com.grupoms.app.presentacion.controlador.comandos.empleado.MostrarEmpleadoCommand;
import com.grupoms.app.presentacion.controlador.comandos.empleado.MostrarListaEmpleadosCommand;
import com.grupoms.app.presentacion.controlador.comandos.ingrediente.AltaIngredienteCommand;
import com.grupoms.app.presentacion.controlador.comandos.ingrediente.BajaIngredienteCommand;
import com.grupoms.app.presentacion.controlador.comandos.ingrediente.ListarIngredientesPorProductoCommand;
import com.grupoms.app.presentacion.controlador.comandos.ingrediente.ListarIngredientesPorProveedorCommand;
import com.grupoms.app.presentacion.controlador.comandos.ingrediente.ModificarIngredienteCommand;
import com.grupoms.app.presentacion.controlador.comandos.ingrediente.MostrarIngredienteCommand;
import com.grupoms.app.presentacion.controlador.comandos.ingrediente.MostrarListaIngredientes;
import com.grupoms.app.presentacion.controlador.comandos.mesa.AltaMesaCommand;
import com.grupoms.app.presentacion.controlador.comandos.mesa.BajaMesaCommand;
import com.grupoms.app.presentacion.controlador.comandos.mesa.ModificarMesaCommand;
import com.grupoms.app.presentacion.controlador.comandos.mesa.MostrarListaMesasCommand;
import com.grupoms.app.presentacion.controlador.comandos.mesa.MostrarMesaCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.AltaPedidoCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.CerrarPedidoCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.DesvincularProductoPedidoCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.DevolverPedidoCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.ModificarPedidoCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.MostrarListaPedidosCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.MostrarPedidoCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.MostrarPedidosEmpleadoCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.MostrarPedidosMesaCommand;
import com.grupoms.app.presentacion.controlador.comandos.pedido.VincularProductoPedidoCommand;
import com.grupoms.app.presentacion.controlador.comandos.producto.AltaProductoCommand;
import com.grupoms.app.presentacion.controlador.comandos.producto.BajaProductoCommand;
import com.grupoms.app.presentacion.controlador.comandos.producto.DesvincularProductoIngredienteCommand;
import com.grupoms.app.presentacion.controlador.comandos.producto.ModificarProductoCommand;
import com.grupoms.app.presentacion.controlador.comandos.producto.MostrarListaProductosCommand;
import com.grupoms.app.presentacion.controlador.comandos.producto.MostrarProductoCommand;
import com.grupoms.app.presentacion.controlador.comandos.producto.MostrarProductosPorProveedorCommand;
import com.grupoms.app.presentacion.controlador.comandos.producto.VincularProductoIngredienteCommand;
import com.grupoms.app.presentacion.controlador.comandos.proveedor.AltaProveedorCommand;
import com.grupoms.app.presentacion.controlador.comandos.proveedor.BajaProveedorCommand;
import com.grupoms.app.presentacion.controlador.comandos.proveedor.ModificarProveedorCommand;
import com.grupoms.app.presentacion.controlador.comandos.proveedor.MostrarListaProveedoresCommand;
import com.grupoms.app.presentacion.controlador.comandos.proveedor.MostrarProveedorCommand;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class FactoryCommandImp extends FactoryCommand {
	private Map<Integer, Command> commands = new HashMap<>();
	private Map<Integer, String> views = new HashMap<>();

	protected FactoryCommandImp() {



		commands.put(Evento.ALTA_MESA, new AltaMesaCommand());
		commands.put(Evento.BAJA_MESA, new BajaMesaCommand());
		commands.put(Evento.MODIFICAR_MESA, new ModificarMesaCommand());
		commands.put(Evento.MOSTRAR_MESA, new MostrarMesaCommand());
		commands.put(Evento.MOSTRAR_LISTA_MESA, new MostrarListaMesasCommand());

		commands.put(Evento.ALTA_INGREDIENTE, new AltaIngredienteCommand());
		commands.put(Evento.BAJA_INGREDIENTE, new BajaIngredienteCommand());
		commands.put(Evento.MODIFICAR_INGREDIENTE, new ModificarIngredienteCommand());
		commands.put(Evento.MOSTRAR_INGREDIENTE, new MostrarIngredienteCommand());
		commands.put(Evento.MOSTRAR_INGREDIENTES, new MostrarListaIngredientes());
		commands.put(Evento.LISTAR_INGREDIENTES_POR_PRODUCTO, new ListarIngredientesPorProductoCommand());
		commands.put(Evento.LISTAR_INGREDIENTES_POR_PROVEEDOR, new ListarIngredientesPorProveedorCommand());

		commands.put(Evento.ALTA_EMPLEADO, new AltaEmpleadoCommand());
		commands.put(Evento.BAJA_EMPLEADO, new BajaEmpleadoCommand());
		commands.put(Evento.MODIFICAR_EMPLEADO, new ModificarEmpleadoCommand());
		commands.put(Evento.MOSTRAR_EMPLEADO, new MostrarEmpleadoCommand());
		commands.put(Evento.MOSTRAR_EMPLEADOS, new MostrarListaEmpleadosCommand());

		commands.put(Evento.ALTA_PRODUCTO, new AltaProductoCommand());
		commands.put(Evento.BAJA_PRODUCTO, new BajaProductoCommand());
		commands.put(Evento.MODIFICAR_PRODUCTO, new ModificarProductoCommand());
		commands.put(Evento.MOSTRAR_PRODUCTO, new MostrarProductoCommand());
		commands.put(Evento.MOSTRAR_LISTA_PRODUCTO, new MostrarListaProductosCommand());
		commands.put(Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR, new MostrarProductosPorProveedorCommand());
		commands.put(Evento.VINCULAR_PRODUCTO_INGREDIENTE,new VincularProductoIngredienteCommand());
		commands.put(Evento.DESVINCULAR_PRODUCTO_INGREDIENTE,new DesvincularProductoIngredienteCommand());


		commands.put(Evento.ALTA_PROVEEDOR, new AltaProveedorCommand());
		commands.put(Evento.BAJA_PROVEEDOR, new BajaProveedorCommand());
		commands.put(Evento.MODIFICAR_PROVEEDOR, new ModificarProveedorCommand());
		commands.put(Evento.MOSTRAR_PROVEEDOR, new MostrarProveedorCommand());
		commands.put(Evento.MOSTRAR_LISTA_PROVEEDOR, new MostrarListaProveedoresCommand());

		commands.put(Evento.ALTA_MATERIAL, new AltaMaterialCommand());
		commands.put(Evento.LISTAR_MATERIAL, new ListarMaterialCommand());
		commands.put(Evento.MOSTRAR_MATERIAL, new MostrarMaterialCommand());
		commands.put(Evento.MODIFICAR_MATERIAL, new ModificarMaterialCommand());
		commands.put(Evento.MOSTRAR_EJEMPLARMATERIAL, new MostrarEjemplaresPorMaterialCommand());
		commands.put(Evento.BAJA_MATERIAL, new BajaMaterialCommand());

		commands.put(Evento.ALTA_EJEMPLAR, new AltaEjemplarCommand());
		commands.put(Evento.BAJA_EJEMPLAR, new BajaEjemplarCommand());
		commands.put(Evento.MODIFICAR_EJEMPLAR, new ModificarEjemplarCommand());
		commands.put(Evento.MOSTRAR_EJEMPLAR, new MostrarEjemplarCommand());
		commands.put(Evento.LISTAR_EJEMPLARES, new ListarEjemplarCommand());
		commands.put(Evento.LISTAR_EJEMPLARES_CLASE, new ListarEjemplaresPorClaseCommand());
		commands.put(Evento.LISTAR_EJEMPLARES_PREST_SOCIO, new ListarEjemplarPrestSocioCommand());

		commands.put(Evento.ALTA_CLASE, new AltaClaseCommand());
		commands.put(Evento.BAJA_CLASE, new BajaClaseCommand());
		commands.put(Evento.MODIFICAR_CLASE, new ModificarClaseCommand());
		commands.put(Evento.MOSTRAR_CLASE, new MostrarClaseCommand());
		commands.put(Evento.LISTAR_CLASES, new ListarClasesCommand());
		commands.put(Evento.VINCULAR_EJEMPLAR_CLASE, new VincularEjemplarAClaseCommand());
		commands.put(Evento.DESVINCULAR_EJEMPLAR_CLASE, new DesvincularEjemplarDeClaseCommand());

		commands.put(Evento.ALTA_SALA, new AltaSalaCommand());
		commands.put(Evento.BAJA_SALA, new BajaSalaCommand());
		commands.put(Evento.MODIFICAR_SALA, new ModificarSalaCommand());
		commands.put(Evento.MOSTRAR_SALA, new MostrarSalaCommand());
		commands.put(Evento.LISTAR_SALA, new ListarSalasCommand());
		commands.put(Evento.MOSTRAR_CLASES_POR_SALA, new MostrarClasesPorSalaCommand());

		commands.put(Evento.ALTA_PRESTAMO, new AltaPrestamoCommand());
		commands.put(Evento.BAJA_PRESTAMO, new BajaPrestamoCommand());
		commands.put(Evento.DEVOLUCION_PRESTAMO, new DevolverPrestamoCommand());
		commands.put(Evento.LISTAR_PRESTAMOS, new ListarPrestamosCommand());
		commands.put(Evento.MOSTRAR_PRESTAMO, new MostrarPrestamoCommand());
		commands.put(Evento.MODIFICAR_PRESTAMO, new ModificarPrestamoCommand());
		commands.put(Evento.CALCULO_PRECIO_PROMOCION, new CalculoPrecioPromocionCommand());

		commands.put(Evento.ALTA_PROMOCION, new AltaPromocionCommand());
		commands.put(Evento.BAJA_PROMOCION, new BajaPromocionCommand());
		commands.put(Evento.MODIFICAR_PROMOCION, new ModificarPromocionCommand());
		commands.put(Evento.LISTAR_PROMOCION, new ListarPromocionCommand());
		commands.put(Evento.MOSTRAR_PROMOCION, new MostrarPromocionCommand());
		commands.put(Evento.VER_PROMOCIONES_POR_SOCIO, new VerPromocionesPorSocioCommand());

		commands.put(Evento.ALTA_SOCIO, new AltaSocioCommand());
		commands.put(Evento.BAJA_SOCIO, new BajaSocioCommand());
		commands.put(Evento.MODIFICAR_SOCIO, new ModificarSocioCommand());
		commands.put(Evento.MOSTRAR_SOCIO, new MostrarSocioCommand());
		commands.put(Evento.LISTAR_SOCIOS, new ListarSociosCommand());
		commands.put(Evento.VINCULAR_PROMOCION, new VincularPromocionASocioCommand());
		commands.put(Evento.DESVINCULAR_PROMOCION, new DesvincularPromocionASocioCommand());
		commands.put(Evento.MOSTRAR_SOCIOS_POR_PROMOCION, new MostrarSociosPorPromocionCommand());

		
		commands.put(Evento.ALTA_PEDIDO,new AltaPedidoCommand());
		commands.put(Evento.CERRAR_PEDIDO,new CerrarPedidoCommand());
		commands.put(Evento.MODIFICAR_PEDIDO,new ModificarPedidoCommand());
		commands.put(Evento.VINCULAR_PRODUCTO_PEDIDO,new VincularProductoPedidoCommand());
		commands.put(Evento.DESVINCULAR_PRODUCTO_PEDIDO,new DesvincularProductoPedidoCommand());
		commands.put(Evento.DEVOLVER_PEDIDO,new DevolverPedidoCommand());
		commands.put(Evento.MOSTRAR_PEDIDO,new MostrarPedidoCommand());
		commands.put(Evento.MOSTRAR_PEDIDOS,new MostrarListaPedidosCommand());
		commands.put(Evento.MOSTRAR_PEDIDOS_EMPLEADO,new MostrarPedidosEmpleadoCommand());
		commands.put(Evento.MOSTRAR_PEDIDOS_MESA,new MostrarPedidosMesaCommand());

		
		
		
		
		
		views.put(Evento.ALTA_PEDIDO, FactoriaVistas.GUI_ALTA_PEDIDO);
		views.put(Evento.CERRAR_PEDIDO,FactoriaVistas.GUI_CERRAR_PEDIDO);
		views.put(Evento.MOSTRAR_PEDIDO, FactoriaVistas.GUI_MOSTRAR_PEDIDO);
		views.put(Evento.MODIFICAR_PEDIDO, FactoriaVistas.GUI_MODIFICAR_PEDIDO);
		views.put(Evento.DEVOLVER_PEDIDO, FactoriaVistas.GUI_DEVOLVER_PEDIDO);
		views.put(Evento.MOSTRAR_PEDIDOS, FactoriaVistas.GUI_MOSTRAR_LISTA_PEDIDOS);
		views.put(Evento.MOSTRAR_PEDIDOS_EMPLEADO, FactoriaVistas.GUI_MOSTRAR_PEDIDOS_EMPLEADO);
		views.put(Evento.MOSTRAR_PEDIDOS_MESA, FactoriaVistas.GUI_MOSTRAR_PEDIDOS_MESA);
		views.put(Evento.MOSTRAR_PEDIDO, FactoriaVistas.GUI_MOSTRAR_PEDIDO);
		views.put(Evento.VINCULAR_PRODUCTO_PEDIDO, FactoriaVistas.GUI_VINCULAR_PRODUCTO_PEDIDO);
		views.put(Evento.DESVINCULAR_PRODUCTO_PEDIDO, FactoriaVistas.GUI_DESVINCULAR_PRODUCTO_PEDIDO);


		
		views.put(Evento.ALTA_INGREDIENTE, FactoriaVistas.GUI_ALTA_INGREDIENTE);
		views.put(Evento.BAJA_INGREDIENTE,FactoriaVistas.GUI_BAJA_INGREDIENTE);
		views.put(Evento.MODIFICAR_INGREDIENTE, FactoriaVistas.GUI_MODIFICAR_INGREDIENTE);
		views.put(Evento.MOSTRAR_INGREDIENTE, FactoriaVistas.GUI_MOSTRAR_INGREDIENTE);
		views.put(Evento.MOSTRAR_INGREDIENTES, FactoriaVistas.GUI_LISTAR_INGREDIENTES);
		views.put(Evento.LISTAR_INGREDIENTES_POR_PROVEEDOR, FactoriaVistas.GUI_LISTAR_INGREDIENTE_PROVEEDOR);
		views.put(Evento.LISTAR_INGREDIENTES_POR_PRODUCTO, FactoriaVistas.GUI_LISTAR_INGREDIENTE_PRODUCTO);

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
		views.put(Evento.MOSTRAR_PRODUCTOS_POR_PROVEEDOR,FactoriaVistas.GUI_PRODUCTOS_POR_PROVEEDOR);
		views.put(Evento.MOSTRAR_LISTA_PRODUCTO, FactoriaVistas.GUI_LISTAR_PRODUCTOS);
		views.put(Evento.VINCULAR_PRODUCTO_INGREDIENTE,FactoriaVistas.GUI_VINCULAR_PRODUCTO_INGREDIENTE);
		views.put(Evento.DESVINCULAR_PRODUCTO_INGREDIENTE,FactoriaVistas.GUI_DESVINCULAR_PRODUCTO_INGREDIENTE);


		views.put(Evento.ALTA_PROVEEDOR, FactoriaVistas.GUI_ALTA_PROVEEDOR);
		views.put(Evento.BAJA_PROVEEDOR, FactoriaVistas.GUI_BAJA_PROVEEDOR);
		views.put(Evento.MODIFICAR_PROVEEDOR, FactoriaVistas.GUI_MODIFICAR_PROVEEDOR);
		views.put(Evento.MOSTRAR_PROVEEDOR, FactoriaVistas.GUI_MOSTRAR_PROVEEDOR);
		views.put(Evento.MOSTRAR_LISTA_PROVEEDOR, FactoriaVistas.GUI_LISTAR_PROVEEDORES);

		views.put(Evento.ALTA_MATERIAL, FactoriaVistas.GUI_ALTA_MATERIAL);
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
		views.put(Evento.LISTAR_EJEMPLARES_CLASE, FactoriaVistas.GUI_LISTAR_EJEMPLARES_POR_CLASE);
		views.put(Evento.VINCULAR_EJEMPLAR_CLASE, FactoriaVistas.GUI_VINCULAR_EJEMPLAR_CLASE);
		views.put(Evento.DESVINCULAR_EJEMPLAR_CLASE, FactoriaVistas.GUI_DESVINCULAR_EJEMPLAR_CLASE);
		views.put(Evento.LISTAR_EJEMPLARES_PREST_SOCIO, FactoriaVistas.GUI_LISTAR_EJEMPLAR_PREST_SOCIO);

		views.put(Evento.ALTA_PRESTAMO, FactoriaVistas.GUI_ALTA_PRESTAMO);
		views.put(Evento.BAJA_PRESTAMO, FactoriaVistas.GUI_BAJA_PRESTAMO);
		views.put(Evento.DEVOLUCION_PRESTAMO, FactoriaVistas.GUI_DEVOLUCION_PRESTAMO);
		views.put(Evento.LISTAR_PRESTAMOS, FactoriaVistas.GUI_LISTAR_PRESTAMO);
		views.put(Evento.MOSTRAR_PRESTAMO, FactoriaVistas.GUI_MOSTRAR_PRESTAMO);
		views.put(Evento.MODIFICAR_PRESTAMO, FactoriaVistas.GUI_MODIFICAR_PRESTAMO);
		views.put(Evento.CALCULO_PRECIO_PROMOCION, FactoriaVistas.GUI_CALCULAR_PRECIO_PROMOCION);

		views.put(Evento.ALTA_SALA, FactoriaVistas.GUI_ALTA_SALA);
		views.put(Evento.BAJA_SALA, FactoriaVistas.GUI_BAJA_SALA);
		views.put(Evento.LISTAR_SALA, FactoriaVistas.GUI_LISTAR_SALA);
		views.put(Evento.MOSTRAR_SALA, FactoriaVistas.GUI_MOSTRAR_SALA);
		views.put(Evento.MODIFICAR_SALA, FactoriaVistas.GUI_MODIFICAR_SALA);
		views.put(Evento.MOSTRAR_CLASES_POR_SALA, FactoriaVistas.GUI_MOSTRAR_CLASES_POR_SALA);

		views.put(Evento.ALTA_CLASE, FactoriaVistas.GUI_ALTA_CLASE);
		views.put(Evento.BAJA_CLASE, FactoriaVistas.GUI_BAJA_CLASE);
		views.put(Evento.LISTAR_CLASES, FactoriaVistas.GUI_LISTAR_CLASE);
		views.put(Evento.MOSTRAR_CLASE, FactoriaVistas.GUI_MOSTRAR_CLASE);
		views.put(Evento.MODIFICAR_CLASE, FactoriaVistas.GUI_MODIFICAR_CLASE);
		views.put(Evento.VINCULAR_EJEMPLAR_CLASE, FactoriaVistas.GUI_VINCULAR_EJEMPLAR_CLASE);
		views.put(Evento.DESVINCULAR_EJEMPLAR_CLASE, FactoriaVistas.GUI_DESVINCULAR_EJEMPLAR_CLASE);

		views.put(Evento.ALTA_SOCIO, FactoriaVistas.GUI_ALTA_SOCIO);
		views.put(Evento.BAJA_SOCIO, FactoriaVistas.GUI_BAJA_SOCIO);
		views.put(Evento.MODIFICAR_SOCIO, FactoriaVistas.GUI_MODIFICAR_SOCIO);
		views.put(Evento.MOSTRAR_SOCIO, FactoriaVistas.GUI_MOSTRAR_SOCIO);
		views.put(Evento.LISTAR_SOCIOS, FactoriaVistas.GUI_LISTAR_SOCIOS);
		views.put(Evento.VINCULAR_PROMOCION, FactoriaVistas.GUI_VINCULAR_PROMOCION);
		views.put(Evento.DESVINCULAR_PROMOCION, FactoriaVistas.GUI_DESVINCULAR_PROMOCION);
		views.put(Evento.MOSTRAR_SOCIOS_POR_PROMOCION, FactoriaVistas.GUI_MOSTRAR_SOCIOS_POR_PROMOCION);

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
