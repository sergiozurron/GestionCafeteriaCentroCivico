package com.grupoms.app.negocio.socioJPA;

public class TSocio {
	protected Integer id;
	protected String nombreYapellido;
	protected String dni;
	protected int tipoSocio;
	protected Integer cuota;
	protected Boolean activo;

	public TSocio(String nombreYapellido, String dni, int tipoSocio, Integer cuota) {
		this.nombreYapellido = nombreYapellido;
		this.dni = dni;
		this.tipoSocio = tipoSocio;
		this.cuota = cuota;
		this.activo = true;
	}

	public TSocio() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreYapellido() {
		return nombreYapellido;
	}

	public void setNombreYapellido(String nombreYapellido) {
		this.nombreYapellido = nombreYapellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getTipoSocio() {
		return tipoSocio;
	}

	public void setTipoSocio(int tipoSocio) {
		this.tipoSocio = tipoSocio;
	}

	public Integer getCuota() {
		return cuota;
	}

	public void setCuota(Integer cuota) {
		this.cuota = cuota;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}