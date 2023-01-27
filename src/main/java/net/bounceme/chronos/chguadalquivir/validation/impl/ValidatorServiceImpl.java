package net.bounceme.chronos.chguadalquivir.validation.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import lombok.SneakyThrows;
import net.bounceme.chronos.chguadalquivir.validation.ValidatorService;

public class ValidatorServiceImpl<T> implements ValidatorService<T> {

	@Override
	@SneakyThrows(ConstraintViolationException.class)
	public void validate(T obj) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<T>> violations = validator.validate(obj);
		
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}

}
