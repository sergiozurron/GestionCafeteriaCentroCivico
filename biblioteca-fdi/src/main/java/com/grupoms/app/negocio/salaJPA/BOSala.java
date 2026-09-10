package com.grupoms.app.negocio.salaJPA;

import java.io.Serializable;
import java.util.List;

import com.grupoms.app.negocio.ClaseJPA.BOClase;

import jakarta.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NamedQueries({
		@NamedQuery(name = "com.grupoms.app.negocio.salaJPA.BOSala.findByName", query = "SELECT s FROM BOSala s WHERE s.nombre = :nombre"),
		@NamedQuery(name = "com.grupoms.app.negocio.salaJPA.BOSala.findAll", query = "SELECT s FROM BOSala s WHERE s.activo = true") })
public class BOSala implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	protected String nombre;
	protected Integer capacidad;
	protected Boolean activo;

	@OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
	private List<BOClase> clases;

	@Version
	private int version;

	public BOSala() {
	}

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

	public List<BOClase> getClases() {
		return clases;
	}

	public void setClases(List<BOClase> clases) {
		this.clases = clases;
	}
}