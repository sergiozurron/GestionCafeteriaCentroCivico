package com.grupoms.app.negocio.prestamoJPA;

import java.io.Serializable;
import java.util.Date;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.socioJPA.BOSocio;
import jakarta.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name = "BOPrestamo.findActivoBySocioYEjemplar", query = "SELECT p FROM BOPrestamo p WHERE p.socio.id = :idSocio AND p.ejemplar.id = :idEjemplar AND p.activo = true AND p.fechaDevuelto IS NULL"),
		@NamedQuery(name = "BOPrestamo.findBySocio", query = "SELECT p FROM BOPrestamo p WHERE p.socio.id = :idSocio AND p.activo = true"),
		@NamedQuery(name = "BOPrestamo.findPendientesBySocio", query = "SELECT p FROM BOPrestamo p WHERE p.socio.id = :idSocio AND p.activo = true AND p.fechaDevuelto IS NULL") })
public class BOPrestamo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date fechaInicial;

	@Temporal(TemporalType.DATE)
	private Date fechaMaxima;

	@Temporal(TemporalType.DATE)
	private Date fechaDevuelto;

	private Double precioMulta;
	private Boolean activo;

	@Version
	private int version;

	@ManyToOne
	@JoinColumn(name = "socio_id")
	private BOSocio socio;

	@ManyToOne
	@JoinColumn(name = "ejemplar_id")
	private BOEjemplar ejemplar;

	public BOPrestamo() {
	}

	public BOPrestamo(BOSocio socio, BOEjemplar ejemplar, Date fechaMaxima) {
		this.socio = socio;
		this.ejemplar = ejemplar;
		this.fechaMaxima = fechaMaxima;
		this.fechaInicial = new Date();
		this.activo = true;
		this.precioMulta = 0.0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	public Date getFechaDevuelto() {
		return fechaDevuelto;
	}

	public void setFechaDevuelto(Date fechaDevuelto) {
		this.fechaDevuelto = fechaDevuelto;
	}

	public Double getPrecioMulta() {
		return precioMulta;
	}

	public void setPrecioMulta(Double precioMulta) {
		this.precioMulta = precioMulta;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public BOSocio getSocio() {
		return socio;
	}

	public void setSocio(BOSocio socio) {
		this.socio = socio;
	}

	public BOEjemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(BOEjemplar ejemplar) {
		this.ejemplar = ejemplar;
	}
}