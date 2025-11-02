package com.grupoms.app.integracion.factoria;

import com.grupoms.app.integracion.mesa.DAOMesa;
import com.grupoms.app.integracion.pedido.DAOPedido;
import com.grupoms.app.integracion.proveedor.DAOProveedor;

public class FactoriaDAO {

	private static FactoriaDAO instancia;
	
	public static FactoriaDAO getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaDAO();
		}
		return instancia;
	}
	
	public abstract DAOProveedor creaDAOProveedor();
	public abstract DAOPedido creaDAOPedido ();
	public abstract DAOEmpleado creaDAOEmpleado();
	public abstract DAOIngrediente creaDAOIngrediente();
	public abstract DAOMesa creaDAOMesa();	
}
