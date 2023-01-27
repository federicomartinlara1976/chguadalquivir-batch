package net.bounceme.chronos.chguadalquivir.validation;

/**
 * @author federico
 *
 */
public interface ValidatorService<T> {
	
	/**
	 * @param T
	 */
	void validate(T obj);
}
