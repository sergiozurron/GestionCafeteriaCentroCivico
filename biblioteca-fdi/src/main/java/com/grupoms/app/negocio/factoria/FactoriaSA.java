package com.grupoms.app.negocio.factoria;

import com.grupoms.app.negocio.ClaseJPA.ClaseSA;
import com.grupoms.app.negocio.EjemplarJPA.EjemplarSA;
import com.grupoms.app.negocio.PromocionJPA.PromocionSA;
import com.grupoms.app.negocio.empleado.SAEmpleado;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;

import com.grupoms.app.negocio.materialJPA.MaterialSA;

import com.grupoms.app.negocio.mesa.SAMesa;
import com.grupoms.app.negocio.pedido.SALineaPedido;
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

	public static synchronized void setInstance(FactoriaSA factoria) {
		instancia = factoria;
	}

	public static synchronized void resetInstance() {
		instancia = null;
	}

	public abstract SAProveedor creaSAProveedor();

	public abstract SAMesa creaSAMesa();

	public abstract SAPedido creaSAPedido();
	
	public abstract SALineaPedido creaSALineaPedido();

	public abstract SAIngrediente creaSAIngrediente();

	public abstract SAProducto creaSAProducto();

	public abstract SAEmpleado creaSAEmpleado();

	public abstract MaterialSA creaSAMaterial();

	public abstract PromocionSA creaSAPromocion();

	public abstract EjemplarSA creaSAEjemplar();

	public abstract ClaseSA creaSAClase();

	public abstract SalaSA creaSASala();

	public abstract SocioSA creaSASocio();

	public abstract PrestamoSA creaSAPrestamo();
}
