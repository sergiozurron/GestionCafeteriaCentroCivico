package com.grupoms.app.negocio.materialJPA;

public class TMaterial {
	
	protected Integer id;
	protected String autor;
	protected Boolean activo;
	
	//0 -> pintura
	//1 -> libro
	protected int tipoMaterial;
	
	public TMaterial(String autor, int tipo) {
		this.autor=autor;
		this.tipoMaterial = tipo;
		this.activo=true;
	}
	
	public TMaterial() {
	}

	//SETTERS
	public void setTipoMaterial(int tipoM) {
		this.tipoMaterial= tipoM;
	}
	
	public void setID(Integer id) {
		this.id=id;
	}
	
	public void setAutor(String autor) {
		this.autor=autor;
	}
	
	public void setActivo(Boolean activo) {
		this.activo=activo;
	}
	
	//GETTERS
	public Integer getID() {
		return this.id;
	}
	
	public String getAutor() {
		return this.autor;
	}
	
	public Boolean getActivo() {
		return this.activo;
	}
	
	public int getTipoMaterial() {
		return this.tipoMaterial;
	}
}
