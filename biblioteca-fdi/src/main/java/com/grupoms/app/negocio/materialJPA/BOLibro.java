package com.grupoms.app.negocio.materialJPA;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@NamedQueries({	@NamedQuery(name = "com.grupoms.app.negocio.materialJPA.BOLibro.findByISBN", query = "SELECT l FROM BOLibro l WHERE l.isbn = :isbn")})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOLibro extends BOMaterial implements Serializable {

	private static final long serialVersionUID = 0;

	public BOLibro(TLibro libro) {
		super(libro);
		this.isbn = libro.getISBN();
		this.editorial = libro.getEditorial();
	}

	public BOLibro() {
	}

	private int isbn;
	private String editorial;

	public void setISBN(int ISBN) {
		this.isbn = ISBN;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getISBN() {
		return isbn;
	}

	public String getEditorial() {
		return editorial;
	}
}
