package net.bounceme.chronos.chguadalquivir.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.chguadalquivir.model.dto.RegistroJpaDTO;
import net.bounceme.chronos.chguadalquivir.model.jpa.RegistroJpa;
import net.bounceme.chronos.chguadalquivir.repository.jpa.RegistroJpaRepository;
import net.bounceme.chronos.chguadalquivir.services.RegistroService;

@Service
public class RegistroServiceImpl implements RegistroService {
	
	@Autowired
	private RegistroJpaRepository registroJpaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(value = "jpaTransactionManager", readOnly = true)
	public List<RegistroJpaDTO> listAll() {
		List<RegistroJpa> registros = registroJpaRepository.findAll();
		return CollectionUtils.isNotEmpty(registros) ? registros.stream()
				.map(registro -> modelMapper.map(registro, RegistroJpaDTO.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", readOnly = true)
	public RegistroJpaDTO getByCode(Long code) {
		Optional<RegistroJpa> oRegistroJpa = registroJpaRepository.findById(code);
		return oRegistroJpa.isPresent() ? modelMapper.map(oRegistroJpa.get(), RegistroJpaDTO.class) : null;
	}

	@Override
	@Transactional("jpaTransactionManager")
	public RegistroJpaDTO write(RegistroJpaDTO registroJpaDTO) {
		RegistroJpa registroJpa = modelMapper.map(registroJpaDTO, RegistroJpa.class);
		registroJpaRepository.save(registroJpa);
		return modelMapper.map(registroJpa, RegistroJpaDTO.class);
	}
}
