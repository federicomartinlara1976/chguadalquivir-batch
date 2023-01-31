package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Zona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5312925342306539860L;
	
	@Getter
	@Setter
	private String codigo;
	
	@Getter
	@Setter
	private String nombre;
	
	@Getter
	@Setter
	private String descripcion;

}
