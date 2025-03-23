package net.bounceme.chronos.chguadalquivir.reader.mapping;

import java.util.Date;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.PuntoControlElement;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioPluviometria;
import net.bounceme.chronos.chguadalquivir.support.ElementMapper;

/**
 * @author federico
 *
 */
@Component
public class PuntoControlRowMapper implements ElementMapper<PuntoControlElement, RegistroDiarioPluviometria> {

	@Override
	public RegistroDiarioPluviometria map(PuntoControlElement pElement) {
		RegistroDiarioPluviometria registro = new RegistroDiarioPluviometria();
		
		// Datos generales
		registro.setFecha(new Date());
		
		String nombre = pElement.getElement().select("tr > td:eq(1)").first().text();
		registro.setNombre(nombre);
		
		String[] tokens = nombre.split(" ");
		registro.setCodigo(tokens[0]);
		
		registro.setZona(tokens[tokens.length - 1]);
		
		/* Cifras
		String sNivel = zonaElement.getElement().select("tr > td:eq(2) span").first().text();
		embalse.setNivel(Float.valueOf(sNivel.replace(",", ".")));
		
		String sCapacidad = zonaElement.getElement().select("tr > td:eq(4)").first().text();
		embalse.setCapacidad(Float.valueOf(sCapacidad.replace(",", ".")));
		
		String sVolumen = zonaElement.getElement().select("tr > td:eq(5) span").first().text();
		embalse.setVolumen(Float.valueOf(sVolumen.replace(",", ".")));
		
		String sPorcentaje = zonaElement.getElement().select("tr > td:eq(7)").first().text();
		embalse.setPorcentaje(Float.valueOf(sPorcentaje.replace(",", ".")));
		*/
		return registro;
	}

}
