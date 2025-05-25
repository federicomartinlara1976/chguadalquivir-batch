package net.bounceme.chronos.chguadalquivir.reader.mapping;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.PuntoControl;
import net.bounceme.chronos.chguadalquivir.model.PuntoControlElement;
import net.bounceme.chronos.chguadalquivir.support.ElementMapper;

/**
 * @author federico
 *
 */
@Component
public class PuntoControlRowMapper implements ElementMapper<PuntoControlElement, PuntoControl> {

	@Override
	public PuntoControl map(PuntoControlElement pElement) {
		PuntoControl registro = new PuntoControl();
		
		String nombre = pElement.getElement().select("tr > td:eq(1)").first().text();
		registro.setNombre(nombre);
		
		String[] tokens = nombre.split(" ");
		registro.setId(tokens[0]);
		
		String zona = tokens[tokens.length - 1];
		zona = zona.substring(1, zona.length() - 1);
		registro.setZona(zona);

		return registro;
	}

}
