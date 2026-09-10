package com.grupoms.app.negocio.producto;

public class TEntradaReceta {
	
	private int productoID; 
	private int ingredienteID; 
	private Boolean activo;
	
	public int getProductoID() { 
		return productoID; 
	}
	public void setProductoID(int productoID) { 
		this.productoID = productoID; 
	} 
	
	public int getIngredienteID() { 
		return ingredienteID; 
	} 
	
	public void setIngredienteID(int ingredienteID) { 
		this.ingredienteID = ingredienteID;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	
	public void setActivo(Boolean a) {
		this.activo = a;
	}

}
