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
		
		String zona = tokens[tokens.length - 1];
		zona = zona.substring(1, zona.length() - 1);
		registro.setZona(zona);
		
		// Cifras
		String horaActual = pElement.getElement().select("tr > td:eq(2)").first().text();
		registro.setHoraActual(Float.valueOf(horaActual.replace(",", ".")));
		
		String horaAnterior = pElement.getElement().select("tr > td:eq(3)").first().text();
		registro.setHoraAnterior(Float.valueOf(horaAnterior.replace(",", ".")));
		
		String ultimas12Horas = pElement.getElement().select("tr > td:eq(4)").first().text();
		registro.setUltimas12Horas(Float.valueOf(ultimas12Horas.replace(",", ".")));
		
		String hoy = pElement.getElement().select("tr > td:eq(5)").first().text();
		registro.setHoy(Float.valueOf(hoy.replace(",", ".")));
		
		String ayer = pElement.getElement().select("tr > td:eq(6)").first().text();
		registro.setAyer(Float.valueOf(ayer.replace(",", ".")));
		
		String unidadMedida = pElement.getElement().select("tr > td:eq(7)").first().text();
		registro.setUnidadMedida(unidadMedida);

		return registro;
	}

}
