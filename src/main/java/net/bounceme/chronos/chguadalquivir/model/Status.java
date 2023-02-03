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
public class Status implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6605836896150803431L;
	
	@Getter
	@Setter
	private String version;
	
	@Getter
	@Setter
	private String platform;
	
	@Getter
	@Setter
	private String response;
}
