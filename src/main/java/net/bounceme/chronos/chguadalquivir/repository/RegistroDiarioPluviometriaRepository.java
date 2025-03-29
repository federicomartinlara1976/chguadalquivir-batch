package net.bounceme.chronos.chguadalquivir.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioPluviometria;

public interface RegistroDiarioPluviometriaRepository extends MongoRepository<RegistroDiarioPluviometria, String> {

}