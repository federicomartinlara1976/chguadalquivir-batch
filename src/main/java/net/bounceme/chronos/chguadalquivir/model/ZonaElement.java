package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import org.jsoup.nodes.Element;

import lombok.Getter;
import lombok.Setter;

public class ZonaElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1887374261691607915L;
	
	@Getter
	@Setter
	private Zona zona;
	
	@Getter
	@Setter
	private transient Element element;
}
