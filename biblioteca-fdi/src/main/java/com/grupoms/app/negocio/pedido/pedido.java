package com.grupoms.app.negocio.pedido;

import java.io.Serializable;
import java.lang.annotation.Inherited;
import java.sql.Date;

import com.grupoms.app.negocio.mesa.mesa;
import com.grupoms.app.negocio.empleado.empleado;
import com.grupoms.app.negocio.pedido.TPedido;

import javax.annotation.processing.SupportedSourceVersion;

public class pedido implements Serializable{
    private static final long serialVersionUI = 0;

    private Empleado empleado;
    private Mesa mesa;

    private Integer id;
    private Integer version;
    private Boolean activo;
    private Double totalFactura;
    private String estado;
    private Date fecha;

    public pedido(){}

    public pedido(TPedido pedido){
        id = pedido.getId();
        activo = pedido.getActivo();
        totalFactura=pedido.getTotal();
        estado = pedido.getEstado();
        fecha = pedido.getFecha();
    }

    public Empleado getEmpleado(){
        return empleado;
    }
    public Mesa getMesa(){
        return mesa;
    }

    public void setId(Integer id){
        this.id=id;
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
    public Integer getId(){return id;}
    

    public Double getTotal(){return totalFactura;}

    public String getEstado(){return estado;}
    public Boolean getActivo(){return activo;}
    public Date getFecha(){return fecha;}
    
    public TPedido toTransfer(){
        TPedido tPedido = new TPedido();
        tPedido.setActivo(activo);
        tPedido.setId(id);
        tPedido.setEstado(estado);
        tPedido.setFecha(fecha);
        tPedido.setIdEmpleado(empleado.getId());
        tPedido.setIdMesa(mesa.getId());
        tPedido.setTotal(totalFactura);
    }
}
