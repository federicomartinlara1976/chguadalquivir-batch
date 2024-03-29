package net.bounceme.chronos.chguadalquivir.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class ZonaJpaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5312925342306539860L;
	
	private String codigo;
	
	private String nombre;
	
	private String descripcion;

}
