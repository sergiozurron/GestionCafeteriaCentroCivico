package com.grupoms.app.negocio.producto;

public abstract class TProducto {

    // Atributos
    private Integer id;

    private String nombre;
    private Double precio;
    private Integer stock;
    private Boolean activo;
    private String tipo;

    public TProducto(String tipo) {
        this.tipo = tipo;
    }

    // Getters
    public Integer getId() {return id;}
    public String getNombre() {return nombre;}
    public Double getPrecio() {return precio;}
    public Integer getStock() {return stock;}
    public Boolean getActivo() {return activo;}

    public String getTipo() {return tipo;}

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
    public void setTipo(String tipo) {this.tipo = tipo;}
}