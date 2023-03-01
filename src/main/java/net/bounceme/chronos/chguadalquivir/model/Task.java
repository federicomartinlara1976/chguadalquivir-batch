package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6557119505034879817L;
	
	@NotEmpty(message = "no puede estar vacío")
	private String name;

}
