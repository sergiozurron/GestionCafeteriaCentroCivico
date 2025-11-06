package com.grupoms.app.negocio.producto;

public class TBebida extends TProducto {

    private Integer tamanho;

    public TBebida() {
        super("Bebida");
    }

    @Override
    public Integer getTamanho() {
        return tamanho;
    }

    @Override
    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    // Las bebidas no tienen calorías específicas
    @Override
    public Integer getCalorias() {
        return null;
    }

    @Override
    public void setCalorias(Integer calorias) {
        // No aplica para bebidas
    }

    // Las bebidas no tienen tiempo de preparación
    @Override
    public Integer getTiempoPreparacion() {
        return null;
    }

    @Override
    public void setTiempoPreparacion(Integer tiempoPreparacion) {
        // No aplica para bebidas
    }
}
