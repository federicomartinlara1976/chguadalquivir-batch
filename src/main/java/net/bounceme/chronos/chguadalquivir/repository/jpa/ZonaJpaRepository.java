package net.bounceme.chronos.chguadalquivir.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.chguadalquivir.model.jpa.ZonaJpa;

@Repository
public interface ZonaJpaRepository extends JpaRepository<ZonaJpa, String> {

}
