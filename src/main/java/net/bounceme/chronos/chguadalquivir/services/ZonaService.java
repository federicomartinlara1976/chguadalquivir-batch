package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import net.bounceme.chronos.chguadalquivir.model.dto.ZonaJpaDTO;

public interface ZonaService {
	
	List<ZonaJpaDTO> listAll();
	
	ZonaJpaDTO getByCode(String code);
	
	ZonaJpaDTO write(ZonaJpaDTO zonaJpaDTO);
}
