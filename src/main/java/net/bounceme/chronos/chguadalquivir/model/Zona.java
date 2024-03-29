package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "zonas")
@Data
public class Zona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5312925342306539860L;
	
	private String codigo;
	
	private String nombre;
	
	private String descripcion;

}
