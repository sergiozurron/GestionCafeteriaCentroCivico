package com.grupoms.app.negocio.factoria;
//DAO
import com.grupoms.app.negocio.mesa.SAMesa;
import com.grupoms.app.negocio.proveedor.SAProveedor;
import com.grupoms.app.negocio.pedido.SAOrden;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;
import com.grupoms.app.negocio.producto.SAProducto;
import com.grupoms.app.negocio.empleado.SAEmpleado;

//JPA
import com.grupoms.app.negocio.materialJPA.MaterialSA;
import com.grupoms.app.negocio.PromocionJPA.PromocionSA;

public abstract class FactoriaSA {
	
	private static FactoriaSA instancia;
	
	public static synchronized FactoriaSA getInstance() {
		if (instancia == null) {
			instancia = new FactoriaSAImp();
		}
		return instancia;
	}
	
	public abstract SAProveedor creaSAProveedor();
	public abstract SAMesa creaSAMesa();
	public abstract SAPedido creaSAPedido();
	public abstract SAIngrediente creaSAIngrediente();
	public abstract SAProducto creaSAProducto();
    public abstract SAOrden creaSAOrden();
	public abstract SAEmpleado creaSAEmpleado();
	public abstract MaterialSA creaSAMaterial();
	public abstract PromocionSA creaSAPromocion();
}
