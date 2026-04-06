package com.grupoms.app.negocio.prestamoJPA;

public class TCalculoPrecioPromocion {

	private Integer idPromocion;
	private Integer idSocio;
	
	public TCalculoPrecioPromocion(Integer idPromocion, Integer idSocio) {
		this.idPromocion = idPromocion;
		this.idSocio = idSocio;
	}
	
	public Integer getIdPromocion() {
		return idPromocion;
	}
	
	public void setIdPromocion(Integer idPromocion) {
		this.idPromocion = idPromocion;
	}
	
	public Integer getIdSocio() {
		return idSocio;
	}
	
	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}
}
