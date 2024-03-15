package net.bounceme.chronos.chguadalquivir.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.jpa.RegistroJpa;
import net.bounceme.chronos.chguadalquivir.repository.jpa.RegistroJpaRepository;
import net.bounceme.chronos.chguadalquivir.services.RegistroService;

@Service
@Slf4j
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
	
	@Override
	@Transactional(value = "jpaTransactionManager", readOnly = true)
	public Float getCapacidad(String codigoEmbalse, Date fecha) {
		log.info("Embalse: {}, fecha: {}", codigoEmbalse, fecha);
		Float capacidad = registroJpaRepository.getCapacidad(fecha, codigoEmbalse);
		return capacidad;
	}
}
