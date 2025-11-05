package com.grupoms.app.negocio.producto;

public class TBebida extends TProducto {

    Integer tamanho;

    public TBebida() {
        super("Bebida");
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public void setCalorias(Integer int1) {
        
    }

    public Integer getCalorias() {
        return null;
    }

    @Override
    public void setTiempoPreparacion(Integer int1) {
        
    }

    public Integer getTiempoPreparacion() {
        return null;
    }
}
