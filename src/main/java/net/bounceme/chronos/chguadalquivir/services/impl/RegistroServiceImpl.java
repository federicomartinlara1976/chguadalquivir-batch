package net.bounceme.chronos.chguadalquivir.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.chguadalquivir.model.jpa.RegistroJpa;
import net.bounceme.chronos.chguadalquivir.repository.jpa.RegistroJpaRepository;
import net.bounceme.chronos.chguadalquivir.services.RegistroService;

@Service
public class RegistroServiceImpl implements RegistroService {
	
	@Autowired
	private RegistroJpaRepository registroJpaRepository;

	@Override
	public List<RegistroJpa> listAll() {
		return registroJpaRepository.findAll();
	}

	@Override
	public RegistroJpa getByCode(Long code) {
		Optional<RegistroJpa> oRegistroJpa = registroJpaRepository.findById(code);
		return oRegistroJpa.isPresent() ? oRegistroJpa.get() : null;
	}

	@Override
	@Transactional("jpaTransactionManager")
	public RegistroJpa write(RegistroJpa registroJpa) {
		registroJpaRepository.save(registroJpa);
		return registroJpa;
	}

}
