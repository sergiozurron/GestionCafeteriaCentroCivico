package com.grupoms.app.negocio.mesa;

public class TMesaTerraza extends TMesa{
	private Integer id;
	private Double suplemento;
	private Boolean cubierta;
	
	public TMesaTerraza() {
		super("Terraza");
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
}
