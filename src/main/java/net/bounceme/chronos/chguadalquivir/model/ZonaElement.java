package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import org.jsoup.nodes.Element;

import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class ZonaElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1887374261691607915L;
	
	private Zona zona;
	
	@Transient
	private Element element;
}
