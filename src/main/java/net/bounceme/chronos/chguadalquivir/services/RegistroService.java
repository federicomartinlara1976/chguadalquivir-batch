package net.bounceme.chronos.chguadalquivir.services;

import java.util.List;

import net.bounceme.chronos.chguadalquivir.model.dto.RegistroJpaDTO;

public interface RegistroService {
	
	List<RegistroJpaDTO> listAll();
	
	RegistroJpaDTO getByCode(Long code);
	
	RegistroJpaDTO write(RegistroJpaDTO registroJpaDTO);
}
