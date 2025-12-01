package com.grupoms.app.negocio.socioJPA;

public class TAdulto extends TSocio{
    private String _miembroPleno;
    public TAdulto(String nombreYapellido, String dni, int tipoSocio, Integer cuota, String miembroPleno) {
        super(nombreYapellido, dni, tipoSocio, cuota);
        _miembroPleno=miembroPleno;
    }
    public TAdulto(){}

    public String getMiembroPleno() {
        return _miembroPleno;
    }

    public void setMiembroPleno(String miembroPleno) {
        this._miembroPleno = miembroPleno;
    }
}
