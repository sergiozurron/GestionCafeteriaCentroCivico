package com.grupoms.app.negocio.salaJPA;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Version;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NamedQueries({
	@NamedQuery(name = "com.grpoms.app.negocio.salaJPA.BOSala.findByName", query = "SELECT s FROM BOSala s WHERE s.nombre = :nombre"),
	@NamedQuery(name = "com.grpoms.app.negocio.salaJPA.BOSala.findAll", query = "SELECT s FROM BOSala s"),
})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOSala implements Serializable {
	private static final long serialVersionUID = 0;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	protected String nombre;
	protected Integer capacidad;
	protected Boolean activo;
	
	@Version
	private int version;
	
/*	@OneToMany(mappedBy = "sala")
	private List<BOClase> clases	*/
	
	public BOSala(TSala sala) {
		this.capacidad = sala.getCapacidad();
		this.nombre = sala.getNombre();
		this.activo = sala.getActivo();
	}
	
	public BOSala() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
/*	public List<BOSala> getSalas() {
		return null;
	}	*/

}
