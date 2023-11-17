package net.bounceme.chronos.chguadalquivir.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.chguadalquivir.model.jpa.EmbalseJpa;
import net.bounceme.chronos.chguadalquivir.repository.jpa.EmbalseJpaRepository;
import net.bounceme.chronos.chguadalquivir.services.EmbalseService;

@Service
public class EmbalseServiceImpl implements EmbalseService {
	
	@Autowired
	private EmbalseJpaRepository embalseJpaRepository;

	@Override
	public List<EmbalseJpa> listAll() {
		return embalseJpaRepository.findAll();
	}

	@Override
	public EmbalseJpa getByCode(String code) {
		Optional<EmbalseJpa> oEmbalseJpa = embalseJpaRepository.findById(code);
		return oEmbalseJpa.isPresent() ? oEmbalseJpa.get() : null;
	}

	@Override
	@Transactional("jpaTransactionManager")
	public EmbalseJpa write(EmbalseJpa embalseJpa) {
		embalseJpaRepository.save(embalseJpa);
		return embalseJpa;
	}

}
