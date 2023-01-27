package net.bounceme.chronos.chguadalquivir.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import net.bounceme.chronos.chguadalquivir.model.Execution;

public interface ExecutionsRepository extends MongoRepository<Execution, String> {

	@Query("{ '_id' : ?0 }")
	List<Execution> findByDate(String id);
	
	@Query("{ $and : [ { '_id' : { $gte : ?0 } }, { '_id' : { $lte : ?1 } } ] }")
	List<Execution> listExecutions(String from, String to);
}