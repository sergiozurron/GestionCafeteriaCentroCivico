package com.grupoms.app.integracion.factoria;

import com.grupoms.app.integracion.ingrediente.DAOIngrediente;
import com.grupoms.app.integracion.ingrediente.DAOIngredienteImp;
import com.grupoms.app.integracion.mesa.DAOMesa;
import com.grupoms.app.integracion.mesa.DAOMesaImp;
import com.grupoms.app.integracion.pedido.DAOOrden;
import com.grupoms.app.integracion.pedido.DAOOrdenImp;
import com.grupoms.app.integracion.pedido.DAOPedido;
import com.grupoms.app.integracion.pedido.DAOPedidoImp;
import com.grupoms.app.integracion.proveedor.DAOProveedor;
import com.grupoms.app.integracion.proveedor.DAOProveedorImpl;
import com.grupoms.app.integracion.producto.DAOProducto;
import com.grupoms.app.integracion.producto.DAOProductoImp;

public class FactoriaDAOImp extends FactoriaDAO{

    @Override
    public DAOProveedor creaDAOProveedor() {
        // TODO Auto-generated method stub
       return new DAOProveedorImpl();
    }

    @Override
    public DAOPedido creaDAOPedido() {
        // TODO Auto-generated method stub
        return new DAOPedidoImp();
    }

    @Override
    public DAOIngrediente creaDAOIngrediente() {
        // TODO Auto-generated method stub
        return new DAOIngredienteImp();
    }

    @Override
    public DAOMesa creaDAOMesa() {
        // TODO Auto-generated method stub
        return new DAOMesaImp();
    }

    @Override
    public DAOProducto creaDAOProducto() {
        // TODO Auto-generated method stub
        return new DAOProductoImp();
    }

    @Override
    public DAOOrden creaDAOOrden() {
        // TODO Auto-generated method stub
        return new DAOOrdenImp();
    }
    
}
