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
	
	public BOLibro(TLibro libro) {
		super(libro);
		this.isbn = libro.getISBN();
		this.editorial=libro.getEditorial();
	}
	
	public BOLibro() {}
	
	private int isbn;
	private String editorial;
	
	//SETTERS
	public void setISBN(int ISBN) {
		this.isbn = ISBN;
	}
		
	public void setEditorial(String editorial) {
		this.editorial=editorial;
	}
		
	//GETTERS	
	public int getISBN() {
		return isbn;
	}
		
	public String getEditorial() {
		return editorial;
	}
}
