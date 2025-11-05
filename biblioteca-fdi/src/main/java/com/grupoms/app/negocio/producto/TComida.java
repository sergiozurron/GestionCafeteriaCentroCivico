package com.grupoms.app.negocio.producto;

public class TComida extends TProducto {

    Integer calorias;
    Integer tiempoPreparacion;

    public TComida() {
        super("Comida");
    }

    public Integer getCalorias() {
        return calorias;
    }

    public Integer getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public void setTiempoPreparacion(Integer tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Override
    public void setTamanho(Integer int1) {
        
    }

    public Integer getTamanho() {
        return null;
    }
}
