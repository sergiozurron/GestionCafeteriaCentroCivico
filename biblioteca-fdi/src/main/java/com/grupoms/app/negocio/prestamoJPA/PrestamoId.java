package com.grupoms.app.negocio.prestamoJPA;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PrestamoId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer socio;
	private Integer ejemplar;
	private Date fechaInicial;
	
	public PrestamoId() {
		
	}
	
	public PrestamoId(Integer idSocio, Integer idEjemplar, Date fechaInicial) {
		this.socio = idSocio;
		this.ejemplar = idEjemplar;
		this.fechaInicial = fechaInicial;
	}
	
	public Integer getSocio() {
		return socio;
	}
	public void setSocio(Integer socio) {
		this.socio = socio;
	}
	public Integer getEjemplar() {
		return ejemplar;
	}
	public void setEjemplar(Integer ejemplar) {
		this.ejemplar = ejemplar;
	}
	public Date getFechaPrestamo() {
		return fechaInicial;
	}
	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaInicial = fechaPrestamo;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ejemplar, fechaInicial, socio);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrestamoId other = (PrestamoId) obj;
		return Objects.equals(ejemplar, other.ejemplar) && Objects.equals(fechaInicial, other.fechaInicial)
				&& Objects.equals(socio, other.socio);
	}
	
}
