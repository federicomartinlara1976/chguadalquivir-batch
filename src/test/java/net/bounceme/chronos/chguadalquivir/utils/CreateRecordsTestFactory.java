package net.bounceme.chronos.chguadalquivir.utils;

import static org.mockito.Mockito.mock;

import jakarta.validation.ConstraintViolation;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;

public class CreateRecordsTestFactory extends CreateRecordsAbstractFactory {

	@Override
	public RegistroDiarioEmbalse createRegistroDiarioEmbalse(String codigo, String embalse, Float capacidad,
			Float men) {
		RegistroDiarioEmbalse registro = new RegistroDiarioEmbalse();
		
		registro.setCodigo(codigo);
		registro.setEmbalse(embalse);
		registro.setCapacidad(capacidad);
		registro.setMen(men);
		
		return registro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConstraintViolation<RegistroDiarioEmbalse> createViolation() {
		ConstraintViolation<RegistroDiarioEmbalse> violation = mock(ConstraintViolation.class); 
		return violation;
	}

	
}
