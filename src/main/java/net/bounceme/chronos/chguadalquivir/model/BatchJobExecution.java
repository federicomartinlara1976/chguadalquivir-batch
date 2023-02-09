package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="BATCH_JOB_EXECUTION")
@Getter
@Setter
@ToString
public class BatchJobExecution implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2151133111044402554L;

}
