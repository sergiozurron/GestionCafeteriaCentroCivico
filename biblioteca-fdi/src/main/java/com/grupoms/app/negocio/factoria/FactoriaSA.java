package com.grupoms.app.negocio.factoria;
import com.grupoms.app.negocio.ClaseJPA.ClaseSA;
import com.grupoms.app.negocio.EjemplarJPA.EjemplarSA;
import com.grupoms.app.negocio.PromocionJPA.PromocionSA;
import com.grupoms.app.negocio.empleado.SAEmpleado;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;
//JPA
import com.grupoms.app.negocio.materialJPA.MaterialSA;
//DAO
import com.grupoms.app.negocio.mesa.SAMesa;
import com.grupoms.app.negocio.pedido.SAOrden;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.prestamoJPA.PrestamoSA;
import com.grupoms.app.negocio.producto.SAProducto;
import com.grupoms.app.negocio.proveedor.SAProveedor;
import com.grupoms.app.negocio.salaJPA.SalaSA;
import com.grupoms.app.negocio.socioJPA.SocioSA;

public abstract class FactoriaSA {
	
	private static FactoriaSA instancia;
	
	public static synchronized FactoriaSA getInstance() {
		if (instancia == null) {
			instancia = new FactoriaSAImp();
		}
		return instancia;
	}
	
	/**
	 * Permite inyectar una factoria para testing
	 */
	public static synchronized void setInstance(FactoriaSA factoria) {
		instancia = factoria;
	}

	/**
	 * Resetea la instancia singleton (útil entre tests)
	 */
	public static synchronized void resetInstance() {
		instancia = null;
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
	public abstract EjemplarSA creaSAEjemplar();
	public abstract ClaseSA creaSAClase();
	public abstract SalaSA creaSASala();
	public abstract SocioSA creaSASocio();
	public abstract PrestamoSA creaSAPrestamo();
}
