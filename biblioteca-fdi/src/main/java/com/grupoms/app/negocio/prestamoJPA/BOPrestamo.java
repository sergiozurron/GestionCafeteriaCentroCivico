package com.grupoms.app.negocio.prestamoJPA;

import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.EjemplarJPA.EjemplarSAImp;
import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.assembler.AdultoAssembler;
import com.grupoms.app.negocio.assembler.EjemplarAssembler;
import com.grupoms.app.negocio.assembler.SocioAssembler;
import com.grupoms.app.negocio.socioJPA.BOSocio;
import com.grupoms.app.negocio.socioJPA.SocioSAImp;
import com.grupoms.app.negocio.socioJPA.TSocio;
import jakarta.persistence.*;

import java.time.LocalDate;

@Inheritance(strategy= InheritanceType.JOINED)
@Entity
@NamedQueries({
        @NamedQuery(name = "com.grupoms.app.negocio.prestamoJPA.BOPrestamo.findByIDs",
                query = "SELECT p FROM BOPrestamo p WHERE p.socio.getId() = :idSocio AND p.ejemplar.getId() = :idEjemplar"),
        @NamedQuery(name="com.grupoms.app.negocio.prestamoJPA.BOPrestamo.findAll",
                query="SELECT s FROM BOSocio s")
})
public class BOPrestamo {
    @EmbeddedId private BOPrestamoID id;
    @ManyToOne
    @MapsId private BOSocio socio;
    @ManyToOne
    @MapsId private BOEjemplar ejemplar;

    private SocioSAImp socioSA;
    private EjemplarSAImp ejemplarSA;

    private Boolean multa=false;
    private Integer precioMulta=0;
    private LocalDate fechaPrevista;
    private LocalDate fechaDevolucion;

    public BOPrestamo(TPrestamo prestamo) {
        this.socio=SocioAssembler.transferToEntity(socioSA.mostrarSocio(prestamo.getIdSocio()));
        this.ejemplar = EjemplarAssembler.transferToEntity(ejemplarSA.mostrarEjemplar(prestamo.getIdEjemplar()));
        this.multa = multa;
        this.precioMulta = precioMulta;
        this.fechaPrevista = fechaPrevista;
        this.fechaDevolucion = fechaDevolucion;
    }

    public BOPrestamo(){}

    public BOPrestamoID getId() {
        return id;
    }

    public BOSocio getSocio() {
        return socio;
    }

    public BOEjemplar getEjemplar() {
        return ejemplar;
    }

    public Boolean getMulta() {
        return multa;
    }

    public LocalDate getFechaPrevista() {
        return fechaPrevista;
    }

    public Integer getPrecioMulta() {
        return precioMulta;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setId(BOPrestamoID id) {
        this.id = id;
    }

    public void setSocio(BOSocio socio) {
        this.socio = socio;
    }

    public void setEjemplar(BOEjemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public void setMulta(Boolean multa) {
        this.multa = multa;
    }

    public void setPrecioMulta(Integer precioMulta) {
        this.precioMulta = precioMulta;
    }

    public void setFechaPrevista(LocalDate fechaPrevista) {
        this.fechaPrevista = fechaPrevista;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }



}
