package com.grupoms.app.negocio.socioJPA;

public class TAdulto extends TSocio {
    
    private Boolean miembroPleno;

    public TAdulto(String nombreYapellido, String dni, int tipoSocio, Integer cuota, Boolean miembroPleno) {
        super(nombreYapellido, dni, tipoSocio, cuota);
        this.miembroPleno = miembroPleno;
    }

    public TAdulto() {}

    public Boolean getMiembroPleno() {
        return miembroPleno;
    }

    public void setMiembroPleno(Boolean miembroPleno) {
        this.miembroPleno = miembroPleno;
    }
}