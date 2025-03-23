package net.bounceme.chronos.chguadalquivir.support;

import org.springframework.lang.Nullable;

/**
 * @author federico
 *
 * @param <SOURCE>
 * @param <DEST>
 */
public interface ElementMapper<E, T> {
	
	/**
	 * @param element
	 * @return
	 */
	@Nullable
	T map(E element);
}
