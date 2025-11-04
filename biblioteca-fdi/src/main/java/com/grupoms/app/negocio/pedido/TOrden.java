package com.grupoms.app.negocio.pedido;

public class TOrden {

    private Integer id;
    private Integer pedido_id;
    private Integer producto_id;
    private Integer cantidad;
    private Double precio_venta;

    public Integer getId(){return id;}
    public Integer getPedidoId(){return pedido_id;}
    public Integer getProductoId(){return producto_id;}
    public Integer getCantidad(){return cantidad;}
    public Double getPrecio(){return precio_venta;}

    public void setId(int int1) {
        this.id =int1;
    }
    public void setPedidoID(int int1) {
       this.pedido_id =int1;
    }
    public void setProductID(int int1) {
       this.producto_id =int1;
    }
    public void setCantidad(int int1) {
      this.cantidad =int1;
    }
    public void setPrecioVenta(double double1) {
        // TODO Auto-generated method stub
        this.precio_venta=double1;
    }

}
