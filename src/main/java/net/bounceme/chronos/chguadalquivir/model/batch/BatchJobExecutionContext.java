package net.bounceme.chronos.chguadalquivir.model.batch;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="BATCH_JOB_EXECUTION_CONTEXT")
@Data
public class BatchJobExecutionContext implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1253176492036388728L;
	
	@JsonIgnore
	@Id
	@OneToOne
	@JoinColumn(name = "JOB_EXECUTION_ID")
	private BatchJobExecution jobExecution;
	
	@Column(name="SHORT_CONTEXT")
	private String shortContext;
	
	@Column(name="SERIALIZED_CONTEXT")
	private String serializedContext;
}
