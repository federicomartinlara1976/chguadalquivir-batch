package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import net.bounceme.chronos.chguadalquivir.model.jpa.RegistroJpa;

public interface RegistroService {
	
	List<RegistroJpa> listAll();
	
	RegistroJpa getByCode(Long code);
	
	RegistroJpa write(RegistroJpa registroJpa);
}
