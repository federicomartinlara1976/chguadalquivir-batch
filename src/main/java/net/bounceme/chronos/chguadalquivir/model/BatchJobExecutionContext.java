package net.bounceme.chronos.chguadalquivir.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
	
	@OneToOne @MapsId
	private BatchJobExecution jobExecution;
	
	@Column(name="SHORT_CONTEXT")
	private String shortContext;
	
	@Column(name="SERIALIZED_CONTEXT")
	private String serializedContext;
}
