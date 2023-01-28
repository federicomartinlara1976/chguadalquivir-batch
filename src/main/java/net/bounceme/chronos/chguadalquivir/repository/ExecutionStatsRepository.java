package net.bounceme.chronos.chguadalquivir.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bounceme.chronos.chguadalquivir.model.ExecutionStats;

public interface ExecutionStatsRepository extends JpaRepository<ExecutionStats, Long> {
}
