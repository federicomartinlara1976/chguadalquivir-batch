package net.bounceme.chronos.chguadalquivir.processor;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Embalse;

/**
 * @author federico
 *
 */
@Component
public class EmbalseValidator implements Validator<Embalse> {

	@Override
	public void validate(Embalse value) throws ValidationException {
		// TODO Auto-generated method stub
		
	}
}
