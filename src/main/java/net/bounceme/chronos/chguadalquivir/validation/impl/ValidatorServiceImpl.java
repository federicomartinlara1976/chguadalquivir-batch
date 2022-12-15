package net.bounceme.chronos.chguadalquivir.validation.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.validation.ValidatorService;

@Service
public class ValidatorServiceImpl implements ValidatorService {

	@Override
	@SneakyThrows(ConstraintViolationException.class)
	public void validate(Embalse embalse) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<Embalse>> violations = validator.validate(embalse);
		
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}

}
