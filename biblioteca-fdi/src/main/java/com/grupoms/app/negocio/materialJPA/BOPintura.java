package com.grupoms.app.negocio.materialJPA;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.PrimaryKeyJoinColumn;



@Entity
@NamedQueries({})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOPintura extends BOMaterial implements Serializable{


	private static final long serialVersionUID = 0;
	
	public BOPintura() {}
	
	public BOPintura(TMaterial material) {
		super(material);
	}
	
	private int numero;
	private Date fecha;
	

	//GETTERS
	
	public int getNumero() {
		return numero;
	}
	
	public Date getFecha() {
		return fecha;
	}
	//SETTERS
	
	public void setNumero(int numero) {
		this.numero=numero;
	}
	
	public void setFecha(Date fecha) {
		this.fecha=fecha;
	}

}
