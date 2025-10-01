package net.bounceme.chronos.chguadalquivir.utils;

import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;

public class CreateRecordsTestFactory extends CreateRecordsAbstractFactory {

	@Override
	public RegistroDiarioEmbalse createRegistroDiarioEmbalse(String codigo, String embalse, Float capacidad,
			Float men) {
		RegistroDiarioEmbalse registro = new RegistroDiarioEmbalse();
		
		registro.setCodigo(codigo);
		registro.setEmbalse(embalse);
		registro.setCapacidad(capacidad);
		registro.setMEN(men);
		
		return registro;
	}

	
}
