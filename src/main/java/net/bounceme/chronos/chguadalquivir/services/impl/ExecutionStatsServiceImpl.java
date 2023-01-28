package net.bounceme.chronos.chguadalquivir.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import net.bounceme.chronos.chguadalquivir.model.ExecutionStats;
import net.bounceme.chronos.chguadalquivir.repository.ExecutionStatsRepository;
import net.bounceme.chronos.chguadalquivir.services.ExecutionStatsService;

@Service
public class ExecutionStatsServiceImpl implements ExecutionStatsService {

	@Autowired
	@Qualifier("executionStatsRepository")
	private ExecutionStatsRepository executionStatsRepository;
	
	@Override
	@Transactional("postgresTransactionManager")
	@SneakyThrows
	public void save(ExecutionStats executionStats) {
		executionStatsRepository.save(executionStats);
	}

}
