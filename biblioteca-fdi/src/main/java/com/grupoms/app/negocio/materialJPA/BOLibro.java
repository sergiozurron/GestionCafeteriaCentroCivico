package com.grupoms.app.negocio.materialJPA;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.PrimaryKeyJoinColumn;
@Entity
@NamedQueries({})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOLibro extends BOMaterial implements Serializable {
	private static final long serialVersionUID = 0;
	
	public BOLibro(TMaterial material) {
		super(material);
	}
	
	public BOLibro() {}
	
	private String isbn;
	private String editorial;
	
	//SETTERS
	public void setISBN(String ISBN) {
		this.isbn = ISBN;
	}
		
	public void setEditorial(String editorial) {
		this.editorial=editorial;
	}
		
	//GETTERS	
	public String getISBN() {
		return isbn;
	}
		
	public String getEditorial() {
		return editorial;
	}
}
