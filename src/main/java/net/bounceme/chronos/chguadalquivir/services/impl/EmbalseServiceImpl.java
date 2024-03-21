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

import net.bounceme.chronos.chguadalquivir.model.dto.EmbalseJpaDTO;
import net.bounceme.chronos.chguadalquivir.model.jpa.EmbalseJpa;
import net.bounceme.chronos.chguadalquivir.repository.jpa.EmbalseJpaRepository;
import net.bounceme.chronos.chguadalquivir.services.EmbalseService;

@Service
public class EmbalseServiceImpl implements EmbalseService {

	@Autowired
	private EmbalseJpaRepository embalseJpaRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional(value = "jpaTransactionManager", readOnly = true)
	public List<EmbalseJpaDTO> listAll() {
		List<EmbalseJpa> embalses = embalseJpaRepository.findAll();
		return CollectionUtils.isNotEmpty(embalses) ? embalses.stream()
				.map(embalse -> modelMapper.map(embalse, EmbalseJpaDTO.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", readOnly = true)
	public EmbalseJpaDTO getByCode(String code) {
		Optional<EmbalseJpa> oEmbalseJpa = embalseJpaRepository.findById(code);
		return oEmbalseJpa.isPresent() ? modelMapper.map(oEmbalseJpa.get(), EmbalseJpaDTO.class) : null;
	}

	@Override
	@Transactional("jpaTransactionManager")
	public EmbalseJpaDTO write(EmbalseJpaDTO embalseJpaDTO) {
		EmbalseJpa embalseJpa = modelMapper.map(embalseJpaDTO, EmbalseJpa.class);
		embalseJpaRepository.save(embalseJpa);
		return modelMapper.map(embalseJpa, EmbalseJpaDTO.class);
	}

}
