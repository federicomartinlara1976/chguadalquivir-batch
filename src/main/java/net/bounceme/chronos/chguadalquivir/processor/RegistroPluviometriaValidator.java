package net.bounceme.chronos.chguadalquivir.processor;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioPluviometria;
import net.bounceme.chronos.chguadalquivir.validation.ValidatorService;

/**
 * @author federico
 *
 */
@Component
@Slf4j
public class RegistroPluviometriaValidator implements Validator<RegistroDiarioPluviometria> {
	
	@Autowired
	private ValidatorService<RegistroDiarioPluviometria> validatorService;

	@Override
	public void validate(RegistroDiarioPluviometria value) {
		try {
			validatorService.validate(value);
		} catch (ConstraintViolationException e) {
			for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
				log.error(violation.getMessage());
			}
			throw new ValidationException(String.format("El registro [%s] no se va a procesar", value.getCodigo()));
		}
	}
}
