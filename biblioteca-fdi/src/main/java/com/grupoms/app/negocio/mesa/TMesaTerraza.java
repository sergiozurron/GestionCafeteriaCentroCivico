package com.grupoms.app.negocio.mesa;

public class TMesaTerraza extends TMesa{
	private Double suplemento;
	private Boolean cubierta;
	
	public TMesaTerraza() {
		super("Terraza");
	}
	public Double getSuplemento() {
		return suplemento;
	}
	public void setSuplemento(Double suplemento) {
		this.suplemento = suplemento;
	}
	public Boolean getCubierta() {
		return cubierta;
	}
	public void setCubierta(Boolean cubierta) {
		this.cubierta = cubierta;
	}
	
	@Override
	public Boolean getReservada() {
		
		return null;
	}
	@Override
	public String getPrivacidad() {
		
		return null;
	}
	@Override
	public void setReservada(Boolean reservada) {
	}
	@Override
	public void setPrivacidad(String privacidad) {
	}
}
