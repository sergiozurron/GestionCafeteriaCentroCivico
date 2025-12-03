package com.grupoms.app.negocio.socioJPA;

public class TInfantil extends TSocio{
    private Double _reduccion;
    private int _edad;
    public TInfantil(String nombreYapellido, String dni, int tipoSocio, Integer cuota, Double reduccion,int edad) {
        super(nombreYapellido, dni, tipoSocio, cuota);
        _reduccion=reduccion;
        _edad=edad;
    }
    public TInfantil(){}

    public int getEdad(){
        return _edad;
    }

    public void setEdad(int edad){
        this._edad=edad;
    }
    public Double getReduccion() {
        int e=this._edad;
        if(e<=3){
            _reduccion=0.50;
        }
        else if(e<=14){
            _reduccion=0.20;
        }
        else if(e<=18){
            _reduccion=0.10;
        }
        return _reduccion;
    }
    public void setReduccion(Double reduccion){
        this._reduccion=reduccion;
    }
}
