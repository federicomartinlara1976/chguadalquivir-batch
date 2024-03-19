package net.bounceme.chronos.chguadalquivir.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.chguadalquivir.model.jpa.EmbalseJpa;

@Repository
public interface EmbalseJpaRepository extends JpaRepository<EmbalseJpa, String> {

	Optional<EmbalseJpa> findByCodigo(String codigo);

}
