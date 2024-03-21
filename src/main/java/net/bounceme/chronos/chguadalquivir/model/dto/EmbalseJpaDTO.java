package net.bounceme.chronos.chguadalquivir.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmbalseJpaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641781709734044777L;
	
	private String codigo;

	private String embalse;
	
	private Float capacidad;
	
	private Float men;
	
	private ZonaJpaDTO zona;
}
