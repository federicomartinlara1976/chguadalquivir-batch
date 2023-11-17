package net.bounceme.chronos.chguadalquivir.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.bounceme.chronos.chguadalquivir.model.Zona;

public interface ZonasRepository extends MongoRepository<Zona, String> {
}