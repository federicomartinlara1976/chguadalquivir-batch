package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6557119505034879817L;
	
	@NotEmpty(message = "no puede estar vacío")
	@Getter
	@Setter
	private String name;

}
