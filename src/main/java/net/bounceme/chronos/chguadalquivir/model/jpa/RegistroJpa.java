package net.bounceme.chronos.chguadalquivir.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "registro_diario")
public class RegistroJpa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	@Id
	@Column(name = "id_registro")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cod_embalse", referencedColumnName = "codigo")
	private EmbalseJpa embalse;
	
	@Column(name = "porcentaje")
	private Float porcentaje;
	
	@Column(name = "volumen")
	private Float volumen;
	
	@Column(name = "nivel")
	private Float nivel;
	
	@Column(name = "fecha")
	private Date fecha;
}
