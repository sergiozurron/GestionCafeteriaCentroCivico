package com.grupoms.app.negocio.mesa;

public abstract class TMesa {
	private Integer id;
	private String ubicacion;
	private Integer numero, capacidad;
	private Boolean activo;
	private String tipo;
	
	public TMesa(String tipo){
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
	
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Integer getCapacidad() {
		return capacidad;
	}
	
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	
	public abstract Double getSuplemento();
	public abstract void setSuplemento(Double suplemento);
	public abstract Boolean getCubierta();
	public abstract void setCubierta(Boolean cubierta);
	
	public abstract Boolean getReservada();
	public abstract void setReservada(Boolean reservada);
	public abstract String getPrivacidad();
	public abstract void setPrivacidad(String privacidad);
}
