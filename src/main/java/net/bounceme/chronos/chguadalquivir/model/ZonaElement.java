package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import org.jsoup.nodes.Element;

import lombok.Data;

@Data
public class ZonaElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1887374261691607915L;
	
	private Zona zona;
	private transient Element element;
}
