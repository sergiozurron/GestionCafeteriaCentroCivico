package com.grupoms.app.negocio.materialJPA;

public class TLibro extends TMaterial{


	protected String ISBN;
	protected String editorial;
	
	public TLibro(String autor, int tipoProducto, String ISBN, String editorial) {
		super(autor, tipoProducto);
		this.ISBN=ISBN;
		this.editorial=editorial;
	}
	
	//SETTERS

	
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}
	
	public void setEditorial(String editorial) {
		this.editorial=editorial;
	}
	
	//GETTERS
	
	public String getISBN() {
		return ISBN;
	}
	
	public String getEditorial() {
		return editorial;
	}
	
}
