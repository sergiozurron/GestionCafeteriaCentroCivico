package com.grupoms.app.negocio.mesa;

public class TMesaSala extends TMesa{
	private Boolean reservada;
	private String privacidad;
	
	public TMesaSala() {
		super("Sala");
	}

	public Boolean getReservada() {
		return reservada;
	}

	public void setReservada(Boolean reservada) {
		this.reservada = reservada;
	}

	public String getPrivacidad() {
		return privacidad;
	}

	public void setPrivacidad(String privacidad) {
		this.privacidad = privacidad;
	}

	@Override
	public Double getSuplemento() {
		
		return null;
	}

	@Override
	public Boolean getCubierta() {
		
		return null;
	}

	@Override
	public void setSuplemento(Double suplemento) {
	}

	@Override
	public void setCubierta(Boolean cubierta) {
	}
	
}

