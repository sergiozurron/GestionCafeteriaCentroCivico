package com.grupoms.app.negocio.socioJPA;

public class TSocio {
    protected Integer id;
    protected String _nombreYapellido;
    protected String _dni;
    protected int _tipoSocio; //0->Adulto     1->Infantil
    protected Integer _cuota;
    protected Boolean _activo;

    public TSocio(String nombreYapellido,String dni,int tipoSocio,Integer cuota){
        _nombreYapellido=nombreYapellido;
        _dni=dni;
        _tipoSocio=tipoSocio;
        _cuota=cuota;
        _activo=true;
    }

    public TSocio(){}

    public Integer getId() {
        return id;
    }
    public String getNombreYapellido() {
        return _nombreYapellido;
    }
    public String getDni() {
        return _dni;
    }
    public int getTipoSocio() {
        return _tipoSocio;
    }
    public Integer getCuota() {
        return _cuota;
    }
    public Boolean getActivo() {
        return _activo;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setNombreYapellido(String nombreYapellido) {
        this._nombreYapellido = nombreYapellido;
    }
    public void setDni(String dni) {
        this._dni = dni;
    }
    public void setTipoSocio(int tipoSocio) {
        this._tipoSocio = tipoSocio;
    }
    public void setCuota(Integer cuota) {
        this._cuota = cuota;
    }
    public void setActivo(Boolean activo) {
        this._activo = activo;
    }
}
