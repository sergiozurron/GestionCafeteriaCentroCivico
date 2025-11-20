package com.grupoms.app.negocio.PromocionJPA;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Version;

@Inheritance(strategy=InheritanceType.JOINED)
@Entity
@NamedQueries({
	@NamedQuery(name = "com.grupoms.app.negocio.PromocionJPA.BOPromocion.findByType", query="SELECT p FROM BOPromocion p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "com.grupoms.app.negocio.PromocionJPA.BOPromocion.findByDiscount", query="SELECT p FROM BOPromocion p WHERE p.descuento = :descuento"),
	@NamedQuery(name = "com.grupoms.app.negocio.PromocionJPA.BOPromocion.findAll", query="SELECT p FROM BOPromocion p")
})
@PrimaryKeyJoinColumn(referencedColumnName = "id")

public class BOPromocion implements Serializable{
    private static final long serialVersionUID = 0;
    
    protected Integer id;
    protected Double descuento;
    protected String tipo;
    protected boolean activo;

    @Version
	private int version;

    //@ManyToMany(mappedBy="promocion")
	//private List<BOSocio> socios;
    
    public BOPromocion(TPromocion promocion) {
        this.id = promocion.getId();
        this.descuento = promocion.getDescuento();
        this.tipo = promocion.getTipo();
        this.activo = promocion.getActivo();
    }
    
    public BOPromocion() {}
    
    //SETTERS

    public void setID(Integer id) {
        this.id=id;
    }
    
    public void setDescuento(Double descuento) {
        this.descuento=descuento;
    }

    public void setTipo(String tipo) {
        this.tipo=tipo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo=activo;
    }
    
    //GETTERS
    
    public Integer getID() {
        return this.id;
    }
    
    public Double getDescuento() {
        return this.descuento;
    }

    public String getTipo() {
        return this.tipo;
    }
    
    public Boolean getActivo() {
        return this.activo;
    }

    //public List<BOSocio> getSocios() {
		//return null;
	//}
}
