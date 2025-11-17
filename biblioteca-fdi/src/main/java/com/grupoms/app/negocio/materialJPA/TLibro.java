package com.grupoms.app.negocio.materialJPA;

public class TLibro extends TMaterial{


	protected int ISBN;
	protected String editorial;
	
	public TLibro(String autor, int tipoProducto,String nombre, int ISBN, String editorial) {
		super(autor, tipoProducto,nombre);
		this.ISBN=ISBN;
		this.editorial=editorial;
	}
	public TLibro() {}
	
	//SETTERS

	
	public void setISBN(int ISBN) {
		this.ISBN = ISBN;
	}
	
	public void setEditorial(String editorial) {
		this.editorial=editorial;
	}
	
	//GETTERS
	
	public int getISBN() {
		return ISBN;
	}
	
	public String getEditorial() {
		return editorial;
	}
	
}
