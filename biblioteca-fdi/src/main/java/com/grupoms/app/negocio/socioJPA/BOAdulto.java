package com.grupoms.app.negocio.socioJPA;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOAdulto extends BOSocio implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean miembroPleno;

	public BOAdulto() {
	}

	public BOAdulto(TAdulto adulto) {
		super(adulto);
		this.miembroPleno = adulto.getMiembroPleno();
	}

	public Boolean getMiembroPleno() {
		return miembroPleno;
	}

	public void setMiembroPleno(Boolean miembroPleno) {
		this.miembroPleno = miembroPleno;
	}
}