package com.grupoms.app.integracion.factoria;

import com.grupoms.app.integracion.empleado.DAOEmpleado;
import com.grupoms.app.integracion.empleado.DAOEmpleadoImp;
import com.grupoms.app.integracion.ingrediente.DAOIngrediente;
import com.grupoms.app.integracion.ingrediente.DAOIngredienteImp;
import com.grupoms.app.integracion.mesa.DAOMesa;
import com.grupoms.app.integracion.mesa.DAOMesaImp;
import com.grupoms.app.integracion.pedido.DAOLineaVenta;
import com.grupoms.app.integracion.pedido.DAOLineaVentaImp;
import com.grupoms.app.integracion.pedido.DAOPedido;
import com.grupoms.app.integracion.pedido.DAOPedidoImp;
import com.grupoms.app.integracion.proveedor.DAOProveedor;
import com.grupoms.app.integracion.proveedor.DAOProveedorImpl;
import com.grupoms.app.integracion.producto.DAOProducto;
import com.grupoms.app.integracion.producto.DAOProductoImp;

public class FactoriaDAOImp extends FactoriaDAO {

	@Override
	public DAOProveedor creaDAOProveedor() {

		return new DAOProveedorImpl();
	}

	@Override
	public DAOPedido creaDAOPedido() {

		return new DAOPedidoImp();
	}

	@Override
	public DAOIngrediente creaDAOIngrediente() {

		return new DAOIngredienteImp();
	}

	@Override
	public DAOMesa creaDAOMesa() {

		return new DAOMesaImp();
	}

	@Override
	public DAOProducto creaDAOProducto() {

		return new DAOProductoImp();
	}

	public DAOEmpleado creaDAOEmpleado() {

		return new DAOEmpleadoImp();
	}

	@Override
	public DAOLineaVenta creaDAOLineaVenta() {

		return new DAOLineaVentaImp();
	}

}
