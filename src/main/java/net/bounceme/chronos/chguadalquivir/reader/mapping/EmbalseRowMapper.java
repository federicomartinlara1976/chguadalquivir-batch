package net.bounceme.chronos.chguadalquivir.reader.mapping;

import java.util.Date;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.ZonaElement;
import net.bounceme.chronos.chguadalquivir.support.ElementMapper;

/**
 * @author federico
 *
 */
@Component
public class EmbalseRowMapper implements ElementMapper<Embalse> {

	@Override
	public Embalse map(ZonaElement zonaElement) {
		Embalse embalse = new Embalse();
		
		String info = zonaElement.getElement().select("tr > td.MaquetacionLeft").first().html();
		
		String cod = info.substring(0, 3);
		String codZona = zonaElement.getZona().getCodigo();
		
		// Datos generales
		embalse.setEmbalse(info);
		embalse.setCod_zona(codZona);
		embalse.setCodigo(codZona + "-" + cod);
		embalse.setZona(zonaElement.getZona().getDescripcion());
		embalse.setFecha(new Date());
		
		// Cifras
		String sMen = zonaElement.getElement().select("tr > td:eq(1)").first().text();
		embalse.setMEN(Float.valueOf(sMen.replace(",", ".")));
		
		String sNivel = zonaElement.getElement().select("tr > td:eq(2) span").first().text();
		embalse.setNivel(Float.valueOf(sNivel.replace(",", ".")));
		
		String sCapacidad = zonaElement.getElement().select("tr > td:eq(4)").first().text();
		embalse.setCapacidad(Float.valueOf(sCapacidad.replace(",", ".")));
		
		String sVolumen = zonaElement.getElement().select("tr > td:eq(5) span").first().text();
		embalse.setVolumen(Float.valueOf(sVolumen.replace(",", ".")));
		
		String sPorcentaje = zonaElement.getElement().select("tr > td:eq(7)").first().text();
		embalse.setPorcentaje(Float.valueOf(sPorcentaje.replace(",", ".")));
		
		return embalse;
	}

}
