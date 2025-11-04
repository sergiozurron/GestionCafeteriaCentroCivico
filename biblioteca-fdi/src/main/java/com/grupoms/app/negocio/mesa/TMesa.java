package com.grupoms.app.negocio.mesa;

public class TMesa {
	private Integer id;
	private String ubicacion;
	private Integer numero;
	private Boolean activo;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
	
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

    public void setCapacidad(int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCapacidad'");
    }

    public void setSala(TSala sala) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSala'");
    }

    public void setTerraza(TTerraza terraza) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTerraza'");
    }
	
}
