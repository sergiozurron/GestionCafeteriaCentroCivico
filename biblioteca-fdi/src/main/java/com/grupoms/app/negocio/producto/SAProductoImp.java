package com.grupoms.app.negocio.producto;

import java.util.List;

import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.producto.DAOProducto;

public class SAProductoImp implements SAProducto{
    private DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();

    public Integer altaProducto(TProducto producto) {
        TProducto productoExistente = daoProducto.buscaPorNombre(producto.getNombre());
		if (productoExistente != null && productoExistente.getActivo()) {
			return -1;
		}
		producto.setActivo(true);
		daoProducto.crea(producto);
		return producto.getId();
    }

    public void bajaProducto(Integer id) {

    }

    public void modificarProducto(TProducto producto) {

    }

    public TProducto mostrarProducto(Integer id) {

    }

    public List<TProducto> mostrarProductos() {
        
    }
}
