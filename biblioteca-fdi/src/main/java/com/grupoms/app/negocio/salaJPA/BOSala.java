package com.grupoms.app.negocio.salaJPA;

import java.io.Serializable;
import jakarta.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NamedQueries({
	@NamedQuery(name = "com.grupoms.app.negocio.salaJPA.BOSala.findByName", query = "SELECT s FROM BOSala s WHERE s.nombre = :nombre"),
	@NamedQuery(name = "com.grupoms.app.negocio.salaJPA.BOSala.findAll", query = "SELECT s FROM BOSala s")
})
public class BOSala implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	protected String nombre;
	protected Integer capacidad;
	protected Boolean activo;
	
	@Version
	private int version;
	
	public BOSala() {}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public Integer getCapacidad() { return capacidad; }
	public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
	public Boolean getActivo() { return activo; }
	public void setActivo(Boolean activo) { this.activo = activo; }
}