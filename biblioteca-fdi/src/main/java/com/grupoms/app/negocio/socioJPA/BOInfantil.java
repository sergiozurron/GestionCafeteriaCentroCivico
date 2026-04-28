package com.grupoms.app.negocio.socioJPA;

import java.io.Serializable;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOInfantil extends BOSocio implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double reduccion;
	private int edad;

	public BOInfantil(TInfantil infantil) {
		super(infantil);
		this.edad = infantil.getEdad();
		this.reduccion = infantil.getReduccion();
	}

	public BOInfantil() {
	}

	public int getEdad() {
		return edad;
	}

	public Double getReduccion() {
		return reduccion;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public void setReduccion(Double reduccion) {
		this.reduccion = reduccion;
	}
	
	@Override
	public Integer calcularNuevaCuota(BOPromocion promocion) {
	    double nuevaCuota = this.getCuota() -
	        (promocion.getDescuento() * (this.getReduccion() / 100.0));

	    return (int) Math.round(Math.max(nuevaCuota, 0));
	}

	@Override
	public Integer calcularNuevaCuota(BOPromocion promocion) {
		Double nuevaCuota = this.cuota - (promocion.getDescuento() * (this.reduccion / 100.0));
		return nuevaCuota.intValue();
	}

	@Override
	public TSocio toDTO() {
		TInfantil infantil = new TInfantil();
		infantil.setId(this.id);
		infantil.setNombreYapellido(this.nombreYapellido);
		infantil.setDni(this.dni);
		infantil.setTipoSocio(this.tipoSocio);
		infantil.setCuota(this.cuota);
		infantil.setActivo(this.activo);
		infantil.setEdad(this.edad);
		infantil.setReduccion(this.reduccion);
		return infantil;
	}

}
