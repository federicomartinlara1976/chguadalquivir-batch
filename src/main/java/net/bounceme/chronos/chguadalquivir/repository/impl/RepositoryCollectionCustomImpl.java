package net.bounceme.chronos.chguadalquivir.repository.impl;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;

/**
 * @author federico
 *
 */
@Component("repositoryCollectionCustom")
public class RepositoryCollectionCustomImpl implements RepositoryCollectionCustom {

	@Getter
	@Setter
	private String collectionName;

}
