package com.grupoms.app.negocio.empleado;

public class TEmpleado {

	private Integer ID;
	private String Nombre;
	private Boolean activo;
	private String DondeAtiende;
	private Double Sueldo;

	public Integer getID() {
		return ID;
	}

	public String getNombre() {
		return Nombre;
	}

	public Boolean getActivo() {
		return activo;
	}

	public String getDondeAtiende() {
		return DondeAtiende;
	}

	public Double getSueldo() {
		return Sueldo;
	}

	public void setID(Integer id) {
		this.ID = id;
	}

	public void setNombre(String nombre) {
		this.Nombre = nombre;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public void setDondeAtiende(String dondeAtiende) {
		this.DondeAtiende = dondeAtiende;
	}

	public void setSueldo(Double sueldo) {
		this.Sueldo = sueldo;
	}
}
