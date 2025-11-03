package com.grupoms.app.negocio.pedido;

import java.sql.Date;

public class TPedido {
    private Integer id;

    private Double totalFactura;
    private String estado;
    private Boolean activo;
    private Integer idEmpleado;
    private Integer idMesa;
    private Date fecha;

    public Integer getId(){return id;}
    public Integer getIdEmpleado(){return idEmpleado;}
    public Integer getIdMesa(){return idMesa;}

    public Double getTotal(){return totalFactura;}

    public String getEstado(){return estado;}
    public Boolean getActivo(){return activo;}
    public Date getFecha(){return fecha;}

    public void setId(Integer id){
        this.id=id;
    }
    public void setIdEmpleado(Integer id){
        this.idEmpleado=id;
    }
    public void setIdMesa(Integer id){
        this.idMesa=id;
    }
    public void setTotal(Double total){
        this.totalFactura=total;
    }
    public void setEstado(String estado){
        this.estado=estado;
    }
    public void setActivo(Boolean activo){
        this.activo=activo;
    }
    public void setFecha(Date fecha){
        this.fecha=fecha;
    }
}
