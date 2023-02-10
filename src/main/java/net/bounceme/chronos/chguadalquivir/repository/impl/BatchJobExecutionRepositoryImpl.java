package net.bounceme.chronos.chguadalquivir.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import net.bounceme.chronos.chguadalquivir.model.BatchJobExecution;
import net.bounceme.chronos.chguadalquivir.repository.BatchJobExecutionRepository;

@Repository
public class BatchJobExecutionRepositoryImpl implements BatchJobExecutionRepository {
	
	@PersistenceContext(unitName = "batch-unit")
	private EntityManager em;

	@Override
	public BatchJobExecution getLastJobExecution() {
		TypedQuery<BatchJobExecution> query = em.createQuery("SELECT b FROM BatchJobExecution b order by b.createTime desc", BatchJobExecution.class);
		query.setMaxResults(1);
		return query.getSingleResult();
	}

}
