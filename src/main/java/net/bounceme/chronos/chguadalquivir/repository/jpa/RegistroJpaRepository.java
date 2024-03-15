package net.bounceme.chronos.chguadalquivir.repository.jpa;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.chguadalquivir.model.jpa.RegistroJpa;

@Repository
public interface RegistroJpaRepository extends JpaRepository<RegistroJpa, Long> {
	
	@Query("select r from RegistroJpa r where r.fecha = :fecha and r.embalse.codigo = :codigo")
	RegistroJpa getRegistro(Date fecha, String codigo);
}
