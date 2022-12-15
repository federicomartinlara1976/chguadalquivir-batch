package net.bounceme.chronos.chguadalquivir.processor;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.validation.ValidatorService;

/**
 * @author federico
 *
 */
@Component
@Slf4j
public class EmbalseValidator implements Validator<Embalse> {
	
	@Autowired
	private ValidatorService validatorService;

	@Override
	public void validate(Embalse value) throws ValidationException {
		try {
			validatorService.validate(value);
		} catch (ConstraintViolationException e) {
			for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
				log.error(violation.getMessage());
			}
			throw new ValidationException(String.format("El embalse [%s] no se va a procesar", value.getCodigo()));
		}
	}
}
