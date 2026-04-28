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

	public Integer calcularNuevaCuota(BOPromocion promocion) {
		Double nuevaCuota;
        if (this.miembroPleno) {
            nuevaCuota = this.cuota - (2 * promocion.getDescuento());
        } else {
            nuevaCuota = this.cuota - promocion.getDescuento();
        }
        return nuevaCuota.intValue();
	}

	@Override
	public TSocio toDTO() {
		TAdulto adulto = new TAdulto();
		adulto.setId(this.id);
		adulto.setNombreYapellido(this.nombreYapellido);
		adulto.setDni(this.dni);
		adulto.setTipoSocio(this.tipoSocio);
		adulto.setCuota(this.cuota);
		adulto.setActivo(this.activo);
		adulto.setMiembroPleno(this.miembroPleno);
		return adulto;
	}
}