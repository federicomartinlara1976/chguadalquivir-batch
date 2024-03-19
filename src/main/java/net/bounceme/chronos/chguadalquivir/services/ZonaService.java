package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import net.bounceme.chronos.chguadalquivir.model.jpa.ZonaJpa;

public interface ZonaService {
	
	List<ZonaJpa> listAll();
	
	ZonaJpa getByCode(String code);
	
	ZonaJpa write(ZonaJpa zonaJpa);
}
