package net.bounceme.chronos.chguadalquivir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.chguadalquivir.model.ExecutionStats;

@Repository
public interface ExecutionStatsRepository extends JpaRepository<ExecutionStats, Long> {
}
