package net.bounceme.chronos.chguadalquivir.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "embalse")
public class EmbalseJpa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	@Id
	@Column(name = "codigo")
	private String codigo;

	@Column(name = "embalse")
	private String embalse;
	
	@Column(name = "capacidad")
	private Float capacidad;
	
	@Column(name = "men")
	private Float men;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cod_zona", referencedColumnName = "codigo")
	private ZonaJpa zona;
}
