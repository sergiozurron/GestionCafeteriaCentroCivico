package com.grupoms.app.negocio.materialJPA;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@NamedQueries({})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOPintura extends BOMaterial implements Serializable {

	private static final long serialVersionUID = 0;

	public BOPintura() {
	}

	public BOPintura(TPintura pintura) {
		super(pintura);
		this.numero = pintura.numero;
		this.fecha = pintura.getFecha();
	}

	private int numero;
	private String fecha;

	public int getNumero() {
		return numero;
	}

	public String getFecha() {
		return fecha;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
