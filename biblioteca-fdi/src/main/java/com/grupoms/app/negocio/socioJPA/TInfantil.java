package com.grupoms.app.negocio.socioJPA;

public class TInfantil extends TSocio {

	private Double reduccion;
	private int edad;

	public TInfantil(String nombreYapellido, String dni, int tipoSocio, Integer cuota, Double reduccion, int edad) {
		super(nombreYapellido, dni, tipoSocio, cuota);
		this.reduccion = reduccion;
		this.edad = edad;
	}

	public TInfantil() {
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Double getReduccion() {
		int e = this.edad;
		if (e <= 3) {
			reduccion = 0.50;
		} else if (e <= 14) {
			reduccion = 0.20;
		} else if (e <= 18) {
			reduccion = 0.10;
		} else {
			reduccion = 0.0;
		}
		return reduccion;
	}

	public void setReduccion(Double reduccion) {
		this.reduccion = reduccion;
	}
}
