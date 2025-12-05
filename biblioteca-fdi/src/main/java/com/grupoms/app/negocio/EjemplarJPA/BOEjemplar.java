package com.grupoms.app.negocio.EjemplarJPA;

import java.util.List;

import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.materialJPA.BOMaterial;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Version;

@Entity
@NamedQuery(name = "BOEjemplar.findAll", query = "SELECT e FROM BOEjemplar e")
public class BOEjemplar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String estado;
	private Boolean activo;
	@ManyToOne
	@JoinColumn(name = "material_id")
	private BOMaterial material;
	@ManyToMany(mappedBy = "ejemplares")
	private List<BOClase> clases;
	
	@Version
	private int version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public BOMaterial getMaterial() {
		return material;
	}

	public void setMaterial(BOMaterial material) {
		this.material = material;		
	}

}
