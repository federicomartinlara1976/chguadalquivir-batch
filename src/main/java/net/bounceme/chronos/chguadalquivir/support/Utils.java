package net.bounceme.chronos.chguadalquivir.support;

import java.util.Set;

import org.slf4j.Logger;

import jakarta.validation.ConstraintViolation;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

	public void logViolations(Logger log, Set<ConstraintViolation<?>> violations) {
		violations.forEach(violation -> log.error(violation.getMessage()));
	}
}
