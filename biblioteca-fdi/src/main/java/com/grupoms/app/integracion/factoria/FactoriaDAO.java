package com.grupoms.app.integracion.factoria;

import com.grupoms.app.integracion.mesa.DAOMesa;
import com.grupoms.app.integracion.pedido.DAOLineaPedido;
import com.grupoms.app.integracion.pedido.DAOPedido;
import com.grupoms.app.integracion.proveedor.DAOProveedor;
import com.grupoms.app.integracion.ingrediente.DAOIngrediente;
import com.grupoms.app.integracion.producto.DAOProducto;
import com.grupoms.app.integracion.empleado.DAOEmpleado;

public abstract class FactoriaDAO {

	private static FactoriaDAO instancia;

	public static synchronized FactoriaDAO getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaDAOImp();
		}
		return instancia;
	}

	public abstract DAOProveedor creaDAOProveedor();

	public abstract DAOPedido creaDAOPedido();

	public abstract DAOEmpleado creaDAOEmpleado();

	public abstract DAOIngrediente creaDAOIngrediente();

	public abstract DAOMesa creaDAOMesa();

	public abstract DAOProducto creaDAOProducto();

	public abstract DAOLineaPedido creaDAOLineaPedido();
}
