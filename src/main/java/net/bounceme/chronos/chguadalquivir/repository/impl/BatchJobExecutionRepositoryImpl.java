package net.bounceme.chronos.chguadalquivir.repository.impl;

import java.util.List;

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
		TypedQuery<BatchJobExecution> query = em
				.createQuery("SELECT b FROM BatchJobExecution b order by b.createTime desc", BatchJobExecution.class);
		query.setMaxResults(1);
		return query.getSingleResult();
	}

	@Override
	public List<BatchJobExecution> getLastJobExecutions(Integer numResults, List<String> applicationJobs) {
		TypedQuery<BatchJobExecution> query = em
				.createQuery("SELECT b FROM BatchJobExecution b where b.jobInstance.jobName in :applicationJobs order by b.createTime desc", BatchJobExecution.class);
		query.setParameter("applicationJobs", applicationJobs);
		query.setMaxResults(numResults);
		return query.getResultList();
	}

}
