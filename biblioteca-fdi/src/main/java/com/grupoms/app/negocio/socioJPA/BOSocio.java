package com.grupoms.app.negocio.socioJPA;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.prestamoJPA.BOPrestamo;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Inheritance(strategy= InheritanceType.JOINED)
@Entity
@NamedQueries({
        @NamedQuery(name = "com.grupoms.app.negocio.socioJPA.BOSocio.findByName",
                query = "SELECT s FROM BOSocio s WHERE s.nombreYapellido = :nombre"),
        @NamedQuery(name="com.grupoms.app.negocio.socioJPA.BOSocio.findByType",
                query="SELECT s FROM BOSocio s WHERE s.tipoSocio = :tipo"),
        @NamedQuery(name="com.grupoms.app.negocio.socioJPA.BOSocio.findAll",
                query="SELECT s FROM BOSocio s"),
        @NamedQuery(name = "com.grupoms.app.negocio.socioJPA.BOSocio.findByPromocion",
                query = "SELECT s FROM BOScio s Join s.promocions p WHERE p.id=:idPromocion ")
})
public class BOSocio implements Serializable {

    private static final long serialVersionUID = 0;

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;
    protected String nombreYapellido;
    protected String dni;
    protected int tipoSocio; //0->Adulto     1->Infantil
    protected Integer cuota;
    protected Boolean activo;

    @Version
    private int version;



    @OneToMany(mappedBy = "socio")
    private List<BOPrestamo> socios;

    @OneToMany(mappedBy = "socio")
    private List<BOPromocion> promocions;

    public BOSocio(TSocio socio) {
        this.nombreYapellido=socio.getNombreYapellido();
        this.dni=socio.getDni();
        this.tipoSocio=socio.getTipoSocio();
        this.cuota=socio.getCuota();
        this.activo=socio.getActivo();
    }

    public BOSocio() {}

    //SETTERS
    public void setID(Integer id) {
        this.id=id;
    }
    public void setNombreYapellido(String nombreYapellido){this.nombreYapellido=nombreYapellido;}
    public void setDni(String dni){this.dni=dni;}
    public void setTipoSocio(int tipoSocio) {
        this.tipoSocio = tipoSocio;
    }
    public void setCuota(Integer cuota) {
        this.cuota = cuota;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    //GETTERS
    public Integer getId() {
        return id;
    }
    public String getNombreYapellido() {
        return nombreYapellido;
    }
    public String getDni() {
        return dni;
    }
    public int getTipoSocio() {
        return tipoSocio;
    }
    public Integer getCuota() {
        return cuota;
    }
    public Boolean getActivo() {
        return activo;
    }

    public List<BOPromocion> getPromocions(){return promocions;}

    public void setPromocions(List<BOPromocion> promocions) {
        this.promocions = promocions;
    }
}
