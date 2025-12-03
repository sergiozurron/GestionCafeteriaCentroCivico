package com.grupoms.app.negocio.socioJPA;

public class TAdulto extends TSocio{
    private Boolean _miembroPleno;
    public TAdulto(String nombreYapellido, String dni, int tipoSocio, Integer cuota, Boolean miembroPleno) {
        super(nombreYapellido, dni, tipoSocio, cuota);
        _miembroPleno=miembroPleno;
    }
    public TAdulto(){}

    public Boolean getMiembroPleno() {
        return _miembroPleno;
    }

    public void setMiembroPleno(Boolean miembroPleno) {
        this._miembroPleno = miembroPleno;
    }
}
