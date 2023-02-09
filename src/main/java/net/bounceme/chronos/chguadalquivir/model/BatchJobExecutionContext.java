package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="BATCH_JOB_EXECUTION_CONTEXT")
@Getter
@Setter
@ToString
public class BatchJobExecutionContext implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1253176492036388728L;
	
}
