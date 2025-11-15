package com.grupoms.app.negocio.materialJPA;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Version;

@Inheritance(strategy=InheritanceType.JOINED)
@Entity
@NamedQueries({
	@NamedQuery(name="Negocio.materialJPA.Material.findAll", query="SELECT m FROM Material m")
})
public class BOMaterial implements Serializable{
	private static final long serialVersionUID = 0;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;
	protected int tipoMaterial;
	protected String autor;
	protected Boolean activo;
	
	@Version
	private int version;

	
	public BOMaterial(TMaterial material) {
		this.autor=material.getAutor();
		this.activo=material.getActivo();
		this.id = material.getID();
		this.tipoMaterial=material.getTipoMaterial();
	}
	
	public BOMaterial() {}
	
	//SETTERS

	public void setID(Integer id) {
		this.id=id;
	}
	
	public void setAutor(String autor) {
		this.autor=autor;
	}
	
	public void setActivo(Boolean activo) {
		this.activo=activo;
	}
	public void setTipoMaterial(int tipo) {
		this.tipoMaterial=tipo;
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
		// TODO Auto-generated method stub
		return tipoMaterial;
	}
}
