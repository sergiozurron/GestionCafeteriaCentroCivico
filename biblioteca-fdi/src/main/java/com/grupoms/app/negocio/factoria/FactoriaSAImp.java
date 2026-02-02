package com.grupoms.app.negocio.factoria;

import com.grupoms.app.negocio.ClaseJPA.ClaseSA;
import com.grupoms.app.negocio.ClaseJPA.ClaseSAImp;
import com.grupoms.app.negocio.EjemplarJPA.EjemplarSA;
import com.grupoms.app.negocio.EjemplarJPA.EjemplarSAImp;
import com.grupoms.app.negocio.PromocionJPA.PromocionSA;
import com.grupoms.app.negocio.PromocionJPA.PromocionSAImp;
import com.grupoms.app.negocio.empleado.SAEmpleado;
import com.grupoms.app.negocio.empleado.SAEmpleadoImp;
import com.grupoms.app.negocio.ingrediente.SAIngrediente;
import com.grupoms.app.negocio.ingrediente.SAIngredienteImp;
import com.grupoms.app.negocio.materialJPA.MaterialSA;
import com.grupoms.app.negocio.materialJPA.MaterialSAImp;
import com.grupoms.app.negocio.mesa.SAMesa;
import com.grupoms.app.negocio.mesa.SAMesaImp;
import com.grupoms.app.negocio.pedido.SAOrden;
import com.grupoms.app.negocio.pedido.SALineaVentaImp;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.SAPedidoImp;
import com.grupoms.app.negocio.prestamoJPA.PrestamoSA;
import com.grupoms.app.negocio.prestamoJPA.PrestamoSAImp;
import com.grupoms.app.negocio.producto.SAProducto;
import com.grupoms.app.negocio.producto.SAProductoImp;
import com.grupoms.app.negocio.proveedor.SAProveedor;
import com.grupoms.app.negocio.proveedor.SAProveedorImpl;
import com.grupoms.app.negocio.salaJPA.SalaSA;
import com.grupoms.app.negocio.salaJPA.SalaSAImp;
import com.grupoms.app.negocio.socioJPA.SocioSA;
import com.grupoms.app.negocio.socioJPA.SocioSAImp;

public class FactoriaSAImp extends FactoriaSA {

	public SAProveedor creaSAProveedor() {
		return new SAProveedorImpl();
	}

	public SAMesa creaSAMesa() {
		return new SAMesaImp();
	}

	@Override
	public SAPedido creaSAPedido() {
		return new SAPedidoImp();
	}

	@Override
	public SAIngrediente creaSAIngrediente() {
		return new SAIngredienteImp();
	}

	@Override
	public SAProducto creaSAProducto() {
		return new SAProductoImp();
	}

	@Override
	public SAOrden creaSAOrden() {
		return new SALineaVentaImp();
	}

	@Override
	public SAEmpleado creaSAEmpleado() {
		return new SAEmpleadoImp();
	}

	@Override
	public MaterialSA creaSAMaterial() {
		return new MaterialSAImp();
	}

	public PromocionSA creaSAPromocion() {
		return new PromocionSAImp();
	}

	public EjemplarSA creaSAEjemplar() {
		return new EjemplarSAImp();
	}

	public ClaseSA creaSAClase() {
		return new ClaseSAImp();
	}

	@Override
	public SalaSA creaSASala() {

		return new SalaSAImp();
	}

	@Override
	public SocioSA creaSASocio() {
		return new SocioSAImp();
	}

	@Override
	public PrestamoSA creaSAPrestamo() {
		return new PrestamoSAImp();
	}

}
