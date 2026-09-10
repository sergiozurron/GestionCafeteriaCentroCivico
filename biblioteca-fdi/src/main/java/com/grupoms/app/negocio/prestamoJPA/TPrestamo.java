package com.grupoms.app.negocio.prestamoJPA;

import java.util.Date;

public class TPrestamo {
	private Integer idSocio;
	private Integer idEjemplar;
	private Date fechaInicial;
	private Date fechaMaxima;
	private Date fechaDevuelto;
	private Double precioMulta;

	public TPrestamo() {
	}

	public TPrestamo(Integer id, Integer idSocio, Integer idEjemplar, Date fechaInicial, Date fechaMaxima,
			Date fechaDevuelto, Double precioMulta, Boolean activo) {
		this.idSocio = idSocio;
		this.idEjemplar = idEjemplar;
		this.fechaMaxima = fechaMaxima;
		this.precioMulta = precioMulta;
	}

	public Integer getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}

	public Integer getIdEjemplar() {
		return idEjemplar;
	}

	public void setIdEjemplar(Integer idEjemplar) {
		this.idEjemplar = idEjemplar;
	}

	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	public Double getPrecioMulta() {
		return precioMulta;
	}

	public void setPrecioMulta(Double precioMulta) {
		this.precioMulta = precioMulta;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaDevuelto() {
		return fechaDevuelto;
	}

	public void setFechaDevuelto(Date fechaDevuelto) {
		this.fechaDevuelto = fechaDevuelto;
	}

}