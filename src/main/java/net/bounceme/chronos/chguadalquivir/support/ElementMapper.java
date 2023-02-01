package net.bounceme.chronos.chguadalquivir.support;

import org.springframework.lang.Nullable;

import net.bounceme.chronos.chguadalquivir.model.ZonaElement;

/**
 * @author federico
 *
 * @param <SOURCE>
 * @param <DEST>
 */
public interface ElementMapper<T> {
	
	/**
	 * @param element
	 * @return
	 */
	@Nullable
	T map(ZonaElement element);
}
