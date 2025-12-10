package com.grupoms.app.negocio.materialJPA;

import java.io.Serializable;
import java.util.List;

import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Inheritance(strategy=InheritanceType.JOINED)
@Entity
@NamedQueries({
	@NamedQuery(name="com.grupoms.app.negocio.materialJPA.BOMaterial.findByType", query="SELECT m FROM BOMaterial m WHERE m.tipoMaterial = :tipo"),
	@NamedQuery(name="com.grupoms.app.negocio.materialJPA.BOMaterial.findAll", query="SELECT m FROM BOMaterial m WHERE m.activo = true"),
	@NamedQuery(name = "com.grupoms.app.negocio.materialJPA.BOMaterial.findByName", query = "SELECT m FROM BOMaterial m WHERE m.nombre = :nombre")
})
public class BOMaterial implements Serializable{
	private static final long serialVersionUID = 0;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;
	protected int tipoMaterial;
	protected String autor;
	protected Boolean activo;
	protected String nombre;
	
	@Version
	private int version;
	
	@OneToMany(mappedBy="material")
	private List<BOEjemplar> ejemplares;
	
	public BOMaterial(TMaterial material) {
		this.autor=material.getAutor();
		this.activo=material.getActivo();
		this.tipoMaterial=material.getTipoMaterial();
		this.nombre = material.getNombre();
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
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	
	public String getNombre() {
		return nombre;
	}


	public List<BOEjemplar> getEjemplares() {
		return ejemplares;
	}
}
