package net.bounceme.chronos.chguadalquivir.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.jpa.EmbalseJpa;
import net.bounceme.chronos.chguadalquivir.model.jpa.RegistroJpa;
import net.bounceme.chronos.chguadalquivir.repository.jpa.EmbalseJpaRepository;
import net.bounceme.chronos.chguadalquivir.repository.jpa.RegistroJpaRepository;
import net.bounceme.chronos.chguadalquivir.services.RegistroService;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;

@Service
@Slf4j
public class RegistroServiceImpl implements RegistroService {
	
	@Autowired
	private RegistroJpaRepository registroJpaRepository;
	
	@Autowired
	private EmbalseJpaRepository embalseJpaRepository;

	@Autowired
	private CHGuadalquivirHelper helper;
	
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
	@Transactional("jpaTransactionManager")
	public Float getMen(String codigoEmbalse, Date fecha) {
		log.info("Embalse: {}, fecha: {}", codigoEmbalse, fecha);
		Optional<EmbalseJpa> oEmbalse = embalseJpaRepository.findById(codigoEmbalse);
		
		if (oEmbalse.isPresent()) {
			Date date = helper.fromString("2024-03-14");
			List<RegistroJpa> registros = registroJpaRepository.findByEmbalseAndFecha(oEmbalse.get(), date);
			return (CollectionUtils.isNotEmpty(registros)) ? registros.get(0).getMEN() : BigDecimal.ZERO.floatValue();
		}
		
		return BigDecimal.ZERO.floatValue();
	}
}
