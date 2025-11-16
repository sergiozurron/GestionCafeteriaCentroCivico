package com.grupoms.app.negocio.materialJPA;

public class TMaterial {
	
	protected Integer id;
	protected String autor;
	protected Boolean activo;
	protected String nombre;
	
	//0 -> pintura
	//1 -> libro
	protected int tipoMaterial;
	
	public TMaterial(String autor, int tipo, String nombre) {
		this.autor=autor;
		this.tipoMaterial = tipo;
		this.activo=true;
		this.nombre=nombre;
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
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
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
	
	public String getNombre() {
		return this.nombre;
	}
}
