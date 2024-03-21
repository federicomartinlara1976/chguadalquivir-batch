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

import net.bounceme.chronos.chguadalquivir.model.dto.ZonaJpaDTO;
import net.bounceme.chronos.chguadalquivir.model.jpa.ZonaJpa;
import net.bounceme.chronos.chguadalquivir.repository.jpa.ZonaJpaRepository;
import net.bounceme.chronos.chguadalquivir.services.ZonaService;

@Service
public class ZonaServiceImpl implements ZonaService {
	
	@Autowired
	private ZonaJpaRepository zonaJpaRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional(value = "jpaTransactionManager", readOnly = true)
	public List<ZonaJpaDTO> listAll() {
		List<ZonaJpa> zonas = zonaJpaRepository.findAll();
		return CollectionUtils.isNotEmpty(zonas) ? zonas.stream()
				.map(zona -> modelMapper.map(zona, ZonaJpaDTO.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", readOnly = true)
	public ZonaJpaDTO getByCode(String code) {
		Optional<ZonaJpa> oZonaJpa = zonaJpaRepository.findById(code);
		return oZonaJpa.isPresent() ? modelMapper.map(oZonaJpa.get(), ZonaJpaDTO.class) : null;
	}

	@Override
	@Transactional("jpaTransactionManager")
	public ZonaJpaDTO write(ZonaJpaDTO zonaJpaDTO) {
		ZonaJpa zonaJpa = modelMapper.map(zonaJpaDTO, ZonaJpa.class);
		zonaJpaRepository.save(zonaJpa);
		return modelMapper.map(zonaJpa, ZonaJpaDTO.class);
	}

}
