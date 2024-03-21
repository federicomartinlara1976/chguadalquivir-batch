package net.bounceme.chronos.chguadalquivir.model.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class RegistroJpaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	private Long id;

	private EmbalseJpaDTO embalse;
	
	private Float porcentaje;
	
	private Float volumen;
	
	private Float nivel;
	
	private Date fecha;
}
