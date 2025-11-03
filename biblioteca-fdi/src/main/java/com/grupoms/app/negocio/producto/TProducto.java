package com.grupoms.app.negocio.producto;

public class TProducto {

    // Atributos
    private Integer id;

    private String nombre;
    private Double precio;
    private Integer stock;
    private Boolean activo;

    // Getters
    public Integer getId() {return id;}
    public String getNombre() {return nombre;}
    public Double getPrecio() {return precio;}
    public Integer getStock() {return stock;}
    public Boolean getActivo() {return activo;}

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPrecio (Double precio) {
        this.precio = precio;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}