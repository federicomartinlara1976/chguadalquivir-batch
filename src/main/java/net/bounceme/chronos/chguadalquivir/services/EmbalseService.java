package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import net.bounceme.chronos.chguadalquivir.model.jpa.EmbalseJpa;

public interface EmbalseService {
	
	List<EmbalseJpa> listAll();
	
	EmbalseJpa getByCode(String code);
	
	EmbalseJpa write(EmbalseJpa embalseJpa);
}
