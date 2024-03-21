package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import net.bounceme.chronos.chguadalquivir.model.dto.EmbalseJpaDTO;

public interface EmbalseService {
	
	List<EmbalseJpaDTO> listAll();
	
	EmbalseJpaDTO getByCode(String code);
	
	EmbalseJpaDTO write(EmbalseJpaDTO embalseJpaDTO);
}
