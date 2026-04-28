package com.grupoms.app.negocio.socioJPA;

import jakarta.persistence.Entity; 
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;

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
	
	@Override
	public Integer calcularNuevaCuota(BOPromocion promocion) {
	    double nuevaCuota;

	    if (this.getMiembroPleno()) {
	        nuevaCuota = this.getCuota() - (2 * promocion.getDescuento());
	    } else {
	        nuevaCuota = this.getCuota() - promocion.getDescuento();
	    }

	    return (int) Math.round(Math.max(nuevaCuota, 0));
	}
	
}