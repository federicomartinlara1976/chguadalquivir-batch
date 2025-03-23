package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.persistence.Transient;

import org.jsoup.nodes.Element;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class PuntoControlElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1887374261691607915L;
	
	private Zona zona;
	
	@Transient
	private Element element;
}
