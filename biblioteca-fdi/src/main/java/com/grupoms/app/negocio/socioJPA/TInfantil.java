package com.grupoms.app.negocio.socioJPA;

public class TInfantil extends TSocio{
    private Integer _reduccion;
    public TInfantil(String nombreYapellido, String dni, int tipoSocio, Integer cuota, Integer reduccion) {
        super(nombreYapellido, dni, tipoSocio, cuota);
        _reduccion=reduccion;
    }
    public TInfantil(){}

    public Integer getReduccion() {
        return _reduccion;
    }

    public void setReduccion(Integer reduccion) {
        this._reduccion = reduccion;
    }
}
