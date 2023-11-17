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
@Table(name="BATCH_STEP_EXECUTION_CONTEXT")
@Data
public class BatchStepExecutionContext implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 458065714471650814L;
	
	@JsonIgnore
	@Id
	@OneToOne
	@JoinColumn(name = "STEP_EXECUTION_ID")
	private BatchStepExecution stepExecution;
	
	@Column(name="SHORT_CONTEXT")
	private String shortContext;
	
	@Column(name="SERIALIZED_CONTEXT")
	private String serializedContext;

}
