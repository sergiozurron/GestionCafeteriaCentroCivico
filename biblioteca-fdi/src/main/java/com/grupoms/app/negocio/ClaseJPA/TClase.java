package com.grupoms.app.negocio.ClaseJPA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TClase {

	protected Integer id;
	protected String tipo;
	protected Date fechaInicio;
	protected Integer duracion;
	protected Boolean activo;
	protected Integer idSala;

	protected List<Integer> idsEjemplares;

	public TClase() {
		this.idsEjemplares = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Integer getIdSala() {
		return idSala;
	}

	public void setIdSala(Integer idSala) {
		this.idSala = idSala;
	}

	public List<Integer> getEjemplares() {
		return idsEjemplares;
	}

	public void setEjemplares(List<Integer> idsEjemplares) {
		this.idsEjemplares = idsEjemplares;
	}
}