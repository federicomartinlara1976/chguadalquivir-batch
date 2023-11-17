package net.bounceme.chronos.chguadalquivir.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.chguadalquivir.model.jpa.ZonaJpa;
import net.bounceme.chronos.chguadalquivir.repository.jpa.ZonaJpaRepository;
import net.bounceme.chronos.chguadalquivir.services.ZonaService;

@Service
public class ZonaServiceImpl implements ZonaService {
	
	@Autowired
	private ZonaJpaRepository zonaJpaRepository;

	@Override
	public List<ZonaJpa> listAll() {
		return zonaJpaRepository.findAll();
	}

	@Override
	public ZonaJpa getByCode(String code) {
		Optional<ZonaJpa> oZonaJpa = zonaJpaRepository.findById(code);
		return oZonaJpa.isPresent() ? oZonaJpa.get() : null;
	}

	@Override
	@Transactional("jpaTransactionManager")
	public ZonaJpa write(ZonaJpa zonaJpa) {
		zonaJpaRepository.save(zonaJpa);
		return zonaJpa;
	}

}
