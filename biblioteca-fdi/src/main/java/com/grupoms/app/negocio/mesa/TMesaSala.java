package com.grupoms.app.negocio.mesa;

public class TMesaSala extends TMesa {
	private Integer id;
	private Boolean reservada;
	private String privacidad;

	public TMesaSala() {
		super("Sala");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
